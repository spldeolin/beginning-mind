package com.spldeolin.beginningmind.core.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.constant.Abbreviation;
import com.spldeolin.beginningmind.core.excel.ExcelContext.ColumnDefinition;
import com.spldeolin.beginningmind.core.util.Times;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.util.StringUtil;

/**
 * Excel读写工具类
 *
 * @author Deolin 2018/07/07
 */
@UtilityClass
@Log4j2
public class Excels2 {

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz) throws ParseInvalidException {
        ExcelContext excelContext = new ExcelContext();
        try {
            analyzeMultipartFile(excelContext, multipartFile);

            return readExcel(excelContext, clazz);
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelContext);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) throws ParseInvalidException {
        ExcelContext excelContext = new ExcelContext();
        try {
            analyzeFile(excelContext, file);

            return readExcel(excelContext, clazz);
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelContext);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(ExcelContext excelContext, Class<T> clazz) throws ParseInvalidException {
        try {
            analyzeModel(excelContext, clazz);
            Workbook workbook = openWorkbook(excelContext);
            Sheet sheet = openSheet(excelContext, workbook);
            analyzeModelFields(excelContext, clazz);
            analyzeColumns(excelContext, sheet);
            List<T> result = Lists.newArrayList();
            List<ParseInvalid> parseInvalids = Lists.newArrayList();
            for (Row row : listValidRows(excelContext, sheet)) {
                if (row != null) {
                    try {
                        result.add(parseRow(clazz, excelContext.getColumnDefinitions(), row));
                    } catch (ParseInvalidException e) {
                        parseInvalids.addAll(e.getParseInvalids());
                    }
                }
            }
            if (parseInvalids.size() > 0) {
                throw new ParseInvalidException().setParseInvalids(parseInvalids);
            }
            return result;
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelContext);
        }
    }

    private static void analyzeFile(ExcelContext excelContext, File file) throws IOException {
        String filename = file.getName();
        excelContext.setFileExtension(FilenameUtils.getExtension(filename));
        excelContext.setFileInputStream(FileUtils.openInputStream(file));
    }

    private static void analyzeMultipartFile(ExcelContext excelContext,
            MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        excelContext.setFileExtension(FilenameUtils.getExtension(filename));
        excelContext.setFileInputStream(multipartFile.getInputStream());
    }

    private static <T> void analyzeModel(ExcelContext excelContext, Class<T> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]未声明@ExcelSheet");
        }
        excelContext.setSheetName(sheetAnno.sheetName());
        excelContext.setRowOffSet(sheetAnno.startingRowNumber());
    }

    private static <T> void analyzeModelFields(ExcelContext excelContext, Class<T> clazz) {
        List<ExcelContext.ColumnDefinition> columnDefinitions = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn columnAnno = field.getAnnotation(ExcelColumn.class);
            if (columnAnno == null) {
                continue;
            }
            ExcelContext.ColumnDefinition columnDefinition = new ExcelContext.ColumnDefinition();
            columnDefinition.setFirstColumnName(columnAnno.firstColumnName());
            columnDefinition.setModelField(field);
            Class<? extends Formatter> formatter = columnAnno.formatter();
            if (formatter != Formatter.class) {
                columnDefinition.setFormatter(Abbreviation.objs.newInstance(formatter));
            }
            columnDefinition.setDefaultValue(columnAnno.defaultValue());
            columnDefinitions.add(columnDefinition);
        }
        if (columnDefinitions.size() == 0) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]中不存在@ExcelColumn字段");
        }
        excelContext.setColumnDefinitions(columnDefinitions);
    }

    private static <T> void analyzeColumns(ExcelContext excelContext, Sheet sheet) {
        for (ExcelContext.ColumnDefinition columnDefinition : excelContext.getColumnDefinitions()) {
            String columnLetter = findColumnLetterByFirstColumnName(sheet, columnDefinition.getFirstColumnName());
            if (columnLetter != null) {
                columnDefinition.setColumnLetter(columnLetter);
                columnDefinition.setColumnNumber(letterToNumber(columnLetter));
            }
        }
    }

    private static String findColumnLetterByFirstColumnName(Sheet sheet, String firstColumnName) {
        if (StringUtils.isBlank(firstColumnName)) {
            return null;
        }
        Row row = sheet.getRow(0);
        if (row == null) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "] 的首行不存在");
        }
        String result = null;
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.toString().equals(firstColumnName)) {
                result = numberToLetter(cell.getColumnIndex() + 1);
            }
        }
        return result;
    }

    private static Workbook openWorkbook(ExcelContext excelContext) throws IOException {
        Workbook workBook;
        String fileExtension = excelContext.getFileExtension();
        InputStream inputStream = excelContext.getFileInputStream();
        if ("xlsx".equals(fileExtension)) {
            workBook = new XSSFWorkbook(inputStream);
        } else if ("xls".equals(fileExtension)) {
            workBook = new HSSFWorkbook(inputStream);
        } else {
            throw new ExcelAnalyzeException("文件拓展名不正确");
        }
        return workBook;
    }

    private static Sheet openSheet(ExcelContext excelContext, Workbook workbook) {
        if (workbook.getNumberOfSheets() == 0) {
            throw new ExcelAnalyzeException("工作簿中不存在工作表");
        }
        Sheet sheet = workbook.getSheet(excelContext.getSheetName());
        if (sheet == null) {
            throw new ExcelAnalyzeException("工作表 [" + excelContext.getSheetName() + "]不存在");
        }
        return sheet;
    }

    private static List<Row> listValidRows(ExcelContext excelContext, Sheet sheet) {
        List<Row> rows = Lists.newArrayList();
        int startRowNum = sheet.getFirstRowNum();
        int offsetRowNum = excelContext.getRowOffSet() - 1;
        if (offsetRowNum >= startRowNum) {
            startRowNum = offsetRowNum;
        }
        for (int rownum = startRowNum; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            List<Integer> cellNumbers = excelContext.getColumnDefinitions().stream().map(
                    ExcelContext.ColumnDefinition::getColumnNumber).collect(Collectors.toList());
            if (row != null && !rowIsAllBlankInCellNumbers(row, cellNumbers)) {
                rows.add(row);
            }

        }
        if (rows.size() == 0) {
            throw new ExcelAnalyzeException("工作表中没有内容");
        }
        return rows;
    }

    /**
     * 判断行中指定列的单元格的内容是否全部为空白
     */
    private static boolean rowIsAllBlankInCellNumbers(Row row, List<Integer> cellNumbers) {
        List<String> contents = Lists.newArrayList();
        for (Integer cellNumber : cellNumbers) {
            if (cellNumber == null) {
                contents.add(null);
                continue;
            }
            int cellIndex = cellNumber - 1;
            Cell cell = row.getCell(cellIndex);
            if (cell == null) {
                contents.add(null);
            } else {
                contents.add(cell.toString());
            }
        }
        return StringUtils.isAllBlank(contents.toArray(new String[0]));
    }

    private static <T> T parseRow(Class<T> clazz, List<ExcelContext.ColumnDefinition> columnDefinitions,
            Row row) throws ParseInvalidException {
        T t = Abbreviation.objs.newInstance(clazz);
        List<ParseInvalid> parseInvalids = Lists.newArrayList();
        for (ExcelContext.ColumnDefinition columnDefinition : columnDefinitions) {
            Integer columnNumber = columnDefinition.getColumnNumber();
            if (columnNumber == null) {
                continue;
            }
            // cell在row中是从0开始的
            int cellIndex = columnNumber - 1;
            Cell cell = row.getCell(cellIndex);
            String cellContent;
            if (cell == null) {
                cellContent = columnDefinition.getDefaultValue();
            } else {
                cellContent = cell.toString().trim();
            }
            Formatter formatter = columnDefinition.getFormatter();
            boolean assignedFormatter = formatter != null && formatter.getClass() != Formatter.class;
            Field field = columnDefinition.getModelField();
            Object fieldValue = null;
            try {
                if (StringUtil.isNotEmpty(cellContent)) {
                    if (!assignedFormatter) {
                        // 没有指定formatter，尝试用缺省方式指定常用formatter
                        Class fieldType = field.getType();
                        // 以.0结尾则删除最后两位
                        if (cellContent.endsWith(".0")) {
                            cellContent = cellContent.substring(0, cellContent.length() - 2);
                        }
                        if (fieldType == String.class) {
                            fieldValue = cellContent;
                        } else if (fieldType == Integer.class) {
                            fieldValue = NumberUtils.createInteger(cellContent);
                        } else if (fieldType == Long.class) {
                            fieldValue = NumberUtils.createLong(cellContent);
                        } else if (fieldType == Float.class) {
                            fieldValue = NumberUtils.createFloat(cellContent);
                        } else if (fieldType == Double.class) {
                            fieldValue = NumberUtils.createDouble(cellContent);
                        } else if (fieldType == BigDecimal.class) {
                            fieldValue = NumberUtils.createBigDecimal(cellContent);
                        } else if (fieldType == Boolean.class) {
                            fieldValue = BooleanUtils.toBoolean(cellContent);
                        } else if (fieldType == LocalDateTime.class) {
                            fieldValue = LocalDateTime.parse(cellContent, Times.DEFAULT_DATE_TIME_FORMATTER);
                        } else {
                            throw new RuntimeException("工具类Excels未为 [" + fieldType.getSimpleName() + field.getName() +
                                    "]提供缺省转换策略，请在@ExcelColumn中指定具体formatter");
                        }
                    } else {
                        // 指定了formatter
                        fieldValue = formatter.parse(cellContent);
                    }
                }
                field.setAccessible(true);
                field.set(t, fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                ParseInvalid parseInvalid = ParseInvalid.builder().rowNumber(row.getRowNum() + 1).columnLetter(
                        columnDefinition.getColumnLetter()).cause("数据格式非法").build();
                if (assignedFormatter) {
                    parseInvalid.setCause(e.getMessage());
                }
                parseInvalids.add(parseInvalid);
            }
        }
        if (parseInvalids.size() > 0) {
            throw new ParseInvalidException().setParseInvalids(parseInvalids);
        }
        return t;
    }

    private static void close(ExcelContext excelContext) {
        InputStream inputStream = excelContext.getFileInputStream();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static int letterToNumber(String columnLetter) {
        columnLetter = columnLetter.toUpperCase();
        int number = 0;
        for (int i = 0; i < columnLetter.length(); i++) {
            number *= 26;
            number += (columnLetter.charAt(i) - 'A' + 1);
        }
        if (number == 0) {
            number = 1;
        }
        return number;
    }

    private static String numberToLetter(int number) {
        StringBuilder rs = new StringBuilder();
        do {
            number--;
            rs.insert(0, ((char) (number % 26 + (int) 'A')));
            number = (number - number % 26) / 26;
        } while (number > 0);
        return rs.toString();
    }

    @SneakyThrows
    public static <T> void writeExcel(File file, Class<T> clazz, List<T> list) {
        ensureFileExist(file);
        ExcelContext excelContext = new ExcelContext();
        try (OutputStream os = new FileOutputStream(file)) {
            analyzeFile(excelContext, file);
            analyzeModel(excelContext, clazz);
            analyzeModelFields(excelContext, clazz);

            // 创建工作簿
            Workbook workbook = newWorkbook(excelContext);

            // 创建工作表
            Sheet sheet = newSheet(excelContext, workbook);

            // 写入第一行（第一行展示列名）
            writeFirstRow(excelContext, sheet);

            // 单元格格式（文本） 这是干嘛的？
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("@"));

            // 写入第N行（下面的行展示数据）
            writeRows(excelContext, cellStyle, sheet, list);

            workbook.write(os);
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelContext);
        }
    }

    private void ensureFileExist(File file) {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("无法创建文件");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Workbook newWorkbook(ExcelContext excelContext) {
        String fileExtension = excelContext.getFileExtension();
        if ("xlsx".equals(fileExtension)) {
            return new XSSFWorkbook();
        } else if ("xls".equals(fileExtension)) {
            return new HSSFWorkbook();
        } else {
            throw new ServiceException("文件拓展名不正确");
        }
    }

    private static Sheet newSheet(ExcelContext excelContext, Workbook workbook) {
        return workbook.createSheet(excelContext.getSheetName());
    }

    private static void writeFirstRow(ExcelContext excelContext, Sheet sheet) {
        Row titleRow = sheet.createRow(0);

        List<ColumnDefinition> columns = excelContext.getColumnDefinitions();
        for (int i = 0; i < columns.size(); i++) {
            ColumnDefinition column = columns.get(i);
            int cellNumber = i;
            Cell cell = titleRow.createCell(cellNumber);

            String cellValue = column.getFirstColumnName();
            cell.setCellValue(cellValue);
        }
    }

    private static <T> void writeRows(ExcelContext excelContext, CellStyle cellStyle, Sheet sheet, List<T> list) {
        for (int j = 0; j < list.size(); j++) {
            T t = list.get(j);
            int rowIndex = j + 1;
            Row titleRow = sheet.createRow(rowIndex);

            List<ColumnDefinition> columns = excelContext.getColumnDefinitions();
            for (int i = 0; i < columns.size(); i++) {
                ColumnDefinition column = columns.get(i);
                int cellNumber = i;
                Cell cell = titleRow.createCell(cellNumber);

                String cellValue = formatCellValue(column, t);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private <T> String formatCellValue(ExcelContext.ColumnDefinition columnDefinition, T t) {
        Field field = columnDefinition.getModelField();
        field.setAccessible(true);
        Object fieldValue = field.get(t);
        if (fieldValue == null) {
            return columnDefinition.getDefaultValue();
        }

        Formatter formatter = columnDefinition.getFormatter();
        if (formatter == null) {
            return fieldValue.toString();
        }

        return formatter.format(fieldValue);
    }

}

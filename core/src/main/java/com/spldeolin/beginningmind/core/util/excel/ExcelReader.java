package com.spldeolin.beginningmind.core.util.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.excel.converter.CellConverter;
import com.spldeolin.beginningmind.core.util.excel.converter.DefalutCellConverterFactory;
import com.spldeolin.beginningmind.core.util.excel.entity.ColumnDefinition;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelDefinitionContext;
import com.spldeolin.beginningmind.core.util.excel.entity.Invalid;
import com.spldeolin.beginningmind.core.util.excel.entity.SheetDefinition;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;
import com.spldeolin.beginningmind.core.util.excel.exception.DefaultCellConverterAbsentException;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelAnalyzeException;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelCellContentInvalidException;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelReader {

    /**
     * 读取Excel
     */
    static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz)
            throws ExcelCellContentInvalidException, ExcelAnalyzeException {
        try {
            ExcelDefinitionContext.newSheetDefinition();
            ExcelAnalyzer.analyzeMultipartFile(multipartFile);
            return readExcel(clazz);
        } catch (IOException e) {
            throw new RuntimeException("文件读写失败");
        } finally {
            close();
            ExcelDefinitionContext.clearAll();
        }

    }

    /**
     * 读取Excel
     */
    static <T> List<T> readExcel(File file, Class<T> clazz)
            throws ExcelCellContentInvalidException, ExcelAnalyzeException {
        try {
            ExcelDefinitionContext.newSheetDefinition();
            ExcelAnalyzer.analyzeFile(file);
            return readExcel(clazz);
        } catch (IOException e) {
            throw new RuntimeException("文件读写失败");
        } finally {
            close();
            ExcelDefinitionContext.clearAll();
        }
    }

    /**
     * 读取Excel
     */
    private static <T> List<T> readExcel(Class<T> clazz)
            throws ExcelCellContentInvalidException, ExcelAnalyzeException {
        try {
            ExcelAnalyzer.analyzeModel(clazz);
            Workbook workbook = openWorkbook();
            Sheet sheet = openSheet(workbook);
            ExcelAnalyzer.analyzeModelFields(clazz);
            analyzeColumns(sheet);
            List<T> result = Lists.newArrayList();
            List<Invalid> parseInvalids = Lists.newArrayList();
            for (Row row : listValidRows(sheet)) {
                if (row != null) {
                    try {
                        result.add(readRow(clazz, row));
                    } catch (ExcelCellContentInvalidException e) {
                        parseInvalids.addAll(e.getParseInvalids());
                    }
                }
            }
            if (parseInvalids.size() > 0) {
                throw new ExcelCellContentInvalidException(parseInvalids);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("文件读写失败");
        } finally {
            close();
        }
    }

    private static void analyzeColumns(Sheet sheet) {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        for (ColumnDefinition columnDefinition : ExcelDefinitionContext.getColumnDefinitions()) {
            String columnLetter = findColumnLetterByFirstColumnName(sheet, columnDefinition.getFirstColumnName(),
                    sheetDefinition.getDataRowStartNo());
            if (columnLetter != null) {
                columnDefinition.setColumnLetter(columnLetter);
                columnDefinition.setColumnNumber(letterToNumber(columnLetter));
            }
        }
    }

    private static String findColumnLetterByFirstColumnName(Sheet sheet, String firstColumnName,
            Integer dataRowStartNo) {
        if (StringUtils.isBlank(firstColumnName)) {
            return null;
        }

        int titleRowStartNo = dataRowStartNo - 1;
        Row row = sheet.getRow(titleRowStartNo - 1); // getRow需要减1
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

    private static Workbook openWorkbook() throws IOException, ExcelAnalyzeException {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        Workbook workBook;
        String fileExtension = sheetDefinition.getFileExtension();
        InputStream inputStream = sheetDefinition.getFileInputStream();
        if ("xlsx".equals(fileExtension)) {
            workBook = new XSSFWorkbook(inputStream);
        } else if ("xls".equals(fileExtension)) {
            workBook = new HSSFWorkbook(inputStream);
        } else {
            throw new ExcelAnalyzeException("文件拓展名非法");
        }
        return workBook;
    }

    private static Sheet openSheet(Workbook workbook) throws ExcelAnalyzeException {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        if (workbook.getNumberOfSheets() == 0) {
            throw new ExcelAnalyzeException("工作簿中不存在工作表");
        }
        Sheet sheet = workbook.getSheet(sheetDefinition.getSheetName());
        if (sheet == null) {
            throw new ExcelAnalyzeException("工作表 [" + sheetDefinition.getSheetName() + "]不存在");
        }
        return sheet;
    }

    private static List<Row> listValidRows(Sheet sheet) throws ExcelAnalyzeException {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        List<Row> rows = Lists.newArrayList();
        int startRowNum = sheet.getFirstRowNum();
        int offsetRowNum = sheetDefinition.getDataRowStartNo() - 1;
        if (offsetRowNum >= startRowNum) {
            startRowNum = offsetRowNum;
        }
        for (int rownum = startRowNum; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            List<Integer> cellNumbers = ExcelDefinitionContext.getColumnDefinitions().stream()
                    .map(ColumnDefinition::getColumnNumber).collect(Collectors.toList());
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

    private static <T> T readRow(Class<T> clazz, Row row) throws ExcelCellContentInvalidException {
        T t = new ObjenesisStd(true).newInstance(clazz);
        List<Invalid> parseInvalids = Lists.newArrayList();
        for (ColumnDefinition columnDefinition : ExcelDefinitionContext.getColumnDefinitions()) {
            Integer columnNumber = columnDefinition.getColumnNumber();
            if (columnNumber == null) {
                continue;
            }
            // cell在row中是从0开始的
            int cellIndex = columnNumber - 1;
            Cell cell = row.getCell(cellIndex);
            String cellContent;
            if (cell == null || StringUtils.isEmpty(cell.toString())) {
                cellContent = columnDefinition.getDefaultValue();
            } else {
                if (CellType.NUMERIC == cell.getCellType()) {
                    // 去除科学计数法
                    cellContent = new DecimalFormat("0").format(cell.getNumericCellValue());
                } else {
                    cellContent = cell.toString().trim();
                }
            }
            CellConverter cellConverter = columnDefinition.getFormatter();
            boolean assignedFormatter = cellConverter != null && cellConverter.getClass() != CellConverter.class;
            Field field = columnDefinition.getModelField();
            Object fieldValue = null;
            try {
                if (StringUtils.isNotEmpty(cellContent)) {
                    // 没有指定formatter，尝试用缺省方式指定常用formatter
                    if (!assignedFormatter) {
                        // 以.0结尾则删除最后两位
                        if (cellContent.endsWith(".0")) {
                            cellContent = cellContent.substring(0, cellContent.length() - 2);
                        }
                        cellConverter = DefalutCellConverterFactory.produceConverter(field.getType());
                    }
                    fieldValue = cellConverter.readFromCellContent(cellContent);
                }
                field.setAccessible(true);
                field.set(t, fieldValue);
            } catch (IllegalAccessException e) {
                // field.set() throws
                throw new RuntimeException("impossible unless bug");
            } catch (DefaultCellConverterAbsentException e) {
                e.setFieldType(field.getType().getSimpleName());
                e.setFieldName(clazz.getName() + "." + field.getName());
                throw e;
            } catch (CellConverterReadException e) {
                Invalid invalid = new Invalid(columnDefinition.getColumnLetter(), row.getRowNum() + 1, "数据格式非法");
                parseInvalids.add(invalid);
            }
        }
        if (parseInvalids.size() > 0) {
            throw new ExcelCellContentInvalidException(parseInvalids);
        }
        return t;
    }

    private static void close() {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        InputStream inputStream = sheetDefinition.getFileInputStream();
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

}

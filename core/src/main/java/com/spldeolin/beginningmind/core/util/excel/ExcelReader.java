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
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelContext;
import com.spldeolin.beginningmind.core.util.excel.entity.Invalid;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelAnalyzeException;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelReadException;
import com.spldeolin.beginningmind.core.util.excel.exception.UnknowDefaultTypeException;
import com.spldeolin.beginningmind.core.util.excel.formatter.Converter;
import com.spldeolin.beginningmind.core.util.excel.formatter.DefalutConverterFactory;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelReader {

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz) throws ExcelReadException {
        ExcelContext excelContext = new ExcelContext();
        try {
            ExcelAnalyzer.analyzeMultipartFile(excelContext, multipartFile);

            return readExcel(excelContext, clazz);
        } catch (IOException e) {
            throw new RuntimeException("文件读写失败");
        } finally {
            close(excelContext);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) throws ExcelReadException {
        ExcelContext excelContext = new ExcelContext();
        try {
            ExcelAnalyzer.analyzeFile(excelContext, file);

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
    private static <T> List<T> readExcel(ExcelContext excelContext, Class<T> clazz) throws ExcelReadException {
        try {
            ExcelAnalyzer.analyzeModel(excelContext, clazz);
            Workbook workbook = openWorkbook(excelContext);
            Sheet sheet = openSheet(excelContext, workbook);
            ExcelAnalyzer.analyzeModelFields(excelContext, clazz);
            analyzeColumns(excelContext, sheet);
            List<T> result = Lists.newArrayList();
            List<Invalid> parseInvalids = Lists.newArrayList();
            for (Row row : listValidRows(excelContext, sheet)) {
                if (row != null) {
                    try {
                        result.add(readRow(clazz, excelContext.getColumnDefinitions(), row));
                    } catch (ExcelReadException e) {
                        parseInvalids.addAll(e.getParseInvalids());
                    }
                }
            }
            if (parseInvalids.size() > 0) {
                throw new ExcelReadException(parseInvalids);
            }
            return result;
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelContext);
        }
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
            List<Integer> cellNumbers = excelContext.getColumnDefinitions().stream()
                    .map(ExcelContext.ColumnDefinition::getColumnNumber).collect(Collectors.toList());
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

    private static <T> T readRow(Class<T> clazz, List<ExcelContext.ColumnDefinition> columnDefinitions, Row row)
            throws ExcelReadException {
        T t = new ObjenesisStd(true).newInstance(clazz);
        List<Invalid> parseInvalids = Lists.newArrayList();
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
                if (CellType.NUMERIC == cell.getCellType()) {
                    // 去除科学计数法
                    cellContent = new DecimalFormat("0").format(cell.getNumericCellValue());
                } else {
                    cellContent = cell.toString().trim();
                }
            }
            Converter formatter = columnDefinition.getFormatter();
            boolean assignedFormatter = formatter != null && formatter.getClass() != Converter.class;
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
                        formatter = DefalutConverterFactory.produceConverter(field.getType());
                    }
                    fieldValue = formatter.read(cellContent);
                }
                field.setAccessible(true);
                field.set(t, fieldValue);
            } catch (IllegalAccessException e) {
                // field.set() throws
                throw new RuntimeException("impossible unless bug");
            } catch (UnknowDefaultTypeException e) {
                e.setFieldType(field.getType().getSimpleName());
                e.setFieldName(clazz.getName() + "." + field.getName());
                throw e;
            } catch (ConverterReadException e) {
                Invalid invalid = new Invalid(columnDefinition.getColumnLetter(), row.getRowNum() + 1, "数据格式非法");
                parseInvalids.add(invalid);
            }
        }
        if (parseInvalids.size() > 0) {
            throw new ExcelReadException(parseInvalids);
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

}

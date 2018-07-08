package com.spldeolin.beginningmind.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.constant.Abbreviation;
import com.spldeolin.beginningmind.core.util.excel.ExcelAnalyzeException;
import com.spldeolin.beginningmind.core.util.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.ExcelDefinition;
import com.spldeolin.beginningmind.core.util.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.util.excel.Formatter;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @author Deolin 2018/07/07
 */
@UtilityClass
@Log4j2
public class Excels2 {

    public static <T> List<T> readExcel(File file, Class<T> clazz) {
        ExcelDefinition excelDefinition = new ExcelDefinition();
        try {
            analyzeFile(excelDefinition, file);
            analyzeModel(excelDefinition, clazz);
            analyzeModelFields(excelDefinition, clazz);
            // TODO 解析完成后excelDefinition做本地缓存
            Workbook workbook = openWorkbook(excelDefinition);
            Sheet sheet = openSheet(excelDefinition, workbook);
            List<T> result = Lists.newArrayList();
            for (Row row : listRows(excelDefinition, sheet)) {
                if (row != null) {
                    result.add(parseRow(clazz, excelDefinition.getColumnDefinitions(), row));
                }
            }
            return result;
        } catch (IOException e) {
            throw new ExcelAnalyzeException("文件读写失败");
        } finally {
            close(excelDefinition);
        }
    }

    /* TODO 重载MultipartFile */
    private static void analyzeFile(ExcelDefinition excelDefinition, File file) throws IOException {
        String filename = file.getName();
        excelDefinition.setFileName(FilenameUtils.getBaseName(filename));
        excelDefinition.setFileExtension(FilenameUtils.getExtension(filename));
        excelDefinition.setFileInputStream(FileUtils.openInputStream(file));
    }

    private static <T> void analyzeModel(ExcelDefinition excelDefinition, Class<T> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]未声明@ExcelSheet");
        }
        excelDefinition.setSheetIndex(sheetAnno.sheetIndex());
        excelDefinition.setRowOffSet(sheetAnno.rowOffSet());
    }

    private static <T> void analyzeModelFields(ExcelDefinition excelDefinition, Class<T> clazz) {
        List<ExcelDefinition.ColumnDefinition> columnDefinitions = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn columnAnno = field.getAnnotation(ExcelColumn.class);
            if (columnAnno == null) {
                continue;
            }
            ExcelDefinition.ColumnDefinition columnDefinition = new ExcelDefinition.ColumnDefinition();
            String columnLetter = columnAnno.columnLetter();
            columnDefinition.setColumnLetter(columnLetter);
            columnDefinition.setColumnNumber(letterToNumber(columnLetter));
            columnDefinition.setModelField(field);
            Class<? extends Formatter> formatter = columnAnno.formatter();
            if (formatter != null && formatter != Formatter.class)
                columnDefinition.setFormatter(Abbreviation.objs.newInstance(formatter));
            columnDefinition.setDefaultValue(columnAnno.defaultValue());
            columnDefinitions.add(columnDefinition);
        }
        if (columnDefinitions.size() == 0) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]中不存在@ExcelColumn字段");
        }
        excelDefinition.setColumnDefinitions(columnDefinitions);
    }

    private static Workbook openWorkbook(ExcelDefinition excelDefinition) throws IOException {
        Workbook workBook;
        String fileExtension = excelDefinition.getFileExtension();
        InputStream inputStream = excelDefinition.getFileInputStream();
        if ("xlsx".equals(fileExtension)) {
            workBook = new XSSFWorkbook(inputStream);
        } else if ("xls".equals(fileExtension)) {
            workBook = new HSSFWorkbook(inputStream);
        } else {
            throw new ExcelAnalyzeException("文件拓展名不正确");
        }
        return workBook;
    }

    private static Sheet openSheet(ExcelDefinition excelDefinition, Workbook workbook) {
        if (workbook.getNumberOfSheets() == 0) {
            throw new ExcelAnalyzeException("工作簿中不存在工作表");
        }
        Sheet sheet;
        try {
            sheet = workbook.getSheetAt(excelDefinition.getSheetIndex());
        } catch (IllegalArgumentException e) {
            log.warn("第" + excelDefinition.getSheetIndex() + "个Sheet不存在");
            throw new ExcelAnalyzeException("工作表完整性被破坏");
        }
        return sheet;
    }

    private static List<Row> listRows(ExcelDefinition excelDefinition, Sheet sheet) {
        List<Row> rows = Lists.newArrayList();
        int startRowNum = sheet.getFirstRowNum();
        int offsetRowNum = excelDefinition.getRowOffSet() - 1;
        if (offsetRowNum >= startRowNum) {
            startRowNum = offsetRowNum;
        }
        for (int rownum = startRowNum; rownum <= sheet.getLastRowNum(); rownum++) {
            rows.add(sheet.getRow(rownum));
        }
        if (rows.size() == 0) {
            throw new ExcelAnalyzeException("工作表中没有内容");
        }
        return rows;
    }

    private static <T> T parseRow(Class<T> clazz, List<ExcelDefinition.ColumnDefinition> columnDefinitions,
            Row row) {
        T t = Abbreviation.objs.newInstance(clazz);
        for (ExcelDefinition.ColumnDefinition columnDefinition : columnDefinitions) {
            // cell在row中是从0开始的
            Cell cell = row.getCell(columnDefinition.getColumnNumber() - 1);
            String cellContent;
            if (cell == null) {
                cellContent = null;
            } else {
                cellContent = cell.toString().trim();
            }
            Field field = columnDefinition.getModelField();
            field.setAccessible(true);
            Object fieldValue = columnDefinition.getDefaultValue();
            if (StringUtil.isNotEmpty(cellContent)) {
                Formatter formatter = columnDefinition.getFormatter();
                if (formatter == null || formatter.getClass() == Formatter.class) {
                    // 没有指定formatter，尝试用缺省方式指定常用formatter
                    Class fieldType = field.getType();
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
            try {
                field.set(t, fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    private static void close(ExcelDefinition excelDefinition) {
        InputStream inputStream = excelDefinition.getFileInputStream();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {}
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

}

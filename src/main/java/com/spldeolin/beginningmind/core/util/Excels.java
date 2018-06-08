package com.spldeolin.beginningmind.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.util.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.util.excel.Formatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Excel读写工具类
 *
 * @author Deolin
 */
@UtilityClass
@Log4j2
public class Excels {

    private static final SimpleDateFormat ISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    private static final Objenesis OBJENESIS = new ObjenesisStd(true);

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(String filePath, Class<T> clazz, List<T> list) {
        writeExcel(new File(filePath), clazz, list);
    }

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(File file, Class<T> clazz, List<T> list) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (OutputStream os = new FileOutputStream(file)) {
            // 解析clazz
            Field[] fields = clazz.getDeclaredFields();
            List<Field> srcFields = new ArrayList<>();
            List<String> columnNames = new ArrayList<>();
            List<Formatter> formatters = new ArrayList<>();
            List<String> defaultValues = new ArrayList<>();
            Integer columnSize = analyzeFields(fields, srcFields, columnNames, formatters, defaultValues);
            log.info(columnSize.toString());
            log.info(srcFields.toString());
            log.info(columnNames.toString());
            log.info(formatters.toString());
            log.info(defaultValues.toString());
            Workbook workbook = new XSSFWorkbook();
            // 单元格格式（文本）
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
            Sheet sheet = workbook.createSheet(getSheetName(clazz));
            // 第一行
            Row titleRow = sheet.createRow(0);
            // 向第一行填入列名
            for (int i = 0; i < columnSize; i++) {
                Cell cell = titleRow.createCell(i + 1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnNames.get(i));
            }
            // 第二行开始
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                // 第N行
                int lineNo = i + 1;
                Row valueRow = sheet.createRow(lineNo);
                // 向N行填入序号
                Cell cell = valueRow.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(lineNo);
                // 向N行填入数据
                for (int j = 0; j < srcFields.size(); j++) {
                    Field field = srcFields.get(j);
                    field.setAccessible(true);
                    Object value = ReflectionUtils.getField(field, t);
                    String cellContent;
                    if (value == null) {
                        cellContent = defaultValues.get(j);
                    } else {
                        Formatter formatter = formatters.get(j);
                        if (formatter == null || formatter.getClass() == Formatter.class) {
                            // 默认formatter或formatter不存在
                            Class fieldType = field.getType();
                            if (fieldType == Date.class) {
                                cellContent = ISO.format((Date) value);
                            } else if (fieldType == LocalDate.class) {
                                cellContent = ((LocalDate) value).format(DateTimeFormatter.ISO_DATE);
                            } else if (fieldType == LocalTime.class) {
                                cellContent = ((LocalTime) value).format(DateTimeFormatter.ISO_TIME);
                            } else if (fieldType == LocalDateTime.class) {
                                cellContent = ((LocalDateTime) value).format(DateTimeFormatter.ISO_DATE_TIME);
                            } else {
                                // 非“时间”的情况，默认直接toString即可
                                cellContent = value.toString();
                            }
                        } else {
                            // 指定了formatter
                            cellContent = formatter.format(value);
                        }
                    }
                    cell = valueRow.createCell(j + 1);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(cellContent);
                }
            }
            workbook.write(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> String getSheetName(Class<T> clazz) {
        ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
        if (excelSheet == null) {
            throw new IllegalArgumentException("实体类未声明@ExcelSheet注解");
        }
        String sheetName = excelSheet.sheetName();
        if (StringUtils.isNotEmpty(sheetName)) {
            return sheetName;
        }
        return "Sheet1";
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return readExcel(fis, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz) {
        try {
            return readExcel(multipartFile.getInputStream(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        XSSFSheet sheet;
        try (XSSFWorkbook workBook = new XSSFWorkbook(inputStream)) {
            if (workBook.getNumberOfSheets() == 0) {
                return list;
            }
            sheet = workBook.getSheetAt(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 解析clazz
        Field[] fields = clazz.getDeclaredFields();
        List<Field> srcFields = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        List<Formatter> formatters = new ArrayList<>();
        List<String> defaultValues = new ArrayList<>();
        analyzeFields(fields, srcFields, columnNames, formatters, defaultValues);
        // 解析excel
        int rowSize = sheet.getLastRowNum();
        if (rowSize < 1) {
            return list;
        }
        Row titleRow = sheet.getRow(0);
        // 解析excel结果
        List<ParseCell> parseCells = new ArrayList<>();
        /* 第一个单元格是没有值的 */
        for (int cellIndex = 1; cellIndex < titleRow.getLastCellNum(); cellIndex++) {
            Cell cell = titleRow.getCell(cellIndex);
            String cellContent = cell.getStringCellValue();
            if (columnNames.contains(cellContent)) {
                int index = columnNames.indexOf(cellContent);
                parseCells.add(ParseCell.builder().srcField(srcFields.get(index)).formatter(
                        formatters.get(index)).cellIndex(cellIndex).build());
            }
        }
        /*
            如果最后一个有值的行号是8，sheet.getLastRowNum()只会是7
          */
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            T t = OBJENESIS.newInstance(clazz);
            boolean isAllCellEmptyThisRow = true;
            for (ParseCell parseCell : parseCells) {
                Field field = parseCell.getSrcField();
                field.setAccessible(true);
                Formatter formatter = parseCell.getFormatter();
                XSSFCell cell = row.getCell(parseCell.getCellIndex());
                if (cell == null) {
                    continue;
                } else {
                    isAllCellEmptyThisRow = false;
                }
                String cellContent = cell.getStringCellValue();
                if (StringUtils.isNotEmpty(cellContent)) {
                    try {
                        if (formatter == null || formatter.getClass() == Formatter.class) {
                            Class fieldType = field.getType();
                            if (fieldType == String.class) {
                                field.set(t, cellContent);
                            } else if (fieldType == Integer.class || "int".equals(fieldType.getTypeName())) {
                                field.set(t, NumberUtils.createInteger(cellContent));
                            } else if (fieldType == Long.class || "long".equals(fieldType.getTypeName())) {
                                field.set(t, NumberUtils.createLong(cellContent));
                            } else if (fieldType == BigInteger.class) {
                                field.set(t, NumberUtils.createBigInteger(cellContent));
                            } else if (fieldType == Float.class || "float".equals(fieldType.getTypeName())) {
                                field.set(t, NumberUtils.createFloat(cellContent));
                            } else if (fieldType == Double.class || "double".equals(fieldType.getTypeName())) {
                                field.set(t, NumberUtils.createDouble(cellContent));
                            } else if (fieldType == BigDecimal.class) {
                                field.set(t, NumberUtils.createBigDecimal(cellContent));
                            } else if (fieldType == Boolean.class || "boolean".equals(fieldType.getTypeName())) {
                                Boolean bool;
                                if (NumberUtils.isCreatable(cellContent)) {
                                    bool = BooleanUtils.toBooleanObject(Integer.parseInt(cellContent));
                                } else {
                                    bool = BooleanUtils.toBooleanObject(cellContent);
                                }
                                field.set(t, bool);
                            } else if (fieldType == Date.class) {
                                field.set(t, ISO.parse(cellContent));
                            } else if (fieldType == LocalDate.class) {
                                field.set(t, LocalDate.parse(cellContent, DateTimeFormatter.ISO_DATE));
                            } else if (fieldType == LocalTime.class) {
                                field.set(t, LocalTime.parse(cellContent, DateTimeFormatter.ISO_TIME));
                            } else if (fieldType == LocalDateTime.class) {
                                field.set(t, LocalDateTime.parse(cellContent, DateTimeFormatter.ISO_DATE_TIME));
                            } else {
                                throw new UnsupportedOperationException(
                                        "读取Excel失败，类" + clazz.getSimpleName() + "的属性" +
                                                field.getType().getClass().getSimpleName() + field.getName() +
                                                "未指定的Formater");
                            }
                        } else {
                            field.set(t, parseCell.getFormatter().parse(cellContent));
                        }
                    } catch (ParseException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // 仅有序号，没有任何内容的行不会被导入
            if (!isAllCellEmptyThisRow) {
                list.add(t);
            }
        }
        return list;
    }

    @SneakyThrows
    private static int analyzeFields(Field[] fields, List<Field> srcFields, List<String> columnNames,
            List<Formatter> formatters, List<String> defaultValues) {
        int columnSize = 0;
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null) {
                columnSize++;
                srcFields.add(field);
                columnNames.add(excelColumn.columnName());
                Class formatter = excelColumn.formatter();
                if (formatter.isInterface()) {
                    formatters.add(null);
                } else {
                    formatters.add((Formatter) formatter.newInstance());
                }
                defaultValues.add(excelColumn.defaultValue());
            }
        }
        return columnSize;
    }

    @Data
    @AllArgsConstructor
    @Builder
    private static class ParseCell {

        private Field srcField;

        private Formatter formatter;

        private Integer cellIndex;

    }

}

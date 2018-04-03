package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * Excel工具类
 *
 * @author Deolin
 */
@Log4j2
public class ExcelUtil {

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
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
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
                    Formatter formatter = formatters.get(j);
                    if (formatter == null) {
                        formatter = new DefaultFormatter();
                    }
                    Field field = srcFields.get(j);
                    field.setAccessible(true);
                    Object value = ReflectionUtils.getField(field, t);
                    String formattedValue;
                    if (value == null) {
                        formattedValue = defaultValues.get(j);
                    } else {
                        formattedValue = formatter.format(value);
                    }
                    cell = valueRow.createCell(j + 1);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(formattedValue);
                }
            }
            try {
                workbook.write(os);
            } finally {
                workbook.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> String getSheetName(Class<T> clazz) {
        ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
        if (excelSheet != null) {
            String sheetName = excelSheet.sheetName();
            if (StringUtils.isNotEmpty(sheetName)) {
                return sheetName;
            }
        }
        return "Sheet1";
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) {
        try {
            return readExcel(new FileInputStream(file), clazz);
        } catch (FileNotFoundException e) {
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
        try {
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            if (workBook.getNumberOfSheets() == 0) {
                return list;
            }
            XSSFSheet sheet = workBook.getSheetAt(0);
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
            for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                T t = clazz.newInstance();
                for (ParseCell parseCell : parseCells) {
                    Field field = parseCell.getSrcField();
                    Formatter formatter = parseCell.getFormatter();
                    String cellContent = row.getCell(parseCell.getCellIndex()).getStringCellValue();
                    if (StringUtils.isNotEmpty(cellContent)) {
                        field.setAccessible(true);
                        if (formatter.getClass() == DefaultFormatter.class) {
                            if (field.getType() == String.class) {
                                field.set(t, cellContent);
                            } else {
                                throw new UnsupportedOperationException("非String类型属性使用指定类型的Format");
                            }
                        } else {
                            field.set(t, parseCell.getFormatter().parse(cellContent));
                        }
                    }
                }
                list.add(t);
            }
        } catch (IllegalAccessException | IOException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static int analyzeFields(Field[] fields, List<Field> srcFields, List<String> columnNames,
            List<Formatter> formatters, List<String> defaultValues) {
        int columnSize = 0;
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null) {
                columnSize++;
                srcFields.add(field);
                columnNames.add(excelColumn.columnName());
                try {
                    formatters.add(excelColumn.formatter().newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
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

    public static void main(String[] args) throws ParseException {
        List<User> users = Lists.newArrayList(
                User.builder().id(123123L).build(),
                User.builder().name("Deolin").build(),
                User.builder().salt("223iosjdio23").build(),
                User.builder().sex("m").build(),
                User.builder().age(18).build(),
                User.builder().flag(true).build(),
                User.builder().ymd(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-02")).build(),
                User.builder().hms(new SimpleDateFormat("HH:mm:ss").parse("16:15:01")).build(),
                User.builder().ymdhms(new Date()).build(),
                User.builder().money(new BigDecimal("23333333.33")).build(),
                User.builder().richText("一个被强调的<strong>曲奇饼干</strong>").build(),
                User.builder().serialNumber(Long.MAX_VALUE).build(),
                User.builder().percent(100.00D).build());

        ExcelUtil.writeExcel("/woody.xlsx", User.class, users);

        log.info(ExcelUtil.readExcel(new File("/woody.xlsx"), User.class).toString());

        System.out.println("临时测试");
    }

}

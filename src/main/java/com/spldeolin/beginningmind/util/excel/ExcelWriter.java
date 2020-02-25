package com.spldeolin.beginningmind.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.spldeolin.beginningmind.util.excel.converter.CellConverter;
import com.spldeolin.beginningmind.util.excel.entity.ColumnDefinition;
import com.spldeolin.beginningmind.util.excel.entity.ExcelDefinitionContext;
import com.spldeolin.beginningmind.util.excel.entity.SheetDefinition;
import com.spldeolin.beginningmind.util.excel.exception.ExcelWriteException;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelWriter {

    private static final String utf8 = StandardCharsets.UTF_8.name();

    /**
     * 生成Excel
     */
    static <T> void writeExcel(File file, Class<T> clazz, List<T> list) throws ExcelWriteException {
        ensureFileExist(file);
        try (OutputStream os = new FileOutputStream(file)) {
            writeExcel(os, clazz, list);
        } catch (IOException e) {
            throw new ExcelWriteException(e);
        }
    }

    static <T> void writeExcel(HttpServletResponse response, String fileBaseName, Class<T> clazz, List<T> list)
            throws ExcelWriteException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileBaseName + ".xlsx", utf8));
            response.setCharacterEncoding(utf8);
            ExcelWriter.writeExcel(response.getOutputStream(), clazz, list);
        } catch (IOException e) {
            throw new ExcelWriteException("文件读写失败");
        }
    }

    private static <T> void writeExcel(OutputStream os, Class<T> clazz, List<T> list) throws IOException {
        try {
            ExcelDefinitionContext.newSheetDefinition();
            ExcelAnalyzer.analyzeModel(clazz);
            ExcelAnalyzer.analyzeModelFields(clazz);

            // 创建工作簿
            Workbook workbook = newWorkbook();

            // 创建工作表
            Sheet sheet = newSheet(workbook);

            // 写入第一行（第一行展示列名）
            writeTitleRow(sheet);

            // 单元格格式（文本） 这是干嘛的？
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("@"));

            // 写入第N行（下面的行展示数据）
            writeDataRows(cellStyle, sheet, list);

            workbook.write(os);
        } finally {
            ExcelDefinitionContext.clearAll();
        }
    }

    private static void ensureFileExist(File file) {
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

    private static Workbook newWorkbook() {
        return new XSSFWorkbook();
    }

    private static Sheet newSheet(Workbook workbook) {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        return workbook.createSheet(sheetDefinition.getSheetName());
    }

    private static void writeTitleRow(Sheet sheet) {
        Row titleRow = sheet.createRow(0);

        List<ColumnDefinition> columns = ExcelDefinitionContext.getColumnDefinitions();
        for (int i = 0; i < columns.size(); i++) {
            ColumnDefinition column = columns.get(i);
            int cellNumber = i;
            Cell cell = titleRow.createCell(cellNumber);

            String cellValue = column.getFirstColumnName();
            cell.setCellValue(cellValue);
        }
    }

    private static <T> void writeDataRows(CellStyle cellStyle, Sheet sheet, List<T> list) {
        for (int j = 0; j < list.size(); j++) {
            T t = list.get(j);
            int rowIndex = j + 1;
            Row titleRow = sheet.createRow(rowIndex);

            List<ColumnDefinition> columns = ExcelDefinitionContext.getColumnDefinitions();
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
    private static <T> String formatCellValue(ColumnDefinition columnDefinition, T t) {
        Field field = columnDefinition.getModelField();
        field.setAccessible(true);
        Object fieldValue;
        try {
            fieldValue = field.get(t);
        } catch (IllegalAccessException e) {
            // field.get() throws
            throw new RuntimeException("impossible unless bug");
        }
        if (fieldValue == null) {
            return columnDefinition.getDefaultValue();
        }

        CellConverter formatter = columnDefinition.getFormatter();
        if (formatter == null) {
            return fieldValue.toString();
        }

        return formatter.writeToCellContent(fieldValue);
    }

//    private static void close() {
//        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
//        InputStream inputStream = sheetDefinition.getFileInputStream();
//        if (inputStream != null) {
//            try {
//                inputStream.close();
//            } catch (IOException ignored) {
//            }
//        }
//    }

}

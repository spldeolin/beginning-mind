package com.spldeolin.beginningmind.core.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.spldeolin.beginningmind.core.common.BizException;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelContext;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelContext.ColumnDefinition;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelWriteException;
import com.spldeolin.beginningmind.core.util.excel.formatter.Converter;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelWriter {

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(File file, Class<T> clazz, List<T> list) {
        ensureFileExist(file);
        ExcelContext excelContext = new ExcelContext();
        try (OutputStream os = new FileOutputStream(file)) {
            ExcelAnalyzer.analyzeFile(excelContext, file);
            ExcelAnalyzer.analyzeModel(excelContext, clazz);
            ExcelAnalyzer.analyzeModelFields(excelContext, clazz);

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
        } catch (Exception e) {
            throw new ExcelWriteException(e);
        } finally {
            close(excelContext);
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

    private static Workbook newWorkbook(ExcelContext excelContext) {
        String fileExtension = excelContext.getFileExtension();
        if ("xlsx".equals(fileExtension)) {
            return new XSSFWorkbook();
        } else if ("xls".equals(fileExtension)) {
            return new HSSFWorkbook();
        } else {
            throw new BizException("文件拓展名不正确");
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

    private static <T> void writeRows(ExcelContext excelContext, CellStyle cellStyle, Sheet sheet, List<T> list)
            throws Exception {
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
    private static <T> String formatCellValue(ExcelContext.ColumnDefinition columnDefinition, T t) throws Exception {
        Field field = columnDefinition.getModelField();
        field.setAccessible(true);
        Object fieldValue = field.get(t);
        if (fieldValue == null) {
            return columnDefinition.getDefaultValue();
        }

        Converter formatter = columnDefinition.getFormatter();
        if (formatter == null) {
            return fieldValue.toString();
        }

        return formatter.write(fieldValue);
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

}

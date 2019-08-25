package com.spldeolin.beginningmind.core.util.excel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelSheet;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelContext;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelContext.ColumnDefinition;
import com.spldeolin.beginningmind.core.util.excel.formatter.Converter;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelAnalyzer {

    static void analyzeFile(ExcelContext excelContext, File file) throws IOException {
        String filename = file.getName();
        excelContext.setFileExtension(FilenameUtils.getExtension(filename));
        excelContext.setFileInputStream(FileUtils.openInputStream(file));
    }

    static void analyzeMultipartFile(ExcelContext excelContext, MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        excelContext.setFileExtension(FilenameUtils.getExtension(filename));
        excelContext.setFileInputStream(multipartFile.getInputStream());
    }

    static <T> void analyzeModel(ExcelContext excelContext, Class<T> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]未声明@ExcelSheet");
        }
        excelContext.setSheetName(sheetAnno.sheetName());
        excelContext.setRowOffSet(sheetAnno.columnTitleRowNo() + 1);
    }

    static <T> void analyzeModelFields(ExcelContext excelContext, Class<T> clazz) {
        List<ColumnDefinition> columnDefinitions = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn columnAnno = field.getAnnotation(ExcelColumn.class);
            if (columnAnno == null) {
                continue;
            }
            ExcelContext.ColumnDefinition columnDefinition = new ExcelContext.ColumnDefinition();
            columnDefinition.setFirstColumnName(columnAnno.columnTitle());
            columnDefinition.setModelField(field);
            Class<? extends Converter> formatter = columnAnno.converter();
            if (formatter != Converter.class) {
                columnDefinition.setFormatter(new ObjenesisStd(true).newInstance(formatter));
            }
            columnDefinition.setDefaultValue(columnAnno.defaultValue());
            columnDefinitions.add(columnDefinition);
        }
        if (columnDefinitions.size() == 0) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]中不存在@ExcelColumn字段");
        }
        excelContext.setColumnDefinitions(columnDefinitions);
    }

}

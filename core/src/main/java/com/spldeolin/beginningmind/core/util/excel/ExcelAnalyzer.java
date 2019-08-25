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
import com.spldeolin.beginningmind.core.util.excel.converter.CellConverter;
import com.spldeolin.beginningmind.core.util.excel.entity.ColumnDefinition;
import com.spldeolin.beginningmind.core.util.excel.entity.ExcelDefinitionContext;
import com.spldeolin.beginningmind.core.util.excel.entity.SheetDefinition;

/**
 * @author Deolin 2019-08-23
 */
public class ExcelAnalyzer {

    static void analyzeFileForRead(File file) throws IOException {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        String filename = file.getName();
        sheetDefinition.setFileExtension(FilenameUtils.getExtension(filename));
        sheetDefinition.setFileInputStream(FileUtils.openInputStream(file));
    }

    static void analyzeMultipartFile(MultipartFile multipartFile) throws IOException {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        String filename = multipartFile.getOriginalFilename();
        sheetDefinition.setFileExtension(FilenameUtils.getExtension(filename));
        sheetDefinition.setFileInputStream(multipartFile.getInputStream());
    }

    static <T> void analyzeModel(Class<T> clazz) {
        SheetDefinition sheetDefinition = ExcelDefinitionContext.getSheetDefinition();
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]未声明@ExcelSheet");
        }
        sheetDefinition.setSheetName(sheetAnno.sheetName());
        sheetDefinition.setDataRowStartNo(sheetAnno.titleRowStartNo() + 1);
    }

    static <T> void analyzeModelFields(Class<T> clazz) {
        List<ColumnDefinition> columnDefinitions = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn columnAnno = field.getAnnotation(ExcelColumn.class);
            if (columnAnno == null) {
                continue;
            }
            ColumnDefinition columnDefinition = new ColumnDefinition();
            columnDefinition.setFirstColumnName(columnAnno.columnTitle());
            columnDefinition.setModelField(field);
            Class<? extends CellConverter> formatter = columnAnno.cellConverter();
            if (formatter != CellConverter.class) {
                columnDefinition.setFormatter(new ObjenesisStd(true).newInstance(formatter));
            }
            columnDefinition.setDefaultValue(columnAnno.defaultCellContentWhenEmpty());
            columnDefinitions.add(columnDefinition);
        }
        if (columnDefinitions.size() == 0) {
            throw new RuntimeException("Model [" + clazz.getSimpleName() + "]中不存在@ExcelColumn字段");
        }
        ExcelDefinitionContext.setColumnDefinition(columnDefinitions);
    }

}

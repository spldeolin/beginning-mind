package com.spldeolin.beginningmind.util.excel.entity;

import java.util.List;

/**
 * @author Deolin 2019-08-25
 */
public class ExcelDefinitionContext {

    private static final ThreadLocal<SheetDefinition> sheetDefinition = new ThreadLocal<>();

    private static final ThreadLocal<List<ColumnDefinition>> columnDefinitions = new ThreadLocal<>();


    public static void newSheetDefinition() {
        SheetDefinition sheetDefinition = new SheetDefinition();
        ExcelDefinitionContext.sheetDefinition.set(sheetDefinition);
    }

    public static SheetDefinition getSheetDefinition() {
        return sheetDefinition.get();
    }

    public static void setColumnDefinition(List<ColumnDefinition> columnDefinition) {
        columnDefinitions.set(columnDefinition);
    }

    public static List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions.get();
    }

    public static void clearAll() {
        sheetDefinition.remove();
        columnDefinitions.remove();
    }

}

package com.spldeolin.beginningmind.core.util.excel.entity;

/**
 * @author Deolin 2019-08-25
 */
public class ExcelDefinitionContext {

    private static final ThreadLocal<SheetDefinition> sheetDefinitionThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<ColumnDefinition> columnDefinitionThreadLocal = new ThreadLocal<>();

    public static SheetDefinition getSheetDefinition() {
        return sheetDefinitionThreadLocal.get();
    }

    public static ColumnDefinition getColumnDefinition() {
        return columnDefinitionThreadLocal.get();
    }

    public static void setSheetDefinition(SheetDefinition sheetDefinition) {
        sheetDefinitionThreadLocal.set(sheetDefinition);
    }

    public static void setColumnDefinition(ColumnDefinition columnDefinition) {
        columnDefinitionThreadLocal.set(columnDefinition);
    }

    public static SheetDefinition newSheetDefinition() {
        SheetDefinition result = new SheetDefinition();
        setSheetDefinition(result);
        return result;
    }

    public static ColumnDefinition newColumnDefinition() {
        ColumnDefinition result = new ColumnDefinition();
        setColumnDefinition(result);
        return result;
    }

    public static void clearAll() {
        sheetDefinitionThreadLocal.remove();
        columnDefinitionThreadLocal.remove();
    }

}

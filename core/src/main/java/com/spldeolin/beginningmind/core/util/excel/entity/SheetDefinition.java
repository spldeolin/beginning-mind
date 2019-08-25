package com.spldeolin.beginningmind.core.util.excel.entity;

import java.io.InputStream;
import java.util.List;
import lombok.Data;

/**
 * Sheet定义，数据来自POJO上的ExcelSheet注解
 *
 * @author Deolin 2018/07/07
 */
@Data
public class SheetDefinition {

    private String fileExtension;

    private InputStream fileInputStream;

    /**
     * Sheet名
     */
    private String sheetName;

    /**
     * 从第几行开始
     */
    private Integer dataRowStartNo;

    /**
     * 列定义
     */
    private List<ColumnDefinition> columnDefinitions;

}


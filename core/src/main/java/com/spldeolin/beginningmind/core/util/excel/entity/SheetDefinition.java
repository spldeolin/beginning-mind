package com.spldeolin.beginningmind.core.util.excel.entity;

import java.io.InputStream;
import lombok.Data;

/**
 * Sheet定义，数据来自POJO上的ExcelSheet注解
 *
 * @author Deolin 2018/07/07
 */
@Data
public class SheetDefinition {

    /**
     * sheet所在Excel文件的拓展名
     */
    private String fileExtension;

    /**
     * sheet所在Excel文件
     */
    private InputStream fileInputStream;

    /**
     * Sheet名
     */
    private String sheetName;

    /**
     * 这个Sheet中，数据行从第几行开始
     */
    private Integer dataRowStartNo;

}


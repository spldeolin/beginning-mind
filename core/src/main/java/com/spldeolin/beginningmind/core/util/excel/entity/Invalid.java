package com.spldeolin.beginningmind.core.util.excel.entity;

import lombok.Data;

/**
 * @author Deolin 2018/07/09
 */
@Data
public class Invalid {

    private String columnLetter;

    private Integer rowNumber;

    private String cause;

    public Invalid(String columnLetter, Integer rowNumber, String cause) {
        this.columnLetter = columnLetter;
        this.rowNumber = rowNumber;
        this.cause = cause;
    }
}

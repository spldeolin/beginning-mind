package com.spldeolin.beginningmind.core.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Deolin 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConverterReadException extends Exception {

    private String fieldTypeName;

    private String fieldName;

    private String cellContent;

    public ConverterReadException() {
    }

    public ConverterReadException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;

}

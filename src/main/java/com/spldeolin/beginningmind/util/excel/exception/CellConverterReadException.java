package com.spldeolin.beginningmind.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Deolin 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CellConverterReadException extends Exception {

    private static final long serialVersionUID = 910574124672350016L;

    private String fieldTypeName;

    private String fieldName;

    private String cellContent;

    public CellConverterReadException() {
    }

    public CellConverterReadException(String message) {
        super(message);
    }

}

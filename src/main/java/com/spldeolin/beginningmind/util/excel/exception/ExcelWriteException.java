package com.spldeolin.beginningmind.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 读取Excel时发生异常
 *
 * @author Deolin 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExcelWriteException extends Exception {

    public ExcelWriteException(Exception e) {
        super(e);
    }

    public ExcelWriteException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 3812539018260837051L;

}

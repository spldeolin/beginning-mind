package com.spldeolin.beginningmind.core.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产Excel时发生异常
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelWriteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcelWriteException() {
        super();
    }

    public ExcelWriteException(Throwable t) {
        super(t);
    }

}
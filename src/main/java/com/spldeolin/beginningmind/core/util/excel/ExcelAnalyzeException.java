package com.spldeolin.beginningmind.core.util.excel;

import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/07/07
 */
@Log4j2
public class ExcelAnalyzeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcelAnalyzeException(String message) {
        super(message);
    }

    public ExcelAnalyzeException(Throwable cause) {
        super(cause);
    }

}
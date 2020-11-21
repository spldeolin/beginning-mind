package com.spldeolin.beginningmind.util.exception;

import com.spldeolin.beginningmind.util.CsvUtils;

/**
 * 工具类Csvs内部抛出的异常，调用方可自行决定如何处理
 *
 * @author Deolin 2020-03-04
 * @see CsvUtils
 */
public class CsvException extends RuntimeException {

    private static final long serialVersionUID = 8247500390534094194L;

    public CsvException() {
        super();
    }

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvException(Throwable cause) {
        super(cause);
    }

    protected CsvException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

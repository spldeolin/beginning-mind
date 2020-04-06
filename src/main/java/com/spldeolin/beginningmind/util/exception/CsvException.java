package com.spldeolin.beginningmind.util.exception;

import com.spldeolin.beginningmind.util.CsvUtils;

/**
 * 工具类Csvs遇到预想以外的情况或是内部异常时，
 * 会抛出这个异常，以交给调用方决定如何处理
 *
 * @see CsvUtils
 * @author Deolin 2020-03-04
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

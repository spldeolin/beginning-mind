package com.spldeolin.beginningmind.util.exception;

import com.spldeolin.beginningmind.util.QrCodeUtils;

/**
 * 工具类QrCodes遇到预想以外的情况或是内部异常时，
 * 会抛出这个异常，以交给调用方决定如何处理
 *
 * @author Deolin 2020-03-05
 * @see QrCodeUtils
 */
public class QrCodeException extends RuntimeException {

    private static final long serialVersionUID = -2485299435288378313L;

    public QrCodeException() {
        super();
    }

    public QrCodeException(String message) {
        super(message);
    }

    public QrCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodeException(Throwable cause) {
        super(cause);
    }

    protected QrCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

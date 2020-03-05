package com.spldeolin.beginningmind.util.exception;

/**
 * 工具类QrCodes遇到预想以外的情况或是内部异常时，
 * 会抛出这个异常，以交给调用方决定如何处理
 *
 * @see com.spldeolin.beginningmind.util.QrCodes
 * @author Deolin 2020-03-05
 */
public class QrCodesException extends RuntimeException {

    private static final long serialVersionUID = -2485299435288378313L;

    public QrCodesException() {
        super();
    }

    public QrCodesException(String message) {
        super(message);
    }

    public QrCodesException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodesException(Throwable cause) {
        super(cause);
    }

    protected QrCodesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

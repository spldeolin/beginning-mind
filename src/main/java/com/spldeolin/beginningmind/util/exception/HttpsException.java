package com.spldeolin.beginningmind.util.exception;

/**
 * 工具类Https遇到预想以外的情况或是内部异常时，
 * 会抛出这个异常，以交给调用方决定如何处理
 *
 * @see com.spldeolin.beginningmind.util.Https
 * @author Deolin 2020-03-05
 */
public class HttpsException extends RuntimeException {

    private static final long serialVersionUID = -2702259454541236423L;

    public HttpsException() {
        super();
    }

    public HttpsException(String message) {
        super(message);
    }

    public HttpsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpsException(Throwable cause) {
        super(cause);
    }

    protected HttpsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

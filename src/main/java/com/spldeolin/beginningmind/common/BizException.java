package com.spldeolin.beginningmind.common;

/**
 * @author Deolin
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
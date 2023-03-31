package com.spldeolin.beginningmind.exception;

/**
 * @author Deolin
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -4104806330438981374L;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
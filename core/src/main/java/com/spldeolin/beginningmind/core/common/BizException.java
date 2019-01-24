package com.spldeolin.beginningmind.core.common;

/**
 * @author Deolin
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
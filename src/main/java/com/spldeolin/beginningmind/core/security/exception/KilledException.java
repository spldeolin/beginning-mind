package com.spldeolin.beginningmind.core.security.exception;

/**
 * 代表登录者已被请离
 *
 * @author Deolin 2018/05/18
 */
public class KilledException extends UnsignedException {

    private static final long serialVersionUID = 1L;

    public KilledException(String message) {
        super(message);
    }

}

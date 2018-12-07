package com.spldeolin.beginningmind.core.security.exception;

/**
 * 代表登录者没权限（请求时未通过鉴权）
 *
 * @author Deolin 2018/05/18
 */
public class UnauthorizeException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnauthorizeException(String message) {
        super(message);
    }

}

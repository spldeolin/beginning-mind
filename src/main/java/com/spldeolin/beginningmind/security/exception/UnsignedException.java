package com.spldeolin.beginningmind.security.exception;

/**
 * 代表未登录者请求需要登录的请求时的异常，由UrlForwardToExceptionController抛出，由统一异常处理捕获
 *
 * @author Deolin 2018/05/18
 */
public class UnsignedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsignedException(String message) {
        super(message);
    }

}

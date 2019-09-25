package com.spldeolin.beginningmind.security.exception;

/**
 * 代表未登录时请求要求登录态的请求
 *
 * @author Deolin 2018/05/18
 */
public class UnsignedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsignedException(String message) {
        super(message);
    }

}

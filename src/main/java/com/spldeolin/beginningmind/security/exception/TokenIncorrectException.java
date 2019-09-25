package com.spldeolin.beginningmind.security.exception;

/**
 * 代表TOKEN不正确
 *
 * @author Deolin 2018/05/18
 */
public class TokenIncorrectException extends UnsignedException {

    private static final long serialVersionUID = 1L;

    public TokenIncorrectException(String message) {
        super(message);
    }

}

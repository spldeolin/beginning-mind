package com.spldeolin.beginningmind.aspect.exception;

/**
 * 代表HTTP404的异常，由UrlForwardToExceptionController抛出，由统一异常处理捕获
 *
 * @author Deolin 2018/05/17
 */
public class RequestNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}

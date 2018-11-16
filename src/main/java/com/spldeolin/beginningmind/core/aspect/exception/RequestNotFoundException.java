package com.spldeolin.beginningmind.core.aspect.exception;

/**
 * 代表HTTP404的异常
 *
 * 由BasicErrorController判断当前请求是否404后，由ErrorControllerAspect解析并抛出，最终交给统一异常处理
 *
 * @author Deolin 2018/11/16
 */
public class RequestNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}

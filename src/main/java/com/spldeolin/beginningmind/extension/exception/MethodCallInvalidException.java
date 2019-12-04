package com.spldeolin.beginningmind.extension.exception;

/**
 * 切面MethodCallValidatedAspect中抛出的异常，是个内部错误
 * 抛出前已进行了log.error，外层无需当作未知的内部错误来处理
 *
 * @author Deolin 2019-12-04
 */
public class MethodCallInvalidException extends RuntimeException {

    private static final long serialVersionUID = -719334768446155035L;

}

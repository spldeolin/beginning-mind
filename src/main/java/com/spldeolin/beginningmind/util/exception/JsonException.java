package com.spldeolin.beginningmind.util.exception;

import com.spldeolin.beginningmind.util.JsonUtils;

/**
 * 工具类Jsons内部抛出的异常，调用方可自行决定如何处理
 *
 * @author Deolin 2020-03-05
 * @see JsonUtils
 */
public class JsonException extends RuntimeException {

    private static final long serialVersionUID = 2506389302288058433L;

    public JsonException() {
        super();
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }

    protected JsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

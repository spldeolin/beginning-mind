package com.spldeolin.beginningmind.core.util;

import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

/**
 * 工具类：简化通过RequestContextUtils获取HttpServletRequest进行操作的代码量
 *
 * @author Deolin
 */
@UtilityClass
public class Requests {

    public static HttpServletRequest request() {
        return RequestContextUtils.request();
    }

    public static void set(String key, Object value) {
        request().setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) request().getAttribute(key);
    }

    public static void remove(String key) {
        request().removeAttribute(key);
    }

}

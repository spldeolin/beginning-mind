package com.spldeolin.beginningmind.core.util;

import javax.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

/**
 * 工具类：简化HttpSession存取Attribute的代码量
 *
 * @author Deolin
 */
@UtilityClass
public class Sessions {

    public static HttpSession session() {
        return RequestContextUtils.request().getSession();
    }

    public static void set(String key, Object value) {
        session().setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) session().getAttribute(key);
    }

    public static void remove(String key) {
        session().removeAttribute(key);
    }

}

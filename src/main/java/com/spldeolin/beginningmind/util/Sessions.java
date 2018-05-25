package com.spldeolin.beginningmind.util;

import lombok.experimental.UtilityClass;

/**
 * 工具类：简化HttpSession存取Attribute的代码量
 *
 * @author Deolin
 */
@UtilityClass
public class Sessions {

    public static void set(String key, Object value) {
        RequestContextUtils.session().setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) RequestContextUtils.session().getAttribute(key);
    }

}

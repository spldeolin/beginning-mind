package com.spldeolin.beginningmind.util;

import lombok.experimental.UtilityClass;

/**
 * 将null转化为其他默认值
 *
 * @author Deolin
 */
@UtilityClass
public class Nulls {

    public static Integer nullToZero(Integer i) {
        if (null == i) {
            return 0;
        }
        return i;
    }

    public static Long nullToZero(Long l) {
        if (null == l) {
            return 0L;
        }
        return l;
    }

    public static String nulltoEmpty(String s) {
        if (null == s) {
            return "";
        }
        return s;
    }

}

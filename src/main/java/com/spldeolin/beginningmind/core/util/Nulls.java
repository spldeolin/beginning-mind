package com.spldeolin.beginningmind.core.util;

import java.util.List;
import com.google.common.collect.Lists;

/**
 * 将null转化为其他默认值
 *
 * @author Deolin
 */
public class Nulls {

    public static Integer toZero(Integer i) {
        if (null == i) {
            return 0;
        }
        return i;
    }

    public static Long toZero(Long l) {
        if (null == l) {
            return 0L;
        }
        return l;
    }

    public static String toEmpty(String s) {
        if (null == s) {
            return "";
        }
        return s;
    }

    public static Boolean toFalse(Boolean b) {
        if (null == b) {
            return Boolean.FALSE;
        }
        return b;
    }

    public static <T> List<T> toEmpty(List<T> list) {
        if (null == list) {
            return Lists.newArrayList();
        }
        return list;
    }

}

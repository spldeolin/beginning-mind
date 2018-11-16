package com.spldeolin.beginningmind.core.generator.util;

public class StringCaseUtils {

    /**
     * @param s 任意字符串
     * @return 第一个字符大写的字符串
     */
    public static String upperFirstChar(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * @param s 任意字符串
     * @return 字符串中是否有大写字母
     */
    public static boolean hasUpperCase(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param s 驼峰分割的字符串
     * @return 下划线分割的字符串
     */
    public static String camelToSnake(String s) {
        StringBuilder sb = new StringBuilder(400);
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        if (sb.charAt(0) == '_') {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * @param s 蛇形字符串
     * @return 首字母小写的驼峰形式的字符串
     */
    public static String snakeToLowerCamel(String s) {
        StringBuilder sb = new StringBuilder(400);
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '_') {
                if (++i < length) {
                    sb.append(Character.toUpperCase(s.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.charAt(0) == '_') {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * @param s 蛇形字符串
     * @return 首字母大写的驼峰形式的字符串
     */
    public static String snakeToUpperCamel(String s) {
        char[] c = snakeToLowerCamel(s).toCharArray();
        c[0] -= 32;
        return String.valueOf(c);
    }

}

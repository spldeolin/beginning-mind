package com.spldeolin.beginningmind.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机字符串
 *
 * @author Deolin
 */
public class StringRandomUtils {

    private static final char[] LOW_EN_NUM = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};

    private static final char[] EASY_READ_AND_SPEAK = new char[]{'3', '4', '5', '7', '8', 'a', 'c', 'f', 'g', 'h', 'j',
            'k', 'p', 's', 't', 'u', 'v', 'w', 'x', 'y'};

    private StringRandomUtils() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 随机选取可见ASCII字符，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateVisibleAscii(int stringLength) {
        return RandomStringUtils.randomAscii(stringLength);
    }

    /**
     * 随机选取大小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateEnNum(int stringLength) {
        return RandomStringUtils.random(stringLength, true, true);
    }

    /**
     * 随机选取小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateLowEnNum(int stringLength) {
        return RandomStringUtils.random(stringLength, LOW_EN_NUM);
    }

    /**
     * 随机选取数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateNum(int stringLength) {
        return RandomStringUtils.randomNumeric(stringLength);
    }

    /**
     * 随机选取发音或者书写时不容易混淆的小写英文字母和数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateEasyReadAndSpeakChar(int stringLength) {
        return RandomStringUtils.random(stringLength, EASY_READ_AND_SPEAK);
    }

    /**
     * 随机生成汉字
     */
    public static String generateHanzi(int stringLength) {
        StringBuilder sb = new StringBuilder(64);
        for (int i = 0; i < stringLength; i++) {
            sb.append((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        }
        return sb.toString();
    }

}

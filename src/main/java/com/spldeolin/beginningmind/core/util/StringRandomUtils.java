package com.spldeolin.beginningmind.core.util;

import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import lombok.experimental.UtilityClass;

/**
 * 随机字符串
 */
@UtilityClass
public class StringRandomUtils {

    private static final char[] LOW_EN_CHARS;

    private static final char[] HIGH_EN_CHARS;

    private static final char[] NUM_CHARS;

    private static final char[] EN_CHARS;

    private static final char[] EN_NUM_CHARS;

    private static final char[] LOW_EN_NUM_CHARS;

    private static final char[] LEGIBLE_EN_NUM_CHARS;

    static {
        LOW_EN_CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        HIGH_EN_CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        NUM_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        EN_CHARS = ArrayUtils.addAll(LOW_EN_CHARS, HIGH_EN_CHARS);

        EN_NUM_CHARS = ArrayUtils.addAll(EN_CHARS, NUM_CHARS);

        LOW_EN_NUM_CHARS = ArrayUtils.addAll(LOW_EN_CHARS, NUM_CHARS);

        LEGIBLE_EN_NUM_CHARS = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    /**
     * 随机选取可见ASCII字符，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateVisibleAscii(int stringLength) {
        StringBuilder sb = new StringBuilder(stringLength);
        Random r = new Random();
        for (int i = 0; i < stringLength; i++) {
            char c = (char) (33 + r.nextInt(94));
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 随机选取大小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateEnNum(int stringLength) {
        return generate(EN_NUM_CHARS, stringLength);
    }

    /**
     * 随机选取小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateLowEnNum(int stringLength) {
        return generate(LOW_EN_NUM_CHARS, stringLength);
    }

    /**
     * 随机选取数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateNum(int stringLength) {
        return generate(NUM_CHARS, stringLength);
    }

    /**
     * 随机选取除了o、l、i的小写英文字母和除了0、1的数字，生成易读的字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateLegibleEnNum(int stringLength) {
        return generate(LEGIBLE_EN_NUM_CHARS, stringLength);
    }

    private static String generate(char[] chars, int stringLength) {
        StringBuilder sb = new StringBuilder(stringLength);
        Random r = new Random();
        for (int i = 0; i < stringLength; i++) {
            char c = chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

}

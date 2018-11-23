package com.spldeolin.beginningmind.core.util;

import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import lombok.experimental.UtilityClass;

/**
 * 随机字符串
 *
 * @author Deolin
 */
@UtilityClass
public class StringRandomUtils {

    private static final char[] LOW_EN;

    private static final char[] UP_EN;

    private static final char[] NUM;

    private static final char[] EN;

    private static final char[] EN_NUM;

    private static final char[] LOW_EN_NUM;

    private static final char[] LEGIBLE_EN_NUM_CHARS;

    static {
        LOW_EN = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        UP_EN = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        NUM = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        EN = ArrayUtils.addAll(LOW_EN, UP_EN);

        EN_NUM = ArrayUtils.addAll(EN, NUM);

        LOW_EN_NUM = ArrayUtils.addAll(LOW_EN, NUM);

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
        return generate(EN_NUM, stringLength);
    }

    /**
     * 随机选取小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateLowEnNum(int stringLength) {
        return generate(LOW_EN_NUM, stringLength);
    }

    /**
     * 随机选取数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateNum(int stringLength) {
        return generate(NUM, stringLength);
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

    public static void main(String[] args) {
        System.out.println(generateLowEnNum(8));
    }

}

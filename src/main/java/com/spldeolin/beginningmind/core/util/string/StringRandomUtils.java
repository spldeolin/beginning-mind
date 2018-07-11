package com.spldeolin.beginningmind.core.util.string;

import java.util.Random;
import lombok.experimental.UtilityClass;

/**
 * 随机字符串
 */
@UtilityClass
public class StringRandomUtils {

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
        return generate(new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'}, stringLength);
    }

    /**
     * 随机选取小写英文数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateLowEnNum(int stringLength) {
        return generate(new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'},
                stringLength);
    }

    /**
     * 随机选取数字，生成字符串
     *
     * @param stringLength 字符串长度
     * @return 随机字符串
     */
    public static String generateNum(int stringLength) {
        return generate(new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, stringLength);
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

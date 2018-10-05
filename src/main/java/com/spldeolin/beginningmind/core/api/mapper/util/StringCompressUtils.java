package com.spldeolin.beginningmind.core.api.mapper.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/09
 */
@UtilityClass
@Log4j2
public class StringCompressUtils {

    public static void main(String[] args) {
        log.info("|" + trimUnnecessaryBlanks("  \n" +
                "    a    \r\n     b    \n" +
                "    ") + "|");
    }

    public static String trimUnnecessaryBlanks(CharSequence sb) {
        return trimUnnecessaryBlanks(sb.toString());
    }

    public static String trimUnnecessaryBlanks(String string) {
        // br to space
        string = string.replaceAll("\\r\\n", " ").replaceAll("\\n", " ");
        // trim head spaces and tail spaces
        string = string.trim();
        // multi spaces to ONE space
        string = string.replaceAll(" +", " ");
        return string;
    }

}

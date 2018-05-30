package com.spldeolin.beginningmind.core.aspect.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class ProcessingTimeLogger {

    private static final String SEP = "\t\t";

    public static void logProcessingTime(String insignia, long time) {
        log.info("[Java] 请求执行时间：" + time + SEP + insignia);
    }

}

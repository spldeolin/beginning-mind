package com.spldeolin.beginningmind.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * java.util.Date类型的工具类
 * <pre>
 * 用于简化Date与LocalDate、LocalTime、LocalDateTime之间互相转化的代码量，
 * 转化涉及到的时区一律为系统所在时区。
 * </pre>
 *
 * @author Deolin 2018/05/23
 */
@UtilityClass
public class Dates {

    public static final ZoneId systemZone = ZoneId.systemDefault();

    /**
     * java.util.Date转化为LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), systemZone);
    }

    /**
     * java.util.Date转化为LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * java.util.Date转化为LocalDate
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * LocalDateTime转化为java.util.Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(systemZone).toInstant());
    }

    /**
     * LocalDate转化为java.util.Date（时分秒缺省为00:00:00）
     */
    public static Date toDate(LocalDate localDate) {
        LocalDateTime startTimeOfDay = localDate.atStartOfDay();
        return toDate(startTimeOfDay);
    }

    /**
     * LocalTime转化为java.util.Date（年月日缺省为1970-01-01）
     */
    public static Date toDate(LocalTime localTime) {
        LocalDateTime timeOfStartDay = LocalDateTime.of(LocalDate.of(1970, 1, 1), localTime);
        return toDate(timeOfStartDay);
    }

}

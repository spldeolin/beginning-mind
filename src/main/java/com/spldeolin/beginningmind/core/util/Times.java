package com.spldeolin.beginningmind.core.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * “时间”类型工具类
 * <pre>
 * 工具类涉及java.util包和java.time包下的4种“时间”类型。
 * 简化Date转化为LocalDate、LocalTime、LocalDateTime的代码量，涉及到的时区一律为系统所在时区；
 * 简化四种“时间”类型转化为String类型的代码量；
 * 不提供返回类型为Date的方法，因为不再建议使用Date类型
 * </pre>
 *
 * @author Deolin 2018/05/23
 */
@UtilityClass
public class Times {

    public static final ZoneId systemZone = ZoneId.systemDefault();

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /*
        java.util.Date相关
     */

    /**
     * Date转化为LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), systemZone);
    }

    /**
     * Date转化为LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * Date转化为LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * LocalDateTime转化为Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /*
        格式化相关
     */

    /**
     * LocalDateTime转化为String（yyyy-MM-dd HH:mm:ss）
     */
    public static String toString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(DATETIME_PATTERN).format(localDateTime);
    }

    /**
     * LocalDateTime转化为String
     */
    public static String toString(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * LocalDate转化为String（yyyy-MM-dd）
     */
    public static String toString(LocalDate localDate) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDate);
    }

    /**
     * LocalDate转化为String
     */
    public static String toString(LocalDate localDate, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    /**
     * LocalTime转化为String（HH:mm:ss）
     */
    public static String toString(LocalTime localTime) {
        return DateTimeFormatter.ofPattern(TIME_PATTERN).format(localTime);
    }

    /**
     * LocalTime转化为String
     */
    public static String toString(LocalTime localTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localTime);
    }

    /*
        UNIX时间戳相关
     */

    /**
     * UNIX时间戳转化为LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long unixTimeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimeStamp), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转化为UNIX时间戳
     */
    public static long toUnixTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(systemZone).toInstant().getEpochSecond();
    }

}

package com.spldeolin.beginningmind.util;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.spldeolin.beginningmind.exception.BizException;
import lombok.extern.log4j.Log4j2;

/**
 * JSON工具类
 * <pre>
 * 支持JSON 与对象间、与对象列表间 的互相转换。
 * p.s.: 本工具类所有public方法都是 OR-ELSE-THROW 的
 * </pre>
 *
 * @author Deolin
 */
@Log4j2
public class Jsons {

    private static final ObjectMapper defaultObjectMapper;

    static {
        defaultObjectMapper = newDefaultObjectMapper();
    }

    public static ObjectMapper newDefaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();

        // 模组（guava collection、jsr310、Long转String）
        om.registerModule(new GuavaModule());
        om.registerModule(longToStringModule());
        om.registerModule(timeModule());

        // json -> object时，忽略json中不认识的属性名
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 时区
        om.setTimeZone(TimeZone.getDefault());
        return om;
    }

    public static SimpleModule timeModule() {
        SimpleModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter date = Times.DEFAULT_DATE_FORMATTER;
        DateTimeFormatter time = Times.DEFAULT_TIME_FORMATTER;
        DateTimeFormatter dateTime = Times.DEFAULT_DATE_TIME_FORMATTER;
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(date))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(date))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(time))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(time))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTime))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTime));
        return javaTimeModule;
    }

    private static SimpleModule longToStringModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return simpleModule;
    }

    /**
     * 美化JSON
     * <pre>
     * e.g.:
     * {
     *   "name" : "Deolin",
     *   "age" : 18,
     *   "isVip" : true
     * }
     * </pre>
     */
    public static String beautify(Object object) {
        try {
            return defaultObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("转化JSON失败", e);
            throw new BizException("转化JSON失败");
        }
    }

    /**
     * 压缩JSON
     * <pre>
     * e.g.:
     * {"name":"Deolin","age":18,"isVip":true}
     * </pre>
     */
    public static String compress(String json) {
        return json.replace(" ", "").replace("\r", "").replace("\n", "").replace("\t", "");
    }

    /**
     * 将对象转化为JSON
     */
    public static String toJson(Object object) {
        return toJson(object, defaultObjectMapper);
    }

    /**
     * 将对象转化为JSON，支持自定义ObjectMapper
     */
    public static String toJson(Object object, ObjectMapper om) {
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("转化JSON失败", e);
            throw new BizException("转化JSON失败");
        }
    }

    /**
     * 将JSON转化为对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return toObject(json, clazz, defaultObjectMapper);
    }

    /**
     * 将JSON转化为对象，支持自定义ObjectMapper
     */
    public static <T> T toObject(String json, Class<T> clazz, ObjectMapper om) {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            log.error("转化对象失败", e);
            throw new BizException("转化对象失败");
        }
    }

    /**
     * 将JSON转化为对象列表
     */
    public static <T> List<T> toListOfObjects(String json, Class<T> clazz) {
        return toListOfObjects(json, clazz, defaultObjectMapper);
    }

    /**
     * 将JSON转化为对象列表，支持自定义ObjectMapper
     */
    public static <T> List<T> toListOfObjects(String json, Class<T> clazz, ObjectMapper om) {
        // ObjectMapper.TypeFactory没有线程隔离，所以需要new一个默认ObjectMapper
        JavaType javaType = newDefaultObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return om.readValue(json, javaType);
        } catch (IOException e) {
            log.error("转化对象列表失败", e);
            throw new BizException("转化对象失败");
        }

    }

}
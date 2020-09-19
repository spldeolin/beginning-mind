package com.spldeolin.beginningmind.util;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.util.exception.JsonException;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON工具类
 *
 * <pre>
 * 特性：
 * 1. 支持Guava的数据结构，如Multimap、Table等
 * 2. 反系列化时，忽略JSON中提供了而Javabean中不存在的属性，不抛出异常
 * 3. 序列化时，支持将Long、long、BigInteger类型转化为String，作用范围可指定
 *    （可通过@JsonSerializer覆盖这个特性）
 * 4. 支持Java8 time包下的LocalDate、LocalTime、LocalDateTime，缺省pattern分别为"yyyy-MM-dd"、"HH:mm:ss"、"yyyy-MM-dd HH:mm:ss"
 *    （可通过@JsonFormat覆盖这个特性）
 * 5. java.util.Date的缺省pattern为yyyy-MM-dd HH:mm:ss
 *    （可通过@JsonFormat覆盖这个特性）
 * 6. 时区默认为东8区
 *    （可通过@JsonFormat覆盖这个特性）
 * 7. 反序列化时，忽略Collection中为null的元素，不add(null)到容器对象中
 * 8. 反序列化时，对每个String进行trim
 *    （可通过@JsonDeserializer覆盖这个特性）
 * </pre>
 *
 * @author Deolin 2018-04-02
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper om = ObjectMapperUtils.initDefault(new ObjectMapper());

    private JsonUtils() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 将对象转化为JSON
     */
    public static String toJson(Object object) {
        return toJson(object, om);
    }

    /**
     * 将对象转化为JSON
     */
    public static String toJson(Object object, ObjectMapper om) {
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object={}", object, e);
            throw new JsonException(e);
        }
    }

    /**
     * 将对象转化为JSON，结果是美化的
     */
    public static String toJsonPrettily(Object object) {
        return toJsonPrettily(object, om);
    }

    /**
     * 将对象转化为JSON，结果是美化的
     */
    public static String toJsonPrettily(Object object, ObjectMapper om) {
        try {
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object={}", object, e);
            throw new JsonException("转化JSON失败");
        }
    }

    /**
     * 将JSON转化为对象
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return toObject(json, clazz, om);
    }

    /**
     * 将JSON转化为对象
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static <T> T toObject(String json, Class<T> clazz, ObjectMapper om) {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            log.error("json={}, clazz={}", json, clazz, e);
            throw new JsonException(e);
        }
    }

    /**
     * 将JSON转化为对象列表
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static <T> List<T> toListOfObject(String json, Class<T> clazz) {
        return toListOfObject(json, clazz, om);
    }

    /**
     * 将JSON转化为对象列表
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */

    public static <T> List<T> toListOfObject(String json, Class<T> clazz, ObjectMapper om) {
        try {
            @SuppressWarnings("unchecked") Class<T[]> arrayClass = (Class<T[]>) Class
                    .forName("[L" + clazz.getName() + ";");
            return Lists.newArrayList(om.readValue(json, arrayClass));
        } catch (IOException | ClassNotFoundException e) {
            log.error("json={}, clazz={}", json, clazz, e);
            throw new JsonException(e);
        }
    }

    /**
     * JSON -> 参数化的对象
     *
     * 示例： Collection<<User<UserAddress>> users = JsonUtils.toParameterizedObject(text);
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static <T> T toParameterizedObject(String json, TypeReference<T> typeReference) {
        return toParameterizedObject(json, typeReference, om);
    }

    /**
     * JSON -> 参数化的对象
     *
     * 示例： Collection<<User<UserAddress>> users = JsonUtils.toParameterizedObject(text);
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static <T> T toParameterizedObject(String json, TypeReference<T> typeReference, ObjectMapper om) {
        try {
            return om.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("json={}, typeReference={}", json, typeReference, e);
            throw new JsonException(e);
        }
    }

    /**
     * JSON -> JsonNode对象
     *
     * <strong>除非JSON对应数据结构在运行时是变化的，否则不建议使这个方法</strong>
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static JsonNode toTree(String json) {
        return toTree(json, om);
    }

    /**
     * JSON -> JsonNode对象
     *
     * <strong>除非JSON对应数据结构在运行时是变化的，否则不建议使这个方法</strong>
     *
     * @throws JsonException 任何原因转化失败时，抛出这个异常，如果需要补偿处理，可以进行捕获
     */
    public static JsonNode toTree(String json, ObjectMapper om) {
        try {
            return om.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("json={}", json, e);
            throw new JsonException(e);
        }
    }

}
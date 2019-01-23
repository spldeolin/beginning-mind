package com.spldeolin.beginningmind.core.util;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
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
import com.spldeolin.beginningmind.core.api.BizException;
import lombok.extern.log4j.Log4j2;

/**
 * JSON工具类
 * <pre>
 * 支持JSON与对象间的互相转换，支持取得JSON结构中的具体的值。
 * p.s.: 本工具类不建议使用Java Collection中的类作为toObject()的返回值类型，
 * 如果仅仅需要临时取某个属性，推荐使用getValue()。
 * </pre>
 *
 * @author Deolin
 */
@Log4j2
public class Jsons {

    public static final ObjectMapper defaultObjectMapper;

    static {
        defaultObjectMapper = new ObjectMapper();

        // 模组（guava collection、jsr310、Long转String）
        defaultObjectMapper.registerModule(new GuavaModule());
        defaultObjectMapper.registerModule(longToStringModulel());
        defaultObjectMapper.registerModule(timeModule());

        // json -> object时，忽略json中不认识的属性名
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 时区
        defaultObjectMapper.setTimeZone(TimeZone.getDefault());
    }

    public static SimpleModule timeModule() {
        SimpleModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(date))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(date))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(time))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(time))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTime))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTime));
        return javaTimeModule;
    }

    private static SimpleModule longToStringModulel() {
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
     * 将对象转化为JSON，采用默认SNAKE-CASE转化方式
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
     * 将JSON转化为对象，采用默认SNAKE-CASE转化方式
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
     * 读取JSON中的某一个值
     * <pre>
     * 这个方法返回的是String格式。
     * 建议使用这个方法来方便地取得目标值，而不是转化为临时的Map对象后通过map.get("key")取得。
     * 例如，想要从下面的JSON从取到第二个user的id，可以调用
     * Jsons.getValue(Integer.class, json, "users", "1", "user_id");
     *
     * {
     *      "organization_id": "112",
     *      "users": [
     *          {
     *              "user_id": "1",
     *              "name": "Light Deolin"
     *          },
     *          {
     *              "user_id": "2",
     *              "name": "Dark Deolin"
     *          }
     *      ]
     *  }
     * </pre>
     *
     * @param json 目标JSON
     * @param nodeKeys 抵达目标节点所有的节点key或数组下标
     * @return 目标值
     */
    public static String getValue(String json, String... nodeKeys) {
        JsonNode node;
        try {
            node = defaultObjectMapper.readTree(json);
        } catch (Exception e) {
            log.error("读取JSON失败", e);
            throw new BizException("读取JSON失败");
        }

        for (String nodeKey : nodeKeys) {
            if (node == null) {
                return null;
            }
            if (NumberUtils.isCreatable(nodeKey)) {
                node = node.get(Integer.parseInt(nodeKey));
            } else {
                node = node.get(nodeKey);
            }
        }
        if (node == null) {
            return null;
        }
        return node.asText();
    }

}
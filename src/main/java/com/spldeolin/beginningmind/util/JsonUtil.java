package com.spldeolin.beginningmind.util;

import java.io.IOException;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * JSON工具类
 * <pre>
 * 支持JSON与对象间的互相转换，支持取得JSON结构中的具体的值。
 * p.s.: 本工具类不建议使用Java Collection作为toObject()的返回值类型，
 * 而是推荐使用getValue()
 * </pre>
 *
 * @author Deolin
 */
@UtilityClass
@Log4j2
public class JsonUtil {

    private static ObjectMapper om;

    static {
        om = new ObjectMapper();
    }

    /**
     * 将对象转化为字符串
     */
    public static String toJson(Object object) {
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new RuntimeException("JsonUtil.toJson()发生异常。");
        }
    }

    /**
     * 将JSON字符串转化为对象
     */
    public static <T> T toObject(Class<T> clazz, String json) {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException("JsonUtil.toObject()发生异常。");
        }
    }

    /**
     * 读取JSON中的某一个值
     * <pre>
     * 这个方法返回的是String格式。
     * 建议使用这个方法来方便地取得目标值，而不是转化为临时的Map对象后通过map.get("key")取得。
     * 例如，想要从下面的JSON从取到第二个user的id，可以调用
     * JsonUtil.getValue(Integer.class, json, "users", "1", "user_id");
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
     * @param json 目标JSON字符串
     * @param nodeKeys 抵达目标节点所有的节点key或数组下标
     * @return 目标值
     */
    public static String getValue(String json, String... nodeKeys) {
        try {
            JsonNode node = om.readTree(json);
            for (String nodeKey : nodeKeys) {
                if (node == null) {
                    break;
                }
                if (NumberUtils.isCreatable(nodeKey)) {
                    Integer index = Integer.valueOf(nodeKey);
                    node = node.get(index);
                } else {
                    node = node.get(nodeKey);
                }
            }
            return node.asText();
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException("JsonUtil.getValue()发生异常。");
        }
    }

}


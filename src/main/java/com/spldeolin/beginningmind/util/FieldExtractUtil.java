package com.spldeolin.beginningmind.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ReflectionUtils;
import lombok.experimental.UtilityClass;

/**
 * 抽取对象中的属性值
 */
@UtilityClass
public class FieldExtractUtil {

    /**
     * 抽取对象集合中所有的ID属性
     *
     * @param objects 对象集合
     * @return 抽出的ID集合
     */
    public static List<Long> extractId(List<?> objects) {
        if (objects.size() == 0) {
            return new ArrayList<>();
        }
        Field id = ReflectionUtils.findField(objects.get(0).getClass(), "id");
        if (id == null) {
            return new ArrayList<>();
        }
        id.setAccessible(true);
        List<Long> ids = new ArrayList<>();
        for (Object object : objects) {
            ids.add((Long) ReflectionUtils.getField(id, object));
        }
        return ids;
    }

    /**
     * 抽取对象集合中所有的指定属性
     *
     * @param objects 对象集合
     * @param fieldName 被抽取属性的字段名
     * @param fieldType 被抽取属性的类型
     * @return 抽出的属性集合
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> extractField(List<?> objects, String fieldName, Class<T> fieldType) {
        if (objects.size() == 0) {
            return new ArrayList<>();
        }
        Field field = ReflectionUtils.findField(objects.get(0).getClass(), fieldName);
        if (field == null) {
            return new ArrayList<>();
        }
        if (fieldType != field.getType()) {
            throw new IllegalArgumentException("抽取属性的字段名与类型不匹配");
        }
        field.setAccessible(true);
        List<T> values = new ArrayList<>();
        for (Object object : objects) {
            values.add((T) ReflectionUtils.getField(field, object));
        }
        return values;
    }

}
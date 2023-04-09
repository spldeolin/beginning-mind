package com.spldeolin.beginningmind.app.util;

import com.google.common.collect.Maps;
import jodd.exception.UncheckedException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Deolin 2023-04-01
 */
public class ReasonableUtils {

    private ReasonableUtils() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 对参数javabean进行合理化
     */
    public static void reasonable(Object javabean) {
        if (javabean == null) {
            return;
        }
        makeStringReasonable(javabean);
        makeContainerReasonable(javabean);
    }

    /**
     * 字符串合理化处理——确保每个String只有null和有字符2种情况，简化safe判断
     * <p>
     * 对参数javabean及其各Nest层次的String对象，进行trim处理和emptyToNull处理
     */
    private static void makeStringReasonable(Object javabean) {
        if (javabean == null) {
            return;
        }
        Class<?> clazz = javabean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(javabean);
                if (fieldValue != null) {
                    Class<?> fieldType = field.getType();
                    if (fieldType.equals(String.class)) {
                        String trimmedValue = ((String) fieldValue).trim();
                        if (trimmedValue.length() == 0) {
                            trimmedValue = null;
                        }
                        field.set(javabean, trimmedValue);
                    } else if (Collection.class.isAssignableFrom(fieldType)) {
                        Collection<?> collection = (Collection<?>) fieldValue;
                        Iterator<?> itr = collection.iterator();
                        while (itr.hasNext()) {
                            Object element = itr.next();
                            if (element == null) {
                                itr.remove();
                            } else {
                                makeStringReasonable(element);
                            }
                        }
//                    } else if (!fieldType.isPrimitive() && !fieldType.isArray() && !fieldType.getName().startsWith("java.")) {
                    } else if (isNotSimpleValueType(fieldType)) {
                        makeStringReasonable(fieldValue);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new UncheckedException(e);
            }
        }
    }

    /**
     * 容器合理化处理——确保每个列表可以安全的forEach
     * <p>
     * 对参数javabean及其各Nest层次的Like-A Collection、Like-A Map和Array对象，进行删除为null的element处理和nullToEmpty处理
     */
    private static void makeContainerReasonable(Object javabean) {
        if (javabean == null) {
            return;
        }

        Field[] fields = javabean.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(javabean);

                if (value == null) {
                    Class<?> fieldType = field.getType();

                    if (Collection.class.isAssignableFrom(fieldType)) {
                        field.set(javabean, createEmptyCollection(fieldType));
                    } else if (Map.class.isAssignableFrom(fieldType)) {
                        field.set(javabean, createEmptyMap(fieldType));
                    } else if (fieldType.isArray()) {
                        field.set(javabean, createEmptyArray(fieldType));
                    }
                } else if (isNotSimpleValueType(value.getClass())) {
                    makeContainerReasonable(value);
                }
            } catch (IllegalAccessException e) {
                throw new UncheckedException(e);
            }
        }
    }

    private static boolean isNotSimpleValueType(Class<?> clazz) {
        return !BeanUtils.isSimpleValueType(clazz);
    }

    private static Object createEmptyCollection(Class<?> collectionType) {
        if (List.class == collectionType || Collection.class == collectionType) {
            return new ArrayList<>();
        } else if (Set.class == collectionType) {
            return new HashSet<>();
        } else if (Queue.class == collectionType) {
            return new ArrayDeque<>();
        } else {
            // other Collection subtypes can be handled here
            try {
                return collectionType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new UncheckedException(e);
            }
        }
    }

    private static Object createEmptyMap(Class<?> mapType) {
        if (Map.class == mapType) {
            return Maps.newHashMap();
        } else {
            // other Map subtypes can be handled here
            try {
                return mapType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new UncheckedException(e);
            }
        }
    }

    private static Object createEmptyArray(Class<?> arrayType) {
        return Array.newInstance(arrayType.getComponentType(), 0);
    }

}
package com.spldeolin.beginningmind.core.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.controller.annotation.DocField;
import com.spldeolin.beginningmind.core.support.dto.MarkdownDocFTL;
import com.spldeolin.beginningmind.core.support.dto.annotation.RequestMethodInfoDTO;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.Nulls;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/02
 */
@Log4j2
public class RequestMethodAnalysiser {

    private static Objenesis objenesis = new ObjenesisStd(true);

    @SneakyThrows
    public static void analysis(Method requestMethod, MarkdownDocFTL template) {
        RequestMethodInfoDTO dto = RequestMethodResolver.resolve(requestMethod);
        template.setCommonDesc(dto.getDesc());
        String mapping = dto.getMapping();
        String hUrl = template.getHttpUrl();
        if (hUrl != null) {
            hUrl += mapping;
        } else {
            hUrl = mapping;
        }
        template.setHttpUrl(hUrl);
        template.setHttpMethod(dto.getHttpMethod());
        String developer = dto.getDeveloper();
        if (StringUtils.isNotBlank(developer)) {
            template.setCommonDeveloper(developer);
        }
        /*
            开始处理Return Type
          */
        Class returnType = dto.getReturnType();
        // 用于决定 生成JSON示例时是否需要在最外层套上一层List或Page
        boolean isList = Nulls.toFalse(dto.getIsReturnList());
        boolean isPage = Nulls.toFalse(dto.getIsReturnPage());
        if (returnType == Void.class) {
            template.setReturnShow(false);
        } else {
            template.setReturnShow(true);
            // 简单类型则不显示“返回值说明”
            if (isSimpleType(returnType)) {
                template.setIsRetrunSimpleType(true);
                if (returnType == String.class) {
                    template.setReturnJson("\"demoString\"");
                } else if (returnType == Integer.class || returnType == Long.class) {
                    template.setReturnJson("2333");
                } else if (returnType == Boolean.class) {
                    template.setReturnJson("true");
                } else {
                    log.error("出现了未考虑到的类型");
                    System.exit(0);
                }
            } else {
                template.setIsRetrunSimpleType(false);
                List<Field> allFields = new ArrayList<>();
                loadClassSimpleFields(returnType, allFields);
                List<MarkdownDocFTL.RField> rFields = new ArrayList<>();
                for (Field field : allFields) {
                    MarkdownDocFTL.RField rField = new MarkdownDocFTL.RField();
                    rField.setReturnName(field.getName());
                    Class fieldClass = field.getType();
                    if (fieldClass == String.class) {
                        rField.setReturnType("String");
                    } else if (fieldClass == Integer.class || fieldClass == Long.class) {
                        rField.setReturnType("Number");
                    } else if (fieldClass == Double.class || fieldClass == BigDecimal.class) {
                        rField.setReturnType("Number（小数）");
                    } else if (fieldClass == Boolean.class) {
                        rField.setReturnType("Boolean");
                    } else if (fieldClass == LocalDate.class || fieldClass == LocalTime.class ||
                            fieldClass == LocalDateTime.class) {
                        rField.setReturnType("String");
                    } else {
                        log.error("出现了未考虑到的类型");
                        System.exit(0);
                    }
                    DocField docField = field.getAnnotation(DocField.class);
                    if (docField == null) {
                        rField.setReturnDesc("　");
                    } else {
                        String desc = docField.desc();
                        if (StringUtils.isBlank(desc)) {
                            desc = "　";
                        }
                        rField.setReturnDesc(desc);
                    }
                    rFields.add(rField);
                }
                template.setReturnFields(rFields);
                Object returnValue = objenesis.newInstance(returnType);
                setFieldValue(returnValue);
                if (isList) {
                    returnValue = Lists.newArrayList(returnValue);
                }
                if (isPage) {
                    Page page = Page.empty();
                    page.setPageNo(2);
                    page.setHasPreviousPage(true);
                    page.setEntitiesInPage(Lists.newArrayList(returnValue));
                    page.setHasNextPage(true);
                    page.setPagesCount(1024);
                    page.setTotal(65535L);
                    returnValue = page;
                }
                String json = Jsons.toBeauty(returnValue);
                json = json.replace("null", "val");
                json = json.replace("  ", "    ");
                json = json.replace("\" :", "\":");
                template.setReturnJson(json);
            }
        }
    }

    private static void loadClassSimpleFields(Class clazz, List<Field> fields) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Class fieldClass = field.getType();
            if (isCollectionType(field)) {
                fieldClass = getGenericType(field);
            }
            if (isSimpleType(fieldClass)) {
                fields.add(field);
            } else {
                loadClassSimpleFields(fieldClass, fields);
            }
        }
    }

    private static boolean isSimpleType(Class clazz) {
        /* TODO 还应该考虑其他简单类型 */
        return clazz == String.class ||
                clazz == Integer.class || clazz == Long.class ||
                clazz == Double.class || clazz == BigDecimal.class ||
                clazz == Boolean.class ||
                clazz == LocalDate.class || clazz == LocalTime.class || clazz == LocalDateTime.class;
    }

    private static boolean isCollectionType(Field field) {
        Class clazz = field.getType();
        return clazz == List.class || clazz == Page.class;
    }

    private static Class getGenericType(Field field) {
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
            return (Class) parameterizedType.getActualTypeArguments()[0];
        }
        return field.getType();
    }

    /**
     * 设置值。
     * 简单类型忽略，
     * 复杂类型设置空对象，并递归
     */
    @SneakyThrows
    private static void setFieldValue(Object object) {
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Class fieldClass = field.getType();
            boolean isList = fieldClass == List.class;
            if (isList) {
                fieldClass = getGenericType(field);
            }
            if (!isSimpleType(fieldClass)) {
                Object fieldValue = objenesis.newInstance(fieldClass);
                setFieldValue(fieldValue);
                if (isList) {
                    fieldValue = Lists.newArrayList(fieldValue);
                }
                field.setAccessible(true);
                field.set(object, fieldValue);
            }
        }
    }

}

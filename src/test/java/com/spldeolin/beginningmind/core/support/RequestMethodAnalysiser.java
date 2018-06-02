package com.spldeolin.beginningmind.core.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
        template.setCommonDesc(Nulls.toEmpty(dto.getDesc()));
        String mapping = dto.getMapping();
        String hUrl = template.getHttpUrl();
        if (hUrl != null) {
            hUrl += mapping;
        } else {
            hUrl = mapping;
        }
        template.setHttpUrl(hUrl);
        template.setHttpMethod(dto.getHttpMethod());
        Class returnType = dto.getReturnType();
        // 用于决定 生成JSON示例时是否需要在最外层套上一层List或Page
        boolean isList = Nulls.toFalse(dto.getIsReturnList());
        boolean isPage = Nulls.toFalse(dto.getIsReturnPage());
        if (returnType == Void.class) {
            template.setReturnShow(false);
        } else {
            template.setReturnShow(true);
            // 简单类型则不显示“返回值说明”
            if (isSimpleClass(returnType)) {
                template.setReturnFieldsShow(false);
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
                template.setReturnFieldsShow(true);
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
                        rField.setReturnDesc("");
                    } else {
                        rField.setReturnDesc(Nulls.toEmpty(docField.desc()));
                    }
                    rFields.add(rField);
                }
                template.setReturnFields(rFields);
                Object returnValue = objenesis.newInstance(returnType);
                if (isList) {
                    returnValue = Lists.newArrayList(returnValue, returnValue);
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
                template.setReturnJson(Jsons.toBeauty(returnValue).replace("null", "val"));
            }
        }
    }

    private static void loadClassSimpleFields(Class clazz, List<Field> fields) {
        for (Field field : clazz.getDeclaredFields()) {
            Class fieldClass = field.getType();
            if (isSimpleClass(fieldClass)) {
                fields.add(field);
            } else {
                loadClassSimpleFields(fieldClass, fields);
            }
        }
    }

    private static boolean isSimpleClass(Class clazz) {
        /* TODO 还应该考虑其他简单类型 */
        return clazz == String.class ||
                clazz == Integer.class || clazz == Long.class ||
                clazz == Double.class || clazz == BigDecimal.class ||
                clazz == Boolean.class ||
                clazz == LocalDate.class || clazz == LocalTime.class || clazz == LocalDateTime.class;
    }

}

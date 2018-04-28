package com.spldeolin.beginningmind.restful.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.restful.annotation.PageNo;
import com.spldeolin.beginningmind.restful.annotation.PageSize;
import com.spldeolin.beginningmind.restful.dto.ControllerInfo;
import com.spldeolin.beginningmind.restful.dto.Invalid;
import com.spldeolin.beginningmind.restful.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 切面：额外注解处理
 *
 * @author Deolin
 */
@Component
@Aspect
@Log4j2
public class ExtraAnnotationHandleAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void performance() {}

    @Autowired
    private BeginningMindProperties properties;

    @Around("performance()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ControllerInfo controllerInfo = RequestContextUtil.getControllerInfo();
        if (controllerInfo == null) {
            // 什么都不做
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }
        List<Invalid> invalids = new ArrayList<>();

        Annotation[][] annotationsEachParams = controllerInfo.getMethod().getParameterAnnotations();
        String[] parameterNames = controllerInfo.getParameterNames();
        Object[] parameterValues = controllerInfo.getParameterValues();
        for (int i = 0; i < annotationsEachParams.length; i++) {
            Annotation[] annotations = annotationsEachParams[i];
            String parameterName = parameterNames[i];
            // 已绑定的参数值（副本）
            Object parameterValue = parameterValues[i];
            for (Annotation annotation : annotations) {
                // 处理PageNo注解
                if (annotation instanceof PageNo) {
                    String pageNoParamName = properties.getPageNoParamName();
                    if (StringUtils.isBlank(pageNoParamName)) {
                        pageNoParamName = "page_no";
                    }
                    parameterValue = handlePageNo(
                            RequestContextUtil.request().getParameter(pageNoParamName));
                }
                // 处理PageSize注解
                if (annotation instanceof PageSize) {
                    String pageSizeParamName = properties.getPageSizeParamName();
                    if (StringUtils.isBlank(pageSizeParamName)) {
                        pageSizeParamName = "page_size";
                    }
                    parameterValue = handlePageSize(annotation,
                            RequestContextUtil.request().getParameter(pageSizeParamName));
                }
                // 处理RequireId注解
                //if (annotation instanceof RequireId) {
                //    handleRequireId(parameterValue, invalids);
                //}
                // 进行RequestParam注解的空检验
                if (annotation instanceof RequestParam) {
                    handleRequestParam(annotation, parameterName, parameterValue, invalids);
                }
                parameterValues[i] = parameterValue;
            }
        }
        // 存在异常，没必要执行切点了
        if (invalids.size() > 0) {
            throw new ExtraInvalidException().setInvalids(invalids);
        } else {
            return proceedingJoinPoint.proceed(parameterValues);
        }
    }

    /**
     * 处理PageNo注解
     *
     * @param urlParamValue 未绑定的URL参数
     * @return 处理后的值
     */
    private Object handlePageNo(String urlParamValue) {
        // 不是数字
        if (!NumberUtils.isCreatable(urlParamValue)) {
            return 1;
        }
        int value = Integer.parseInt(urlParamValue);
        // 超出范围
        if (value < 1) {
            return 1;
        }
        return value;
    }

    /**
     * 处理PageSize注解
     *
     * @param annotation PageSize注解对象
     * @param urlParamValue PageSize修饰的参数的值
     * @return 处理后的值
     */
    private Object handlePageSize(Annotation annotation, String urlParamValue) {
        // 不是数字
            if (!NumberUtils.isCreatable(urlParamValue)) {
            return ((PageSize) annotation).defaultSize();
        }
        int limit = ((PageSize) annotation).limit();
        int value = Integer.parseInt(urlParamValue);
        // 超出范围
        if (value < 1) {
            return 1;
        }
        // 超出范围
        if (value > limit) {
            return limit;
        }
        return value;
    }

    /**
     * 处理RequireId注解
     *
     * @param parameterValue RequireId修饰的参数的值
     * @param invalids 校验未通过的信息
     */
    private void handleRequireId(Object parameterValue, List<Invalid> invalids) {
        Class<?> clazz = parameterValue.getClass();
        Field id = ReflectionUtils.findField(clazz, "id");
        if (id != null) {
            id.setAccessible(true);
            if (ReflectionUtils.getField(id, parameterValue) == null) {
                invalids.add(Invalid.builder().name("id").value(null).cause("不能为空").build());
            }
        }
    }

    /**
     * 处理RequestParam注解
     *
     * @param annotation RequestParam注解对象
     * @param parameterName RequestParam修饰的参数的名称
     * @param parameterValue RequestParam修饰的参数的值
     * @param invalids 校验未通过的信息
     */
    private void handleRequestParam(Annotation annotation, String parameterName, Object parameterValue,
            List<Invalid> invalids) {
        if (((RequestParam) annotation).required()) {
            // 空对象
            if (parameterValue == null ||
                    (parameterValue instanceof String && ((String) parameterValue).length() == 0)) {
                invalids.add(Invalid.builder().name(parameterName).value(null).cause("不能为空").build());
            }
        }
    }

}

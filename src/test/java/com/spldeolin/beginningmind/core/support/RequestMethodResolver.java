package com.spldeolin.beginningmind.core.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.common.base.Joiner;
import com.spldeolin.beginningmind.core.controller.annotation.DocMethod;
import com.spldeolin.beginningmind.core.support.dto.annotation.RequestMethodInfoDTO;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/02
 */
@Log4j2
public class RequestMethodResolver {

    public static RequestMethodInfoDTO resolve(Method method) {
        RequestMethodInfoDTO dto = RequestMethodInfoDTO.builder().desc(method.getName()).returnType(
                Void.class).developer("").date(LocalDate.now().toString()).build();
        /*
            @RequestMapping, @GetMapping, @PostMapping, ...
        */
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (requestMapping != null) {
            RequestMethod[] httpMethod = requestMapping.method();
            if (httpMethod.length == 0) {
                dto.setHttpMethod("任意");
            } else {
                dto.setHttpMethod(Joiner.on(", ").join(httpMethod));
            }
            dto.setMapping(requestMapping.value()[0]);
        } else if (getMapping != null) {
            dto.setHttpMethod("GET").setMapping(getMapping.value()[0]);
        } else if (postMapping != null) {
            dto.setHttpMethod("POST").setMapping(postMapping.value()[0]);
        } else if (putMapping != null) {
            dto.setHttpMethod("PUT").setMapping(putMapping.value()[0]);
        } else if (deleteMapping != null) {
            dto.setHttpMethod("DELETE").setMapping(deleteMapping.value()[0]);
        } else {
            log.error("impossible");
            System.exit(0);
        }
        /*
            @DocMethod
         */
        DocMethod docMethod = method.getAnnotation(DocMethod.class);
        if (docMethod == null) {
            return dto;
        }
        String desc = docMethod.desc();
        if (StringUtils.isNotBlank(desc)) {
            dto.setDesc(desc);
        }
        Class returnType = docMethod.returnType();
        if (returnType != Void.class) {
            dto.setReturnType(returnType);
        }
        dto.setIsReturnList(docMethod.isReturnList());
        dto.setIsReturnPage(docMethod.isReturnPage());
        String developer = docMethod.developer();
        if (StringUtils.isNotBlank(developer)) {
            dto.setDeveloper(developer);
        }
        String date = docMethod.date();
        if (StringUtils.isNotBlank(date)) {
            dto.setDate(date);
        }
        /*
            signature
         */
        List<Class> parameterTypes = new ArrayList<>();
        List<String> parameterNames = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            parameterTypes.add(parameter.getType());
            parameterNames.add(parameter.getName());
        }
        dto.setParameterTypes(parameterTypes);
        dto.setParameterNames(parameterNames);
        return dto;
    }

}

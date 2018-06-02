package com.spldeolin.beginningmind.core.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spldeolin.beginningmind.core.support.dto.annotation.ClassRequestMappingDTO;

/**
 * @author Deolin 2018/06/02
 */
public class ClassRequestMappingResolver {

    public static ClassRequestMappingDTO resolve(Class controller) {
        ClassRequestMappingDTO dto = ClassRequestMappingDTO.builder().mapping("").build();
        RequestMapping requestMapping = (RequestMapping) controller.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return dto;
        }
        String mapping = requestMapping.value()[0];
        if (StringUtils.isBlank(mapping)) {
            return dto;
        }
        if (mapping.equals("/")) {
            return dto;
        }
        dto.setMapping(mapping);
        return dto;
    }

}

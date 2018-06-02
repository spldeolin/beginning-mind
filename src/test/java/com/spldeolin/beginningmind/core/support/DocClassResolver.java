package com.spldeolin.beginningmind.core.support;

import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import com.spldeolin.beginningmind.core.controller.annotation.DocClass;
import com.spldeolin.beginningmind.core.support.dto.annotation.DocClassDTO;

/**
 * @author Deolin 2018/06/02
 */
public class DocClassResolver {

    public static DocClassDTO resolve(Class controller) {
        DocClassDTO dto = DocClassDTO.builder().name("").developer("未声明").date(LocalDate.now().toString()).build();
        DocClass docClass = (DocClass) controller.getAnnotation(DocClass.class);
        if (docClass == null) {
            return dto;
        }
        String name = docClass.name();
        if (StringUtils.isNotBlank(name)) {
            dto.setName(name);
        }
        String developer = docClass.developer();
        if (StringUtils.isNotBlank(developer)) {
            dto.setDeveloper(developer);
        }
        String date = docClass.date();
        if (StringUtils.isNotBlank(date)) {
            dto.setDate(date);
        }
        return dto;
    }

}

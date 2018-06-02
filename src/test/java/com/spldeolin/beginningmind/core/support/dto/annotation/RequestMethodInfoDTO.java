/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.support.dto.annotation;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/06/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RequestMethodInfoDTO implements Serializable {

    /*
        @DocMethod
     */

    private String desc;

    private Class returnType;

    private Boolean isReturnList;

    private Boolean isReturnPage;

    private String developer;

    private String date;

    /*
        @RequestMapping, @GetMapping, @PostMapping, ...
     */

    private String httpMethod;

    private String mapping;

    /*
        signature
     */

    private List<Class> parameterTypes;

    private List<String> parameterNames;

    private static final long serialVersionUID = 1L;

}
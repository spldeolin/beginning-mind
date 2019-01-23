package com.spldeolin.beginningmind.launch.test.generator.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/15
 */

@Data
@Builder
public class ServiceFtl implements Serializable {

    private String basePackageReference;

    private String moduleName;

    private String entityName;

    private String entityCnsName;

    private String author;

    private static final long serialVersionUID = 1L;

}
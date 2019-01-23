package com.spldeolin.beginningmind.launch.test.generator.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/14
 */
@Data
@Builder
public class EntityFtl implements Serializable {

    private String basePackageReference;

    private String moduleName;

    private String entityCnsName;

    private String author;

    private String tableName;

    private String entityName;

    private List<Property> properties;

    private static final long serialVersionUID = 1L;

    @Data
    @Builder
    public static class Property implements Serializable {

        private String fieldCnsName;

        private Boolean isDeleteFlag;

        private Boolean isVersion;

        private String columnName;

        private String fieldType;

        private String fieldName;

        private static final long serialVersionUID = 1L;

    }

}
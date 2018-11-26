package com.spldeolin.beginningmind.core.generator.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ModelFtl implements Serializable {

    private String packageReference;

    private String modelCnsName;

    private String author;

    private String tableName;

    private String modelName;

    private List<Property> properties;

    private static final long serialVersionUID = 1L;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Accessors(chain = true)
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
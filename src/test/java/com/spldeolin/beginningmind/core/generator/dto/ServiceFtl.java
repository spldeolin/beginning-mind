package com.spldeolin.beginningmind.core.generator.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/15
 */

@Data
@Builder
public class ServiceFtl implements Serializable {

    private String packageReference;

    private String entityName;

    private String entityCnsName;

    private String author;

    private static final long serialVersionUID = 1L;

}
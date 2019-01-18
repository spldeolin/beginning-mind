package com.spldeolin.beginningmind.core.generator.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/14
 */

@Data
@Builder
public class MapperXmlFtl implements Serializable {

    private String packageReference;

    private String entityName;

    private static final long serialVersionUID = 1L;

}
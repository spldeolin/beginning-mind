package com.spldeolin.beginningmind.api.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2025-02-09
 */
@Data
@Accessors(chain = true)
public class SampleRespDto {

    private String greeting;

}
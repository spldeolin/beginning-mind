package com.spldeolin.beginningmind.api.dto.req;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2025-02-09
 */
@Data
@Accessors(chain = true)
public class SampleReqDto {

    @NotEmpty
    private String name;

}
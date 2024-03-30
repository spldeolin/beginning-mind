package com.spldeolin.beginningmind.api.javabean.req;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2023-04-09
 */
@Data
@Accessors(chain = true)
public class DemoReqDto {

    @NotNull
    private Long id;

}
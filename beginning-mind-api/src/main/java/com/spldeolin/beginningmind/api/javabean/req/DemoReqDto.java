package com.spldeolin.beginningmind.api.javabean.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Deolin 2023-04-09
 */
@Data
@Accessors(chain = true)
public class DemoReqDto {

    @NotNull
    private Long id;

}
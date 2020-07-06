package com.spldeolin.beginningmind.demo.valid;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 一个嵌套了另一个Dto的Dto
 *
 * @author Deolin 2020-07-06
 */
@Data
public class NestDto {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 6)
    private String name;

    @NotEmpty
    private Collection<@NotNull @Valid SimpleDto> dtos;

}
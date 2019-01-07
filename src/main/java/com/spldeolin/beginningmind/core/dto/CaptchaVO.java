package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/05/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CaptchaVO implements Serializable {

    private String image;

    private String token;

    private static final long serialVersionUID = 1L;

}
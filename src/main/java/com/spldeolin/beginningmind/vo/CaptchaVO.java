package com.spldeolin.beginningmind.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
@AllArgsConstructor
public class CaptchaVO implements Serializable {

    private String image;

    private String token;

    private static final long serialVersionUID = 1L;

}
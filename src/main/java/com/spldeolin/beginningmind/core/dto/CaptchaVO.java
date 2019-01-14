package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
public class CaptchaVO implements Serializable {

    private String image;

    private String token;

    private static final long serialVersionUID = 1L;

    public CaptchaVO(String image, String token) {
        this.image = image;
        this.token = token;
    }
}
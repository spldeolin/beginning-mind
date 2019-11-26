package com.spldeolin.beginningmind.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
@AllArgsConstructor
public class CaptchaVO {

    private String image;

    private String token;

}
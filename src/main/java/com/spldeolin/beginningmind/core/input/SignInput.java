package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Deolin
 */
@Data
public class SignInput implements Serializable {

    /**
     * 用户输入的“用户名/手机/邮箱”
     */
    @NotBlank
    private String principal;

    /**
     * 用户输入的“密码”
     */
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @NotBlank
    private String captcha;

    /**
     * 用于获取验证码缓存的token
     */
    @NotBlank
    private String captchaToken;

    private static final long serialVersionUID = 1L;

}
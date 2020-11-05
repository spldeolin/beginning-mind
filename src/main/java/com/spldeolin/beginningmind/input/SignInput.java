package com.spldeolin.beginningmind.input;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Deolin
 */
@Data
public class SignInput {

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

}
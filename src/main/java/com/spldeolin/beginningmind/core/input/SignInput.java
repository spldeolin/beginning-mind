package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
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

    private static final long serialVersionUID = 1L;

}
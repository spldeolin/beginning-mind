package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.NotBlank;
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

    private static final long serialVersionUID = 1L;

    /**
     * 转化为UsernamePasswordToken对象，作为subject.login()的参数
     */
    public UsernamePasswordToken toToken() {
        UsernamePasswordToken token = new UsernamePasswordToken(principal, password);
        token.setRememberMe(false);
        return token;
    }

}
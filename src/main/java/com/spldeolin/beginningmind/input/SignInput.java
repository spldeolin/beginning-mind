package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SignInput implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private static final long serialVersionUID = 1L;

    /**
     * 转化为UsernamePasswordToken对象，作为subject.login()的参数
     */
    public UsernamePasswordToken toToken() {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);
        return token;
    }

}
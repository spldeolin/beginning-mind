package com.spldeolin.beginningmind.component.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        // info来自数据库，token来自用户输入
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String realPassword = new String(token.getPassword());
        String inputPassword = (String) info.getCredentials();
        if (!super.equals(realPassword, inputPassword)) {
            throw new IncorrectCredentialsException("用户不存在或密码错误");
        }
        return true;
    }

}

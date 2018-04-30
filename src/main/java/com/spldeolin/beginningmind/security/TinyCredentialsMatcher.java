package com.spldeolin.beginningmind.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import com.spldeolin.beginningmind.security.dto.FinalCredential;

public class TinyCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        // token来自用户输入，info来自数据库
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
        // 解析密码
        String inputPassword = new String(token.getPassword());
        FinalCredential finalCredential = (FinalCredential) info.getCredentials();
        String finalPassword = finalCredential.getFinalPassword();
        // 匹配
        if (!inputPassword.equals(finalPassword)) {
            throw new IncorrectCredentialsException("用户不存在或密码错误");
        }
        return true;
    }

}

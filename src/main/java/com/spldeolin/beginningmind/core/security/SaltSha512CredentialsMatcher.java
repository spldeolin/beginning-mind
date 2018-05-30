package com.spldeolin.beginningmind.core.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import com.spldeolin.beginningmind.core.security.dto.SaltCredential;

public class SaltSha512CredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        // token来自用户输入，info来自数据库
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
        // SHA512 -> 加盐 -> SHA512
        String rawPassword = new String(token.getPassword());
        String passwordEX = DigestUtils.sha512Hex(rawPassword);
        SaltCredential dto = (SaltCredential) info.getCredentials();
        String passwordEX2 = DigestUtils.sha512Hex(passwordEX + dto.getSalt());
        String password = dto.getPassword();
        // 匹配
        if (!passwordEX2.equals(password)) {
            throw new IncorrectCredentialsException("用户不存在或密码错误");
        }
        return true;
    }

}

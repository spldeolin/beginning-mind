package com.spldeolin.beginningmind.security;

import java.time.LocalDateTime;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import com.spldeolin.beginningmind.model.Account;
import com.spldeolin.beginningmind.security.dto.CurrentSigner;
import com.spldeolin.beginningmind.security.dto.FinalCredential;
import com.spldeolin.beginningmind.service.AccountService;
import com.spldeolin.beginningmind.util.RequestContextUtil;

public class ServiceRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    /**
     * 认证后授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Account account = accountService.searchOne("username", username).orElseThrow(
                () -> new UnknownAccountException("用户不存在或密码错误"));
        if (!account.getEnableSign()) {
            throw new DisabledAccountException("用户已被禁用");
        }
        CurrentSigner currentSigner = CurrentSigner.builder().sessionId(RequestContextUtil.session().getId()).account(
                account).signedAt(LocalDateTime.now()).build();
        FinalCredential finalCredential = FinalCredential.builder().finalPassword(account.getPassword()).build();
        return new SimpleAuthenticationInfo(currentSigner, finalCredential, getName());
    }

}

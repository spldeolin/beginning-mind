package com.spldeolin.beginningmind.security;

import java.time.LocalDateTime;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Sets;
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
    // TODO 这个方法每次请求需要权限的接口时都会调用，考虑做个缓存。
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // TODO 这里通过accountService获取用户的权限
        Set<String> permissionNames = Sets.newHashSet("test/set");
        info.setStringPermissions(permissionNames);
        return info;
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

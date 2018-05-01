package com.spldeolin.beginningmind.security;

import java.time.LocalDateTime;
import java.util.List;
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
import com.spldeolin.beginningmind.model.SecurityAccount;
import com.spldeolin.beginningmind.security.dto.CurrentSigner;
import com.spldeolin.beginningmind.security.dto.FinalCredential;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.RequestContextUtil;

public class ServiceRealm extends AuthorizingRealm {

    @Autowired
    private SecurityAccountService securityAccountService;

    /**
     * 认证后授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long accountId = ((CurrentSigner) principals.getPrimaryPrincipal()).getSecurityAccount().getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> permissionMappings = securityAccountService.listAccountPermissionMappings(accountId);
        Set<String> permissionNames = Sets.newHashSet(permissionMappings);
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
        SecurityAccount securityAccount = securityAccountService.searchOne("username", username).orElseThrow(
                () -> new UnknownAccountException("用户不存在或密码错误"));
        if (!securityAccount.getEnableSign()) {
            throw new DisabledAccountException("用户已被禁用");
        }
        CurrentSigner currentSigner = CurrentSigner.builder().sessionId(
                RequestContextUtil.session().getId()).securityAccount(securityAccount).signedAt(
                LocalDateTime.now()).build();
        FinalCredential finalCredential = FinalCredential.builder().finalPassword(
                securityAccount.getPassword()).build();
        return new SimpleAuthenticationInfo(currentSigner, finalCredential, getName());
    }

}

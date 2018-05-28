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
import com.spldeolin.beginningmind.model.SecurityUser;
import com.spldeolin.beginningmind.security.dto.CurrentSigner;
import com.spldeolin.beginningmind.security.dto.SaltCredential;
import com.spldeolin.beginningmind.service.SecurityUserService;
import com.spldeolin.beginningmind.util.RequestContextUtils;

public class ServiceRealm extends AuthorizingRealm {

    @Autowired
    private SecurityUserService securityUserService;

    /**
     * 认证后授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = ((CurrentSigner) principals.getPrimaryPrincipal()).getSecurityUser().getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> permissionMappings = securityUserService.listUserPermissions(userId);
        Set<String> permissionNames = Sets.newHashSet(permissionMappings);
        info.setStringPermissions(permissionNames);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 通过principal查找用户
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String principal = token.getUsername();
        SecurityUser securityUser = securityUserService.searchOneByPrincipal(principal).orElseThrow(
                () -> new UnknownAccountException("用户不存在或密码错误"));
        if (!securityUser.getEnableSign()) {
            throw new DisabledAccountException("用户已被禁用");
        }
        // 组装当前登录用户对象
        CurrentSigner currentSigner = CurrentSigner.builder().sessionId(
                RequestContextUtils.session().getId()).securityUser(securityUser).signedAt(
                LocalDateTime.now()).build();
        SaltCredential saltCredential = SaltCredential.builder().password(securityUser.getPassword()).salt(
                securityUser.getSalt()).build();
        return new SimpleAuthenticationInfo(currentSigner, saltCredential, getName());
    }

}

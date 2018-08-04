package com.spldeolin.beginningmind.core.security;

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
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.core.security.dto.SaltCredentialDTO;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Sessions;

/**
 * 对接Service的Realm
 *
 * @author Deolin
 */
public class ServiceRealm extends AuthorizingRealm {

    @Autowired
    private UserService securityUserService;

    /**
     * 认证后授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = ((CurrentSignerDTO) principals.getPrimaryPrincipal()).getUser().getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permissionNames = securityUserService.listUserPermissions(userId);
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
        User user = securityUserService.searchOneByPrincipal(principal).orElseThrow(
                () -> new UnknownAccountException("用户不存在或密码错误"));
        if (!user.getEnableSign()) {
            throw new DisabledAccountException("用户已被禁用");
        }
        // 组装当前登录用户对象
        CurrentSignerDTO currentSigner = CurrentSignerDTO.builder().sessionId(
                Sessions.session().getId()).user(user).signedAt(
                LocalDateTime.now()).build();
        SaltCredentialDTO saltCredential = SaltCredentialDTO.builder().password(user.getPassword()).salt(
                user.getSalt()).build();
        return new SimpleAuthenticationInfo(currentSigner, saltCredential, getName());
    }

}

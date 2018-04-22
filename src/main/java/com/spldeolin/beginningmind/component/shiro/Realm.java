package com.spldeolin.beginningmind.component.shiro;

import com.spldeolin.beginningmind.dto.CurrentSignUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Realm extends AuthorizingRealm {

    private static final String usernameFromDB = "deolin";

    private static final String passwordFromDB = "000000";

    @Autowired
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        if (!usernameFromDB.equals(username)) {
            throw new UnknownAccountException("用户不存在或密码错误");
        }
        CurrentSignUser currentSignUser = CurrentSignUser.builder().username(username).build();
        return new SimpleAuthenticationInfo(currentSignUser, passwordFromDB, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO
        return null;
    }

}

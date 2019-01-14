package com.spldeolin.beginningmind.core.security;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;
import com.spldeolin.beginningmind.core.security.exception.UnauthorizeException;
import com.spldeolin.beginningmind.core.security.util.SignContext;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/07
 */
@Component
@Log4j2
public class PermissionChecker {

    @Autowired
    @Qualifier("anonUrlsPrefix")
    private Set<String> anonUrlsPrefix;

    public void ensureAuth(HttpServletRequest request) throws UnauthorizeException {
        String url = request.getRequestURI();
        if (startsWithAnonUrlsPrefix(request.getRequestURI())) {
            return;
        }

        boolean isPermit = false;
        for (PermissionEntity permission : SignContext.current().getPermissions()) {
            if (request.getMethod().equalsIgnoreCase(permission.getMappingMethod())
                    && url.startsWith(permission.getMappingPath())) {
                isPermit = true;
            }
        }
        if (!isPermit) {
            throw new UnauthorizeException("权限不足");
        }
    }

    private boolean startsWithAnonUrlsPrefix(String uri) {
        for (String prefix : anonUrlsPrefix) {
            if (uri.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}

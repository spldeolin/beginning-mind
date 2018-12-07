package com.spldeolin.beginningmind.core.security;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.security.util.Signer;

/**
 * @author Deolin 2018/12/02
 */
@Component
public class SignedChecker {

    @Autowired
    @Qualifier("anonUrlsPrefix")
    private Set<String> anonUrlsPrefix;

    public void ensureSigned(HttpServletRequest request) throws UnsignedException {
        if (startsWithAnonUrlsPrefix(request.getRequestURI())) {
            return;
        }

        if (!Signer.isSigning()) {
            throw new UnsignedException("未登录或登录超时");
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

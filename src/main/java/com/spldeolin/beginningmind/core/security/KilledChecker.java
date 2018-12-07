package com.spldeolin.beginningmind.core.security;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.security.exception.KilledException;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.impl.SignServiceImpl;
import com.spldeolin.beginningmind.core.util.Sessions;

/**
 * @author Deolin 2018/12/02
 */
@Component
public class KilledChecker {

    @Autowired
    private SignService signService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("anonUrlsPrefix")
    private Set<String> anonUrlsPrefix;

    public void ensureNotKilled(HttpServletRequest request) throws KilledException {
        if (startsWithAnonUrlsPrefix(request.getRequestURI())) {
            return;
        }

        boolean isSigning = Signer.isSigning();
        if (isSigning) {

            boolean isKilled = redisTemplate.opsForHash()
                    .get(SignServiceImpl.SIGN_STATUS_BY_USER_ID + Signer.userId(), Sessions.session().getId()) == null;
            if (isKilled) {
                signService.signOut();
                throw new KilledException("已被请离，请重新登录");
            }
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

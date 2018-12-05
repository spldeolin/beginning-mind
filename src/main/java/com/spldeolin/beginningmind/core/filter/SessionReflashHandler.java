package com.spldeolin.beginningmind.core.filter;

import java.time.LocalDateTime;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.util.Sessions;

/**
 * @author Deolin 2018/12/05
 */
@Component
public class SessionReflashHandler {

    @Async
    public void asyncReflashExpire(HttpSession session) {
        reflashGlobalSession(session);
        reflashSessionAttributes(session);
    }

    private void reflashGlobalSession(HttpSession session) {
        session.setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);
    }

    private void reflashSessionAttributes(HttpSession session) {
        for (String key : Collections.list(session.getAttributeNames())) {
            Object value = session.getAttribute(key);
            if (value instanceof Sessions.ValueWrapper) {
                Sessions.ValueWrapper wrapper = (Sessions.ValueWrapper) value;
                wrapper.setSetAt(LocalDateTime.now());
                Sessions.set(key, wrapper.getValue(), wrapper.getExpiredSeconds());
            }
        }
    }

}

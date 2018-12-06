package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.util.Sessions;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：ReadContentFilter的内侧
 *
 * 前置：无
 *
 * 后置：刷新全局会话和会话每个k-v的失效时间
 *
 * @author Deolin 2018/12/06
 */
@Order(SessionReflashFilter.ORDER)
@Component
@Log4j2
public class SessionReflashFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + ReadContentFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(request, response);

        // 刷新全局会话的失效时间
        reflashGlobalSession(Sessions.session());
        // 刷新会话每个k-v的失效时间
        reflashSessionAttributes(Sessions.session());
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

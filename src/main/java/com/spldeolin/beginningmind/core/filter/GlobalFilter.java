package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.github.pagehelper.page.PageMethod;
import com.google.common.base.Stopwatch;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.util.Sessions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

/**
 * 全局过滤器
 *
 * @author Deolin 2018/06/17
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@Log4j2
public class GlobalFilter extends OncePerRequestFilter {

    private ThreadLocal<RequestTrackDTO> requestTrackContext = new ThreadLocal<>();

    @Autowired
    private RequestTrackService requestTrackService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 构造请求轨迹，存入ThreadLocal
        RequestTrackDTO requestTrackDTO = requestTrackService.buildRequestTrack();
        requestTrackContext.set(requestTrackDTO);

        // 设置Log MDC
        setLogMDC();

        // todo security（是否被踢出、是否登录、鉴权）

        // 填入登录者信息
        if (Signer.isSigning()) {
            requestTrackDTO.setUserId(Signer.userId());
        }

        requestTrackDTO.setStopwatch(Stopwatch.createStarted());
        filterChain.doFilter(request, response);

        // 完成并保存
        requestTrackService.completeAndSave(requestTrackDTO, request);

        // 刷新会话与每个会话k-v的失效时间
        reflashSessionExpire();
        reflashSessionAttributeExpire();

        // 清除ThreadLocal（分页、Log MDC、请求轨迹）
        PageMethod.clearPage();
        removeLogMDC();
        requestTrackContext.remove();
    }

    private void setLogMDC() {
        ThreadContext.put(CoupledConstant.LOG_MDC_INSIGNIA, "[" + RequestTrackContext.getInsignia() + "]");
    }

    private void reflashSessionExpire() {
        Sessions.session().setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);
    }

    private void reflashSessionAttributeExpire() {
        HttpSession httpSession = Sessions.session();
        for (String key : Collections.list(httpSession.getAttributeNames())) {
            Object value = httpSession.getAttribute(key);
            if (value instanceof Sessions.ValueWrapper) {
                Sessions.ValueWrapper wrapper = (Sessions.ValueWrapper) value;
                wrapper.setSetAt(LocalDateTime.now());
                Sessions.set(key, wrapper, wrapper.getExpiredSeconds());
            }
        }
    }

    private void removeLogMDC() {
        ThreadContext.remove(CoupledConstant.LOG_MDC_INSIGNIA);
    }

}

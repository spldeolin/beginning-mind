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
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.CheckKilledHandler;
import com.spldeolin.beginningmind.core.security.CheckSignedHandler;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.Sessions;
import lombok.extern.log4j.Log4j2;

/**
 * 全局过滤器
 *
 * @author Deolin 2018/06/17
 */
@Component
@Log4j2
public class GlobalFilter extends OncePerRequestFilter {

    @Autowired
    private RequestTrackService requestTrackService;

    @Autowired
    private CheckKilledHandler checkKilledHandler;

    @Autowired
    private CheckSignedHandler checkSignedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 构造请求轨迹，存入ThreadLocal
        RequestTrackDTO requestTrackDTO = requestTrackService.buildRequestTrack();
        RequestTrackContext.setRequestTrack(requestTrackDTO);

        // 设置Log MDC
        setLogMDC();

        // todo security（鉴权）
        try {
            checkKilledHandler.ensureNotKilled(request);
            checkSignedHandler.ensureSigned(request);
        } catch (UnsignedException e) {
            handleException(response, e);
            return;
        }

        // 填入登录者信息
        if (Signer.isSigning()) {
            requestTrackDTO.setUserId(Signer.userId());
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(request, response);
        log.info(new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding()));
        wrappedResponse.copyBodyToResponse();
        log.info(new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding()));

        // 完成并保存
        requestTrackService.completeAndSave(requestTrackDTO, request);

        // 刷新会话与每个会话k-v的失效时间
        reflashSessionExpire();
        reflashSessionAttributeExpire();

        // 清除ThreadLocal（Log MDC、请求轨迹）
        clearAllThreadLocal();
    }

    private void setLogMDC() {
        ThreadContext.put(CoupledConstant.LOG_MDC_INSIGNIA, "[" + RequestTrackContext.getInsignia() + "]");
    }

    private void handleException(HttpServletResponse response, UnsignedException e) throws IOException {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write(Jsons.toJson(RequestResult.failure(ResultCode.UNSIGNED, e.getMessage())));
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
                Sessions.set(key, wrapper.getValue(), wrapper.getExpiredSeconds());
            }
        }
    }

    private void clearAllThreadLocal() {
        ThreadContext.remove(CoupledConstant.LOG_MDC_INSIGNIA);
        RequestTrackContext.clearRequestTrack();
    }

}

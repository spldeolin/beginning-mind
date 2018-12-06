package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.CheckActuatorTokenHandler;
import com.spldeolin.beginningmind.core.security.CheckKilledHandler;
import com.spldeolin.beginningmind.core.security.CheckSignedHandler;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.RequestTrackContext;
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
    private CheckActuatorTokenHandler checkActuatorTokenHandler;

    @Autowired
    private CheckKilledHandler checkKilledHandler;

    @Autowired
    private CheckSignedHandler checkSignedHandler;

    @Autowired
    private SessionReflashHandler sessionReflashHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // todo security（鉴权）
        try {
            checkActuatorTokenHandler.ensureTokenCorrect(request);
            checkKilledHandler.ensureNotKilled(request);
            checkSignedHandler.ensureSigned(request);
        } catch (UnsignedException e) {
            returnExceptionAsJson(response, e);
            return;
        }

        // 填入登录者信息
        if (Signer.isSigning()) {
            RequestTrackContext.getRequestTrack().setUserId(Signer.userId());
        }

        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // 填入request和response的content（同步）
        fillContent(RequestTrackContext.getRequestTrack(), wrappedRequest, wrappedResponse);

        // 刷新会话的失效时间（异步）
        sessionReflashHandler.asyncReflashExpire(Sessions.session());
    }

    private void fillContent(RequestTrackDTO track, ContentCachingRequestWrapper wrappedRequest,
            ContentCachingResponseWrapper wrappedResponse) {
        String requestContent = null;
        String responseContent = null;
        try {
            requestContent = new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("读取requestContent失败", e);
        }
        try {
            responseContent = new String(wrappedResponse.getContentAsByteArray(),
                    wrappedResponse.getCharacterEncoding());
            wrappedResponse.copyBodyToResponse();
        } catch (IOException e) {
            log.error("读取responseContent失败", e);
        }
        track.setRequestContent(requestContent);
        track.setResponseContent(responseContent);
    }

    private void returnExceptionAsJson(HttpServletResponse response, UnsignedException e) throws IOException {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write(Jsons.toJson(RequestResult.failure(ResultCode.UNSIGNED, e.getMessage())));
    }

}

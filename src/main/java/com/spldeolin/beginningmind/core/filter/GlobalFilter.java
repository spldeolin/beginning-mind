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
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
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
    private SessionReflashHandler sessionReflashHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
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

}

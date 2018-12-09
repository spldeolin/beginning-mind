package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：SecurityFilter的内侧
 *
 * 前置：包装并替换request和response对象
 *
 * 后置：从包装对象读取content，为response包装对象调用
 *
 * @author Deolin 2018/12/06
 */
@Order(ReadContentFilter.ORDER)
@Component
@Log4j2
public class ReadContentFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + SecurityFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(request, response);

        // 向RequestTrack填入request和response的content（同步）
        fillContent(WebContext.getRequestTrack(), wrappedRequest, wrappedResponse);
    }

    private void fillContent(RequestTrack track, ContentCachingRequestWrapper wrappedRequest,
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

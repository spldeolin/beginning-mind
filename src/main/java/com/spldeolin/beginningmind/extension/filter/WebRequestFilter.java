package com.spldeolin.beginningmind.extension.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.config.SessionConfig;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.util.MdcRunnable;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * web请求过滤器
 *
 * @author Deolin 2018/12/06
 */
@Order(1)
@Component
@Log4j2
public class WebRequestFilter extends OncePerRequestFilter {

    /**
     * 必须与log4j2.yml的PatternLayout.pattern中的%X{insignia}占位符名相同
     */
    private static final String logMdcInsignia = "insignia";

    @Autowired
    private RequestTrackAsyncHandler requestTrackAsyncHandler;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.equals("/favicon.ico") || uri.equals("/")) {
            return;
        }

        // 将request、response、session、新构造的请求轨迹存入ThreadLocal
        RequestTrack requestTrack = new RequestTrack();
        WebContext.setRequestTrack(requestTrack);
        WebContext.setRequest(request);
        WebContext.setResponse(response);
        WebContext.setSession(request.getSession());

        // 设置Log MDC
        ThreadContext.put(logMdcInsignia, "[" + requestTrack.getInsignia() + "]");

        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(request, response);

        // 刷新全局会话的失效时间
        WebContext.getSession().setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);

        // 向RequestTrack填入request和response的content（同步）
        fillContent(requestTrack, wrappedRequest, wrappedResponse);

        // 清除Log MDC
        ThreadContext.remove(logMdcInsignia);

        // 补全并保存RequestTrack对象（异步）
        taskExecutor
                .execute(new MdcRunnable(() -> requestTrackAsyncHandler.asyncCompleteAndSave(requestTrack, request)));

        // 清空ThreadLocal
        WebContext.removeRequestTrack();
        WebContext.removeRequest();
        WebContext.removeResponse();
        WebContext.removeSession();
    }

    private void fillContent(RequestTrack requestTrack, ContentCachingRequestWrapper wrappedRequest,
            ContentCachingResponseWrapper wrappedResponse) {
        String requestContent = "";
        String responseContent = "";
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
        requestTrack.setRequestContent(requestContent);
        requestTrack.setResponseContent(responseContent);
    }

}

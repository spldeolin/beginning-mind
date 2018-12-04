package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;
import com.github.pagehelper.page.PageMethod;
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

    private ErrorAttributes errorAttributes;

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
        filterChain.doFilter(wrappedRequest, response);
        log.info(new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8));

        // 完成并保存
        requestTrackService.completeAndSave(requestTrackDTO, request);

        // 刷新会话与每个会话k-v的失效时间
        reflashSessionExpire();
        reflashSessionAttributeExpire();

        // 清除ThreadLocal（分页、Log MDC、请求轨迹）
        PageMethod.clearPage();
        removeLogMDC();
        RequestTrackContext.clearRequestTrack();
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

    private void removeLogMDC() {
        ThreadContext.remove(CoupledConstant.LOG_MDC_INSIGNIA);
    }


    protected Map<String, Object> getTrace(HttpServletRequest request, int status) {
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        Principal principal = request.getUserPrincipal();

        Map<String, Object> trace = new LinkedHashMap<>();
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        trace.put("principal", principal.getName());
        trace.put("query", request.getQueryString());
        trace.put("statusCode", status);

        if (exception != null && this.errorAttributes != null) {
            trace.put("error", this.errorAttributes
                    .getErrorAttributes((ServerRequest) new ServletRequestAttributes(request), true));
        }

        return trace;
    }

    private void getBody(ContentCachingRequestWrapper request, Map<String, Object> trace) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }

                trace.put("body", payload);
            }
        }
    }
}

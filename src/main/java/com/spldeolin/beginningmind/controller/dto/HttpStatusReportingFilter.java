package com.spldeolin.beginningmind.controller.dto;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.util.RequestContextUtil;

/**
 * 过滤器：用于请求结束后，获取请求回应的HTTP STATUS
 * <pre>
 * 所有正常返回的请求STATUS都是“0”，（不知道为什么不是200？）
 * HTTP404的请求STATUS会是“404”，
 * 框架层面的BUG会是401、403、500。
 * STATUS非“0”的请求都会转发到“/error”路由的请求，
 * 在这其中，非“404”的框架层面BUG非常应该存入request.attribution中，
 * 让“/error”请求作出区别对待。
 * </pre>
 */
@Component
public class HttpStatusReportingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        // 内部类
        HttpServletResponseWrapper response = new HttpServletResponseWrapper((HttpServletResponse) res) {
            private int httpStatus;

            @Override
            public void sendError(int sc) throws IOException {
                httpStatus = sc;
                super.sendError(sc);
            }

            @Override
            public void sendError(int sc, String msg) throws IOException {
                httpStatus = sc;
                super.sendError(sc, msg);
            }

            @Override
            public void setStatus(int sc) {
                httpStatus = sc;
                super.setStatus(sc);
            }

            public int getStatus() {
                return httpStatus;
            }
        };
        chain.doFilter(req, response);
        int status = response.getStatus();
        if (0 != status) {
            RequestContextUtil.request().setAttribute("{HTTP_STATUS_CODE}", status);
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }

}

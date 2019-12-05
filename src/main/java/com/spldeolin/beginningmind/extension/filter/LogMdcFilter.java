package com.spldeolin.beginningmind.extension.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.extension.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.extension.filter.constant.FilterOrderConstant;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：RequestTrackFilter的内侧
 *
 * 前置：从RequestTrack获取insignia，存入Log MDC
 *
 * 后置：清空Log MDC
 *
 * @author Deolin 2018/12/06
 */
@Order(FilterOrderConstant.LOG_MDC_FILTER_ORDER)
@Component
@Log4j2
public class LogMdcFilter extends OncePerRequestFilter {

    /**
     * 必须与log4j2.yml的PatternLayout.pattern中的%X{insignia}占位符名相同
     */
    private static final String LOG_MDC_INSIGNIA = "insignia";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        // 设置Log MDC
        ThreadContext.put(LOG_MDC_INSIGNIA, "[" + track.getInsignia() + "]");

        filterChain.doFilter(request, response);

        // 清除Log MDC
        ThreadContext.remove(LOG_MDC_INSIGNIA);
    }

}

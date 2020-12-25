package com.spldeolin.beginningmind.extension.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.extension.constant.RequestTrackConstant;
import com.spldeolin.beginningmind.extension.javabean.RequestTrack;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求轨迹过滤器
 *
 * @author Deolin 2018/12/06
 */
@Component
@Slf4j
public class BizFilter extends OncePerRequestFilter implements Ordered {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        RequestTrack.current().getMore().put("aaaa", "你好啊");

        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return RequestTrackConstant.REQUEST_TRACK_FILTER_ORDER + 1;
    }

}

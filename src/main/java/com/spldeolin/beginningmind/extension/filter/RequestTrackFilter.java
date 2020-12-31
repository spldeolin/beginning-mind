package com.spldeolin.beginningmind.extension.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.extension.constant.RequestTrackConstant;
import com.spldeolin.beginningmind.extension.handle.FullUrlHandle;
import com.spldeolin.beginningmind.extension.handle.InsigniaCreationHandle;
import com.spldeolin.beginningmind.extension.handle.MdcHandle;
import com.spldeolin.beginningmind.extension.handle.ReportRequestTrackHandle;
import com.spldeolin.beginningmind.extension.handle.RequestIgnoreHandle;
import com.spldeolin.beginningmind.extension.javabean.RequestTrack;
import com.spldeolin.beginningmind.extension.javabean.RequestTrack.RequestTrackBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求轨迹过滤器
 *
 * @author Deolin 2018/12/06
 */
@Component
@Slf4j
public class RequestTrackFilter extends OncePerRequestFilter implements Ordered {

    @Autowired
    private RequestIgnoreHandle requestIgnoreHandle;

    @Autowired
    private InsigniaCreationHandle insigniaCreationHandle;

    @Autowired
    private MdcHandle mdcHandle;

    @Autowired
    private FullUrlHandle fullUrlHandle;

    @Autowired
    private ReportRequestTrackHandle reportRequestTrackHandle;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 过滤规则
        if (requestIgnoreHandle.isIgnore(request)) {
            filterChain.doFilter(request, response);
        }

        // 构造RequestTrack对象，保存到上下文
        RequestTrack track = RequestTrack.current();
        if (track == null) {
            RequestTrackBuilder builder = RequestTrack.builder();
            builder.insignia(insigniaCreationHandle.createInsignia(request));
            builder.requestArrivedAt(LocalDateTime.now());
            builder.httpMethod(request.getMethod());
            builder.uri(request.getRequestURI());
            builder.fullUrl(fullUrlHandle.parseFullUrl(request));
            builder.rawRequest(request);
            builder.rawResponse(response);
            track = builder.build();
        }
        RequestTrack.setCurrent(track);

        // 设置Log MDC
        mdcHandle.putInsignia(track.getInsignia());
        mdcHandle.putOtherMdcs(track);

        // 报告请求到达
        log.info(reportRequestTrackHandle.buildArrivedReport(track).toString());

        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {

            // 报告请求离开
            StringBuilder report = reportRequestTrackHandle.buildLeavedReport(track, wrappedRequest, wrappedResponse);
            StringBuilder moreReport = reportRequestTrackHandle.buildMoreLeavedReport(track.getMore());
            log.info(report.append(moreReport).toString());

            // insignia保存到response报文
            response.setHeader(RequestTrackConstant.INSIGNIA_PLACEHOLDER, track.getInsignia());

            // 清空上下文
            mdcHandle.removeAllMdcs();
            RequestTrack.removeCurrent();
        }
    }

    @Override
    public int getOrder() {
        return RequestTrackConstant.REQUEST_TRACK_FILTER_ORDER;
    }

}

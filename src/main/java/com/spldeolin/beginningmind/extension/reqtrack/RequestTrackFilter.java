package com.spldeolin.beginningmind.extension.reqtrack;

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
import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack.RequestTrackBuilder;
import com.spldeolin.beginningmind.extension.reqtrack.handle.FullUrlHandle;
import com.spldeolin.beginningmind.extension.reqtrack.handle.InsigniaCreationHandle;
import com.spldeolin.beginningmind.extension.reqtrack.handle.MdcHandle;
import com.spldeolin.beginningmind.extension.reqtrack.handle.ReportRequestTrackHandle;
import com.spldeolin.beginningmind.extension.reqtrack.handle.RequestExclusionHandle;
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
    private RequestExclusionHandle requestIgnoreHandleExcluded;

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
        if (requestIgnoreHandleExcluded.isExcluded(request)) {
            filterChain.doFilter(request, response);
        }

        // 构造RequestTrack对象，保存到上下文
        RequestTrackBuilder builder = RequestTrack.builder();
        builder.insignia(insigniaCreationHandle.createInsignia(request));
        builder.requestArrivedAt(LocalDateTime.now());
        builder.httpMethod(request.getMethod());
        builder.uri(request.getRequestURI());
        builder.fullUrl(fullUrlHandle.parseFullUrl(request));
        builder.rawRequest(request);
        builder.rawResponse(response);
        RequestTrack requestTrack = builder.build();
        RequestTrack.setCurrent(requestTrack);

        // 设置Log MDC
        mdcHandle.putInsignia(requestTrack.getInsignia());
        mdcHandle.putOtherMdcs(requestTrack);

        // 请求到达时的报告
        log.info(reportRequestTrackHandle.buildArrivedReport(requestTrack).toString());

        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // insignia保存到response报文
            response.setHeader(RequestTrackConstant.INSIGNIA_PLACEHOLDER, requestTrack.getInsignia());

            // 请求离开时的报告
            StringBuilder report = reportRequestTrackHandle.buildLeavedReport(requestTrack, wrappedRequest, wrappedResponse);
            StringBuilder moreReport = reportRequestTrackHandle.buildMoreLeavedReport(requestTrack.getMore());
            log.info(report.append(moreReport).toString());

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

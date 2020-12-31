package com.spldeolin.beginningmind.extension.reqtrack.handle;

import java.util.Map;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack;

/**
 * 报告请求轨迹
 *
 * @author Deolin 2020-12-25
 */
public interface ReportRequestTrackHandle {

    /**
     * 请求到达时的报告
     */
    StringBuilder buildArrivedReport(RequestTrack requestTrack);

    /**
     * 请求离开时的报告（基本信息）
     */
    StringBuilder buildLeavedReport(RequestTrack requestTrack,
            ContentCachingRequestWrapper contentCachingRequestWrapper,
            ContentCachingResponseWrapper contentCachingResponseWrapper);

    /**
     * 请求离开时的报告（更多信息）
     */
    StringBuilder buildMoreLeavedReport(Map<String, Object> more);

}
package com.spldeolin.beginningmind.extension.reqtrack.handle;

import java.util.Map;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack;

/**
 * @author Deolin 2020-12-25
 */
public interface ReportRequestTrackHandle {

    StringBuilder buildArrivedReport(RequestTrack requestTrack);

    StringBuilder buildLeavedReport(RequestTrack requestTrack, ContentCachingRequestWrapper contentCachingRequestWrapper,
            ContentCachingResponseWrapper contentCachingResponseWrapper);

    StringBuilder buildMoreLeavedReport(Map<String, Object> more);

}
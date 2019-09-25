package com.spldeolin.beginningmind.filter.async;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.filter.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.util.Jsons;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/06
 */
@Component
@Log4j2
public class RequestTrackAsyncHandler {

    public void asyncCompleteAndSave(RequestTrackDTO track, HttpServletRequest request) {
        analysizRequestTrack(track, request);
        saveTrackToEs(track);
    }

    private void saveTrackToEs(RequestTrackDTO trackDTO) {
        log.info(Jsons.beautify(trackDTO));
    }

    private void analysizRequestTrack(RequestTrackDTO track, HttpServletRequest request) {
        track.setHttpMethod(request.getMethod());

        track.setUrl(getFullUrlFromRequest(request));

        track.setUserAgent(request.getHeader("User-Agent"));

        track.setReferer(request.getHeader("Referer"));

        track.setElapsed(track.getStopwatch().elapsed(TimeUnit.MILLISECONDS));

        track.setIp(getIpFromRequest(request));
    }

    private String getFullUrlFromRequest(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(64);
        url.append(request.getRequestURL());
        for (Entry<String, String[]> queryValuesEachKey : request.getParameterMap().entrySet()) {
            String queryKey = queryValuesEachKey.getKey();
            for (String queryValue : queryValuesEachKey.getValue()) {
                if (queryValue != null) {
                    url.append("&");
                    url.append(queryKey);
                    url.append("=");
                    url.append(queryValue);
                }
            }
        }
        return url.toString().replaceFirst("&", "?");
    }

    private String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

package com.spldeolin.beginningmind.core.filter.async;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/06
 */
@Component
@Log4j2
public class RequestTrackAsyncHandler {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Async
    public void asyncCompleteAndSave(RequestTrack track, HttpServletRequest request) {
        analysizRequestTrack(track, request);
        saveTrack(track);
    }

    private void saveTrack(RequestTrack track) {
        log.info("rq-" + track.getInsignia() + System.getProperty("line.separator") + track);
    }

    private void analysizRequestTrack(RequestTrack track, HttpServletRequest request) {
        track.setHttpMethod(request.getMethod());

        track.setUrl(getFullUrlFromRequest(request));

        track.setElapsed(track.getStopwatch().elapsed(TimeUnit.MILLISECONDS));

        Long signedUserId = track.getUserId();
        if (signedUserId != null) {
            User user = userService.get(track.getUserId()).orElseThrow(() -> new RuntimeException("不存在或是已被删除"));
            track.setUserName(user.getName());
            track.setUserMobile(user.getMobile());
        }

        track.setIp(getIpFromRequest(request));

        track.setSessionId(request.getSession().getId());

        track.setActiveProfile(environment.getActiveProfiles()[0]);
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

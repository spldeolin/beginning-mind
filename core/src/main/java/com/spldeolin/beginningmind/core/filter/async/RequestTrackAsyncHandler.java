package com.spldeolin.beginningmind.core.filter.async;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.repository.UserRepo;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrackDTO;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/06
 */
@Component
@Log4j2
public class RequestTrackAsyncHandler {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void asyncCompleteAndSave(RequestTrackDTO track, HttpServletRequest request) {
        analysizRequestTrack(track, request);
        saveTrackToEs(track);
    }

    private void saveTrackToEs(RequestTrackDTO track) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(track).build();

        try {
            elasticsearchTemplate.index(indexQuery);
            log.info("Saving request track succeed. [{}]", track.getInsignia());
        } catch (Exception e) {
            log.warn("Saving request track failed. [{}]", track.toString());
        }
    }

    private void analysizRequestTrack(RequestTrackDTO track, HttpServletRequest request) {
        track.setHttpMethod(request.getMethod());

        track.setUrl(getFullUrlFromRequest(request));

        track.setUserAgent(request.getHeader("User-Agent"));

        track.setReferer(request.getHeader("Referer"));

        track.setElapsed(track.getStopwatch().elapsed(TimeUnit.MILLISECONDS));

        Long signedUserId = track.getUserId();
        if (signedUserId != null) {
            UserEntity user = userRepo.get(track.getUserId()).orElseThrow(() -> new RuntimeException("不存在或是已被删除"));
            track.setUserName(user.getName());
            track.setUserMobile(user.getMobile());
        }

        track.setIp(getIpFromRequest(request));

        track.setSessionId(request.getSession().getId());
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

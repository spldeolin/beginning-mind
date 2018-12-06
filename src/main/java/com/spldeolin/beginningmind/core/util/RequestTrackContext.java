package com.spldeolin.beginningmind.core.util;

import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import lombok.extern.log4j.Log4j2;

/**
 * 请求轨迹的线程上下文
 *
 * @author Deolin 2018/12/01
 */
@Log4j2
public class RequestTrackContext {

    private static final ThreadLocal<RequestTrackDTO> threadLocal = new ThreadLocal<>();

    /**
     * @param requestTrack 当前请求轨迹
     */
    public static void setRequestTrack(RequestTrackDTO requestTrack) {
        threadLocal.set(requestTrack);
    }

    /**
     * @return 当前请求轨迹
     */
    public static RequestTrackDTO getRequestTrack() {
        return threadLocal.get();
    }

    /**
     * @return 当前请求轨迹的标识
     */
    public static String getInsignia() {
        RequestTrackDTO track = getRequestTrack();
        if (track == null) {
            return "";
        } else {
            return track.getInsignia();
        }
    }

    /**
     * 清空当前请求轨迹
     */
    public static void clearRequestTrack() {
        threadLocal.remove();
    }

}

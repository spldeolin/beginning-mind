package com.spldeolin.beginningmind.core.util;

import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import lombok.extern.log4j.Log4j2;

/**
 * 请求轨迹的线程上下文
 *
 * @author Deolin 2018/12/01
 */
@Log4j2
public class RequestTrackContext {

    private static final ThreadLocal<RequestTrack> REQUEST_TRACK_CONTEXT = new ThreadLocal<>();

    /**
     * @param requestTrack 当前请求轨迹
     */
    public static void setRequestTrack(RequestTrack requestTrack) {
        REQUEST_TRACK_CONTEXT.set(requestTrack);
    }

    /**
     * @return 当前请求轨迹
     */
    public static RequestTrack getRequestTrack() {
        return REQUEST_TRACK_CONTEXT.get();
    }

    /**
     * @return 当前请求轨迹的标识
     */
    public static String getInsignia() {
        RequestTrack track = getRequestTrack();
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
        REQUEST_TRACK_CONTEXT.remove();
    }

}

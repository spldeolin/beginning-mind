package com.spldeolin.beginningmind.core.util;

import javax.servlet.http.HttpServletRequest;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import lombok.experimental.UtilityClass;

/**
 * 工具类：简化通过RequestContextUtils获取HttpServletRequest进行操作的代码量
 *
 * @author Deolin
 */
@UtilityClass
public class Requests {

    private static final String REQUEST_TRACK_KEY = "com.spldeolin.beginningmind.core.util.Requests.REQUEST_TRACK_KEY";

    public static HttpServletRequest request() {
        return RequestContextUtils.request();
    }

    public static void set(String key, Object value) {
        request().setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) request().getAttribute(key);
    }

    public static void remove(String key) {
        request().removeAttribute(key);
    }

    /**
     * @param requestTrack 当前请求轨迹
     */
    public static void setRequestTrack(RequestTrackDTO requestTrack) {
        request().setAttribute(REQUEST_TRACK_KEY, requestTrack);
    }

    /**
     * @return 当前请求轨迹
     */
    public static RequestTrackDTO getRequestTrack() {
        return (RequestTrackDTO) request().getAttribute(REQUEST_TRACK_KEY);
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

}

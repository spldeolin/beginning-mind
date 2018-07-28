package com.spldeolin.beginningmind.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrack;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestContextUtils {

    public static HttpServletRequest request() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    public static HttpServletResponse response() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getResponse();
    }

    public static Cookie[] cookies() {
        Cookie[] cookies = request().getCookies();
        if (cookies == null) {
            cookies = new Cookie[0];
        }
        return cookies;
    }

    /**
     * @param requestTrack  当前请求轨迹
     */
    public static void setRequestTrack(RequestTrack requestTrack) {
        request().setAttribute("{CURRENT_CONTROLLER_INFO}", requestTrack);
    }

    /**
     * @return 当前请求轨迹
     */
    public static RequestTrack getRequestTrack() {
        return (RequestTrack) request().getAttribute("{CURRENT_CONTROLLER_INFO}");
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

}

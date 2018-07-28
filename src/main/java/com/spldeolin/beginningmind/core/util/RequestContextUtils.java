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
     * 设置当前HTTP请求的控制器信息
     *
     * @param requestTrack 控制器信息
     */
    public static void setRequestTrack(RequestTrack requestTrack) {
        request().setAttribute("{CURRENT_CONTROLLER_INFO}", requestTrack);
    }

    /**
     * 获取当前HTTP请求的控制器信息
     *
     * @return 的控制器信息
     */
    public static RequestTrack getRequestTrack() {
        return (RequestTrack) request().getAttribute("{CURRENT_CONTROLLER_INFO}");
    }

}

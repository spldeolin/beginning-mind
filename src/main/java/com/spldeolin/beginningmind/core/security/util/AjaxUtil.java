package com.spldeolin.beginningmind.core.security.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.spldeolin.beginningmind.core.util.Jsons;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Ajax工具类
 * <pre>
 * 用于判断请求是否为Ajax请求，
 * 用于返回application/json的Response
 * </pre>
 *
 * @author Deolin 2018/07/27
 */
@UtilityClass
@Log4j2
public class AjaxUtil {

    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    public static void outputJson(ServletResponse response, Object responseBody) throws IOException {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(Jsons.toJson(responseBody));
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

}

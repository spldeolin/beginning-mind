package com.spldeolin.beginningmind.core.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.spldeolin.beginningmind.core.controller.UrlForwardToExceptionController;
import lombok.AllArgsConstructor;

/**
 * Actuator相关请求过滤器。
 * <pre>
 * 用于处理actuator提供的相关请求，为这类请求专门提供过滤策略
 * </pre>
 */
@AllArgsConstructor
public class ActuatorFilter extends AccessControlFilter {

    public static final String MARK = "actuator";

    private TempTokenHolder tempTokenHolder;

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        String token = req.getParameter("token");
        return tempTokenHolder.getActuatorToken().equals(token);
    }

    // 如果isAccessAllowed()返回false，则调用这个方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 不保存request，直接重定向掉
        WebUtils.issueRedirect(request, response, UrlForwardToExceptionController.UNAUTHORIZED_URL);
        return false;
    }

}

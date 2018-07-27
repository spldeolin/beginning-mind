package com.spldeolin.beginningmind.core.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.AjaxUtils;
import com.spldeolin.beginningmind.core.security.TempTokenHolder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Actuator相关请求过滤器。
 * <pre>
 * 用于处理actuator提供的相关请求，为这类请求专门提供过滤策略
 * </pre>
 */
@Log4j2
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
        log.info("请求被ActuatorFilter过滤，返回“权限不足”");
        AjaxUtils.outputJson(response, RequestResult.failure(ResultCode.FORBIDDEN));
        return false;
    }

}

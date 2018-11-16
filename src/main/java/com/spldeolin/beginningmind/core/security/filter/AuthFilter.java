package com.spldeolin.beginningmind.core.security.filter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.util.AjaxUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 鉴权过滤器
 *
 * <pre>
 * 请求登录者必须拥有请求的权限，才可以通过过滤。
 * 这个过滤器会在ShiroConfig的ShiroFilterFactoryBean中 被注册为auth标记
 * </pre>
 *
 * @author Deolin 2018/07/27
 */
@Log4j2
public class AuthFilter extends PermissionsAuthorizationFilter {

    public static final String MARK = "auth";

    /**
     * @see AuthorizationFilter#onAccessDenied(ServletRequest, ServletResponse)
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            log.info("请求被AuthFilter过滤，会话失效，返回“未登录或登录超时”");
            AjaxUtil.outputJson(response, RequestResult.failure(ResultCode.UNSIGNED));
        } else {
            log.info("请求被AuthFilter过滤，返回“权限不足”");
            AjaxUtil.outputJson(response, RequestResult.failure(ResultCode.FORBIDDEN));
        }
        return false;
    }

}

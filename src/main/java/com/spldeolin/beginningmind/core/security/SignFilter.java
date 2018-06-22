/*
    这里有个巨大的问题。RequestMappingHandlerMapping这个对象，
    通过@Autowird，直接或间接注入一个@Configuration内时，启动会报错，提示 No ServletContext set.

    如果我在这个类中，通过@Autowird获取RequestMappingHandlerMapping对象，就算是“间接”，
    因为这个类会注入到ShiroConfig中，而ShiroConfig声明了@Configuration。

    原因可能是RequestMappingHandlerMapping需要在开发者定义的Bean都被容器实例化后，才能实例化。
    如果@Autowired进了一个@Configuration，那就会在其他Bean实例化的过程中，强行实例化，从而引起报错。

    所以，现在的解决方式是，在isAccessAllowed()方法内，通过ApplicationContext静态地获取RequestMappingHandlerMapping对象，
    isAccessAllowed()被调用的时候，容器一定是实例化完毕了，那时候通过getBean()获取RequestMappingHandlerMapping时，
    返回是的正确实例化的RequestMappingHandlerMapping单例对象，那样就没有问题了。

    Deolin 2018/05/18

    由于重构了过滤器链，没有/**=anon了，所以在这个过滤器中不用考虑Not Found了。上面这段话可以认为过时了。

    Deolin 2018/05/26

 */

package com.spldeolin.beginningmind.core.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.spldeolin.beginningmind.core.controller2.UrlForwardToExceptionController;

/**
 * 登录过滤器
 * <pre>
 * 请求只有是“已认证”或“rememberMe”，才可以通过过滤，
 * 另外，NotFound的请求，直接通过这个过滤器
 * 这个过滤器会在ShiroConfig的ShiroFilterFactoryBean中 被注册为sign标记
 * </pre>
 *
 * @author Deolin 2018/05/18
 */
public class SignFilter extends AccessControlFilter {

    public static final String MARK = "sign";

    @Override
    public void setLoginUrl(String loginUrl) {
        super.setLoginUrl(UrlForwardToExceptionController.SHIROFILTER_LOGINURL_URL);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        Subject subject = getSubject(req, resp);
        // 放行“已认证”
        if (subject.isAuthenticated()) {
            return true;
        }
        // 放行“rememberMe”
        if (subject.isRemembered()) {
            return true;
        }

        // 判断请求是否NotFound
        //boolean notFound = true;
        //Map<RequestMappingInfo, HandlerMethod> handlerMethods = ApplicationContext.getBean(
        //        RequestMappingHandlerMapping.class).getHandlerMethods();
        //for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
        //    RequestMappingInfo info = entry.getKey();
        //    if (null != info.getMatchingCondition((HttpServletRequest) req)) {
        //        notFound = false;
        //    }
        //}
        //// 放行NotFound请求
        //if (notFound) {
        //    return true;
        //}
        return false;
    }

    // 如果isAccessAllowed()返回false，则调用这个方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, UrlForwardToExceptionController.SHIROFILTER_LOGINURL_URL);
        return false;
    }

}

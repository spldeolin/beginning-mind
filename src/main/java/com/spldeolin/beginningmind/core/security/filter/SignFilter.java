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

package com.spldeolin.beginningmind.core.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.AjaxUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 登录过滤器
 * <pre>
 * 请求只有是“已认证”或“rememberMe”，才可以通过过滤。
 * 这个过滤器会在ShiroConfig的ShiroFilterFactoryBean中 被注册为sign标记
 * </pre>
 *
 * @author Deolin 2018/05/18
 */
@Log4j2
public class SignFilter extends AccessControlFilter {

    public static final String MARK = "sign";

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        Subject subject = getSubject(req, resp);
        // 放行“已认证”、放行“rememberMe”
        return subject.isAuthenticated() || subject.isRemembered();
    }

    // 如果isAccessAllowed()返回false，则调用这个方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("请求被SignFilter过滤，返回“未登录或登录超时”");
        AjaxUtils.outputJson(response, RequestResult.failure(ResultCode.UNSIGNED));
        return false;
    }

}

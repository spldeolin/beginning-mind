package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import com.spldeolin.beginningmind.core.security.ActuatorTokenChecker;
import com.spldeolin.beginningmind.core.security.PermissionChecker;
import com.spldeolin.beginningmind.core.security.KilledChecker;
import com.spldeolin.beginningmind.core.security.SignedChecker;
import com.spldeolin.beginningmind.core.security.exception.UnauthorizeException;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：LogMdcFilter的内侧
 *
 * 前置：在启用安全模块时，Actuator请求的token校验 -> 是否被请离校验 -> 是否登录校验 -> 鉴权校验 -> 填入登录者信息
 *
 * 后置：向RequestTrack填入登录者信息
 *
 * @author Deolin 2018/12/06
 */
@Order(SecurityFilter.ORDER)
@Component
@Log4j2
public class SecurityFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + LogMdcFilter.ORDER;

    @Autowired
    private ActuatorTokenChecker checkActuatorTokenHandler;

    @Autowired
    private KilledChecker checkKilledHandler;

    @Autowired
    private SignedChecker checkSignedHandler;

    @Autowired
    private PermissionChecker checkAuthHandler;

    @Autowired
    private CoreProperties coreProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if (coreProperties.getEnableSecurity()) {
            try {
                // actuator token是否正确
                checkActuatorTokenHandler.ensureTokenCorrect(request);
                // 是否被请离
                checkKilledHandler.ensureNotKilled(request);
                // 是否未登录
                checkSignedHandler.ensureSigned(request);
                // 是否未授权
                checkAuthHandler.ensureAuth(request);
            } catch (UnsignedException | UnauthorizeException e) {
                returnExceptionAsJson(response, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);

        // 向RequestTrack填入登录者信息
        if (Signer.isSigning()) {
            RequestTrack track = WebContext.getRequestTrack();
            if (track == null) {
                throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
            }
            track.setUserId(Signer.userId());
        }
    }

    private void returnExceptionAsJson(HttpServletResponse response, String exceptionMessage) throws IOException {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write(Jsons.toJson(RequestResult.failure(ResultCode.UNSIGNED, exceptionMessage)));
    }

}

package com.spldeolin.beginningmind.core.service.impl;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Service
@Log4j2
public class RequestTrackServiceImpl implements RequestTrackService {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Override
    public void fillJoinPointInfo(RequestTrackDTO track, JoinPoint joinPoint) {
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        track.setFullyQualifiedName(joinPoint.getTarget().getClass().getName() + "#" + requestMethod.getName());
        track.setMethod(requestMethod);
        track.setParameterNames(parameterNames);
        track.setParameterValues(parameterValues);
    }

}
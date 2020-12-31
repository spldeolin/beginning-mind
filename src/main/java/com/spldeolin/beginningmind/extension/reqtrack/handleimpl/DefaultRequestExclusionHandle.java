package com.spldeolin.beginningmind.extension.reqtrack.handleimpl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.extension.reqtrack.handle.RequestExclusionHandle;

/**
 * @author Deolin 2020-12-25
 */
@Component
public class DefaultRequestExclusionHandle implements RequestExclusionHandle {

    public boolean isExcluded(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.equals("/favicon.ico") || uri.equals("/");
    }

}
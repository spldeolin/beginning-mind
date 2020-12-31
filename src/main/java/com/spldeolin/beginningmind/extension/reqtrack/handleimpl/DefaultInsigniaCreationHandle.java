package com.spldeolin.beginningmind.extension.reqtrack.handleimpl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.extension.reqtrack.handle.InsigniaCreationHandle;
import com.spldeolin.beginningmind.service.SnowFlakeService;

/**
 * @author Deolin 2020-12-25
 */
@Component
public class DefaultInsigniaCreationHandle implements InsigniaCreationHandle {

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Override
    public String createInsignia(HttpServletRequest request) {
        String result = request.getHeader("insignia");
        if (result == null) {
            result = "rt" + snowFlakeService.nextId();
        }
        return result;
    }

}
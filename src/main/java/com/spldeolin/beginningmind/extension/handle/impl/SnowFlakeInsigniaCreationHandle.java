package com.spldeolin.beginningmind.extension.handle.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.extension.handle.InsigniaCreationHandle;
import com.spldeolin.beginningmind.service.SnowFlakeService;

/**
 * @author Deolin 2020-12-25
 */
@Component
public class SnowFlakeInsigniaCreationHandle implements InsigniaCreationHandle {

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Override
    public String createInsignia(HttpServletRequest request) {
        String result = request.getHeader("insignia");
        if (result == null) {
            result = String.valueOf(snowFlakeService.nextId());
        }
        return result;
    }

}
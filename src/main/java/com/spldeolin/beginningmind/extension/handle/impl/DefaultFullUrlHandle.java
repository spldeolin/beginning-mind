package com.spldeolin.beginningmind.extension.handle.impl;

import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.extension.handle.FullUrlHandle;

/**
 * @author Deolin 2020-12-25
 */
@Component
public class DefaultFullUrlHandle implements FullUrlHandle {

    @Override
    public String parseFullUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(128);
        url.append(request.getRequestURL());
        for (Entry<String, String[]> queryValuesEachKey : request.getParameterMap().entrySet()) {
            String queryKey = queryValuesEachKey.getKey();
            for (String queryValue : queryValuesEachKey.getValue()) {
                if (queryValue != null) {
                    url.append("&");
                    url.append(queryKey);
                    url.append("=");
                    url.append(queryValue);
                }
            }
        }
        return url.toString().replaceFirst("&", "?");
    }

}
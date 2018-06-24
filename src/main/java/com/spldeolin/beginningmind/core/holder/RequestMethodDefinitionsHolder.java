package com.spldeolin.beginningmind.core.holder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/24
 */
@Component
@Log4j2
public class RequestMethodDefinitionsHolder {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private List<String> mappings;

    public void init() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        mappings = Lists.newArrayList();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String pattern : patterns) {
                pattern = pattern.replaceAll("\\{[^}]+}", "");
                if (pattern.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[0]) ||
                        pattern.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[1]) ||
                        pattern.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[2])) {
                    continue;
                }
                log.info("Holded mapping pattern [" + pattern + "]");
                mappings.add(pattern);
            }
        }
    }

    public boolean matchAnyMappings(String url) {
        if (mappings != null) {
            for (String mapping : mappings) {
                if (url.contains(mapping)) {
                    return true;
                }
            }
        }
        return false;
    }

}

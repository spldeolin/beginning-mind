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
import com.spldeolin.beginningmind.core.redis.RedisCache;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/24
 */
@Component
@Log4j2
public class RequestMethodDefinitionsHolder {

    private static final String KEY = "com.spldeolin.beginningmind.core.holder.RequestMethodDefinitionsHolder.KEY";

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private RedisCache redisCache;

    public void init() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        List<String> mappings = Lists.newArrayList();
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
                log.debug("Holded mapping pattern [" + pattern + "]");
                mappings.add(pattern);
            }
        }
        redisCache.set(KEY, mappings);
    }

    public boolean matchAnyMappings(String url) {
        List<String> mappings = redisCache.get(KEY);
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

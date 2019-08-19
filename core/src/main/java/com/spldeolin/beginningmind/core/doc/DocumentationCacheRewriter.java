package com.spldeolin.beginningmind.core.doc;

import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;

/**
 * 重写DocumentationCache中的部分属性值
 *
 * @author Deolin 2019-08-16
 */
@Component
@Log4j2
public class DocumentationCacheRewriter implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private DocumentationCache documentationCache;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (Documentation doc : documentationCache.all().values()) {
            for (Entry<String, ApiListing> apiEachGroup : doc.getApiListings().entries()) {
                apiEachGroup.getValue().getApis().forEach(api -> api.getOperations().forEach(operation -> {
                    operation.getResponseMessages().removeIf(one -> one.getCode() != 200);
                }));
            }
        }
    }

}

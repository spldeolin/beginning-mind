package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.common.service.MvcHandlerGeneratorService;
import com.spldeolin.allison1875.sqlapigenerator.SqlapiGeneratorConfig;
import com.spldeolin.allison1875.sqlapigenerator.SqlapiGeneratorModule;
import com.spldeolin.beginningmind.Application;
import com.spldeolin.beginningmind.allison1875.serviceimpl.common.MvcHandlerGeneratorServiceImpl2;

/**
 * @author Deolin 2024-02-05
 */
public class SqlapGeneratorBoot {

    public static void main(String[] args) {
        SqlapiGeneratorConfig config = new SqlapiGeneratorConfig();
        config.setCommonConfig(Constant.COMMON_CONFIG);
        config.setMethodName("hello4");
        config.setSelectListOrOne(true);
        config.setSql("select a from b");
        config.setMapperName("TableNamingMapper");
        config.setServiceName("AaaaService");
        config.setControllerName("DemoApiController");

        Allison1875.allison1875(Application.class, new SqlapiGeneratorModule(config) {
            @Override
            protected void configure() {
                super.configure();
                bind(MvcHandlerGeneratorService.class).toInstance(new MvcHandlerGeneratorServiceImpl2());
            }
        });
    }

}
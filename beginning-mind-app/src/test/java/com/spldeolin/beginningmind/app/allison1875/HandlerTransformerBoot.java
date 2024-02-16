package com.spldeolin.beginningmind.app.allison1875;

import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.common.service.MvcHandlerGeneratorService;
import com.spldeolin.allison1875.handlertransformer.HandlerTransformerConfig;
import com.spldeolin.allison1875.handlertransformer.HandlerTransformerModule;
import com.spldeolin.allison1875.handlertransformer.service.FieldService;
import com.spldeolin.beginningmind.app.Application;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.common.MvcHandlerGeneratorServiceImpl2;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.handlertransformer.FieldServiceImpl2;

/**
 * @author Deolin 2020-11-17
 */
public class HandlerTransformerBoot {

    public static void main(String[] args) {
        HandlerTransformerConfig config = new HandlerTransformerConfig();
        config.setCommonConfig(Constant.COMMON_CONFIG);
        config.setPageTypeQualifier("com.github.pagehelper.PageInfo");
        config.setEnableLotNoAnnounce(true);

        Allison1875.allison1875(Application.class, new HandlerTransformerModule(config) {
            @Override
            protected void configure() {
                super.configure();
                bind(FieldService.class).toInstance(new FieldServiceImpl2());
                bind(MvcHandlerGeneratorService.class).toInstance(new MvcHandlerGeneratorServiceImpl2());
            }
        });
    }

}
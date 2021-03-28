package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.handlertransformer.HandlerTransformerConfig;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;
import com.spldeolin.beginningmind.allison1875.module.BmHandlerTransformerModule;

/**
 * @author Deolin 2020-11-17
 */
public class HandlerTransformerBoot {

    public static void main(String[] args) {
        HandlerTransformerConfig config = ConfigConstant.handlerTransformerConfig;
        config.batchSetAllPackagesByWildcard("com.spldeolin.beginningmind.-");
        config.setAuthor("Deolin");
        Allison1875
                .allison1875(HandlerTransformerBoot.class, new BmHandlerTransformerModule(), ConfigConstant.baseConfig,
                        config);
    }

}
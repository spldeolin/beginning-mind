package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.querytransformer.QueryTransformerConfig;
import com.spldeolin.allison1875.querytransformer.QueryTransformerModule;
import com.spldeolin.beginningmind.Application;

/**
 * @author Deolin 2020-11-17
 */
public class QueryTransformerBoot {

    public static void main(String[] args) {
        QueryTransformerConfig config = new QueryTransformerConfig();
        config.setCommonConfig(Constant.COMMON_CONFIG);
        config.setEnableLotNoAnnounce(true);

        Allison1875.allison1875(Application.class, new QueryTransformerModule(config));
    }

}
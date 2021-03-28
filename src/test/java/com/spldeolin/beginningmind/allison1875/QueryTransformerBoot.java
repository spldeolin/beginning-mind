package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.querytransformer.QueryTransformerConfig;
import com.spldeolin.allison1875.querytransformer.QueryTransformerModule;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;

/**
 * @author Deolin 2020-11-17
 */
public class QueryTransformerBoot {

    public static void main(String[] args) {
        QueryTransformerConfig config = ConfigConstant.queryTransformerConfig;
        config.setMapperXmlDirectoryPath("src/main/resources/mapper");
        Allison1875.allison1875(QueryTransformerBoot.class, new QueryTransformerModule(), ConfigConstant.baseConfig,
                config);
    }

}
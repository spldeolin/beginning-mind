package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.querytransformer.QueryTransformerConfig;
import com.spldeolin.allison1875.querytransformer.processor.QueryTransformer;

/**
 * @author Deolin 2020-10-16
 */
public class QueryTransformerBoot {

    public static void main(String[] args) {
        Allison1875.allison1875(QueryTransformerBoot.class, new QueryTransformer());
    }

    static {
        QueryTransformerConfig conf = QueryTransformerConfig.getInstance();

        conf.setMapperXmlDirectoryPath("src/main/resources/mapper");

        conf.getEntityCommonPropertyTypes().put("id", "Long");
        conf.getEntityCommonPropertyTypes().put("insertedAt", "Date");
        conf.getEntityCommonPropertyTypes().put("updatedAt", "Date");
    }

}
package com.spldeolin.beginningmind.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;
import com.spldeolin.beginningmind.allison1875.module.BmDocAnalyzerModule;

/**
 * @author Deolin 2020-11-17
 */
public class DocAnalyzerBoot {

    public static void main(String[] args) {
        DocAnalyzerConfig config = ConfigConstant.docAnalyzerConfig;
        config.setDependencyProjectPaths(Lists.newArrayList());
        config.setGlobalUrlPrefix("");
        config.setYapiToken("bb007399eacfc009fcf3453f17f9acf75c36812311419f166453f4ad0536c955");
        Allison1875.allison1875(DocAnalyzerBoot.class, new BmDocAnalyzerModule(), ConfigConstant.baseConfig, config);

    }

}
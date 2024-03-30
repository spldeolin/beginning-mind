package com.spldeolin.beginningmind.app.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerModule;
import com.spldeolin.allison1875.docanalyzer.enums.FlushToEnum;
import com.spldeolin.allison1875.docanalyzer.service.EnumService;
import com.spldeolin.allison1875.docanalyzer.service.FieldService;
import com.spldeolin.allison1875.docanalyzer.service.ResponseBodyService;
import com.spldeolin.beginningmind.app.Application;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.docanalyzer.EnumServiceImpl2;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.docanalyzer.FieldServiceImpl2;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.docanalyzer.ResponseBodyServiceImpl2;

/**
 * @author Deolin 2020-11-17
 */
public class DocAnalyzerBoot {

    public static void main(String[] args) {
        DocAnalyzerConfig config = new DocAnalyzerConfig();
        config.setDependencyProjectDirectories(Lists.newArrayList());
        config.setGlobalUrlPrefix("bm");
        config.setFlushTo(FlushToEnum.YAPI);
        config.setYapiUrl("http://127.0.0.1:3000");
        config.setYapiToken("55bb5ee352612026c13f1911d8ef4e9e26a00e033cdf5861bff4bc2b9c60d5fa");
        config.setMarkdownDirectoryPath("./doc-markdowns");
        config.setEnableCurl(true);
        config.setEnableResponseBodySample(true);
        config.setEnableNoModifyAnnounce(true);
        config.setEnableLotNoAnnounce(true);

        Allison1875.allison1875(Application.class, new DocAnalyzerModule(config) {

            @Override
            protected void configure() {
                super.configure();
                bind(ResponseBodyService.class).toInstance(new ResponseBodyServiceImpl2());
                bind(FieldService.class).toInstance(new FieldServiceImpl2());
                bind(EnumService.class).toInstance(new EnumServiceImpl2());
            }
        });

    }

}
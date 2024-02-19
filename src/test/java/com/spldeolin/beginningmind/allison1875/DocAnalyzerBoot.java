package com.spldeolin.beginningmind.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerModule;
import com.spldeolin.allison1875.docanalyzer.enums.FlushToEnum;
import com.spldeolin.allison1875.docanalyzer.service.ConcernedResponseBodyAnalyzerService;
import com.spldeolin.allison1875.docanalyzer.service.EnumConstantAnalyzerService;
import com.spldeolin.allison1875.docanalyzer.service.FieldMoreInfoAnalyzerService;
import com.spldeolin.allison1875.docanalyzer.service.SpecificFieldDescriptionsService;
import com.spldeolin.beginningmind.Application;
import com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer.ConcernedResponseBodyAnalyzerServiceImpl2;
import com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer.EnumConstantAnalyzerServiceImpl2;
import com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer.FieldMoreInfoAnalyzerServiceImpl2;
import com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer.SpecificFieldDescriptionsServiceImpl2;

/**
 * @author Deolin 2020-11-17
 */
public class DocAnalyzerBoot {

    public static void main(String[] args) {
        DocAnalyzerConfig config = new DocAnalyzerConfig();
        config.setDependencyProjectDirectories(Lists.newArrayList());
        config.setGlobalUrlPrefix("");
        config.setFlushTo(Lists.newArrayList(FlushToEnum.YAPI, FlushToEnum.LOCAL_MARKDOWN));
        config.setYapiUrl("http://127.0.0.1:3000");
        config.setYapiToken("bb007399eacfc009fcf3453f17f9acf75c36812311419f166453f4ad0536c955");
        config.setMarkdownDirectoryPath("./doc-markdowns");
        config.setEnableCurl(true);
        config.setEnableResponseBodySample(true);
        config.setEnableNoModifyAnnounce(true);
        config.setEnableLotNoAnnounce(true);

        Allison1875.allison1875(Application.class, new DocAnalyzerModule(config) {

            @Override
            protected void configure() {
                super.configure();
                bind(ConcernedResponseBodyAnalyzerService.class).toInstance(
                        new ConcernedResponseBodyAnalyzerServiceImpl2());
                bind(EnumConstantAnalyzerService.class).toInstance(new EnumConstantAnalyzerServiceImpl2());
                bind(FieldMoreInfoAnalyzerService.class).toInstance(new FieldMoreInfoAnalyzerServiceImpl2());
                bind(SpecificFieldDescriptionsService.class).toInstance(new SpecificFieldDescriptionsServiceImpl2());
            }
        });

    }

}
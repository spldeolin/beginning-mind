package com.spldeolin.beginningmind.allison1875.module;

import com.spldeolin.allison1875.base.ancestor.Allison1875MainProcessor;
import com.spldeolin.allison1875.base.ancestor.Allison1875Module;
import com.spldeolin.allison1875.base.util.ValidateUtils;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.docanalyzer.handle.AnalyzeEnumConstantHandle;
import com.spldeolin.allison1875.docanalyzer.handle.MoreJpdvAnalysisHandle;
import com.spldeolin.allison1875.docanalyzer.handle.SpecificFieldDescriptionsHandle;
import com.spldeolin.allison1875.docanalyzer.processor.DocAnalyzer;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.EnumAncestorHandle;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.EnumNameJpdvAnalysisHandle;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.PageHelperDescriptionsHandle;

/**
 * @author Deolin 2020-12-06
 */
public class BmDocAnalyzerModule extends Allison1875Module {

    private final DocAnalyzerConfig docAnalyzerConfig;

    public BmDocAnalyzerModule(DocAnalyzerConfig docAnalyzerConfig) {
        this.docAnalyzerConfig = docAnalyzerConfig;
    }

    @Override
    public Class<? extends Allison1875MainProcessor> provideMainProcessorType() {
        return DocAnalyzer.class;
    }

    @Override
    protected void configure() {
        bind(AnalyzeEnumConstantHandle.class).to(EnumAncestorHandle.class);
        bind(MoreJpdvAnalysisHandle.class).to(EnumNameJpdvAnalysisHandle.class);
        bind(SpecificFieldDescriptionsHandle.class).to(PageHelperDescriptionsHandle.class);
        ValidateUtils.ensureValid(docAnalyzerConfig);
        bind(DocAnalyzerConfig.class).toInstance(docAnalyzerConfig);
    }

}
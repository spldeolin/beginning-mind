package com.spldeolin.beginningmind.allison1875.module;

import com.spldeolin.allison1875.docanalyzer.DocAnalyzerModule;
import com.spldeolin.allison1875.docanalyzer.handle.AnalyzeCustomValidationHandle;
import com.spldeolin.allison1875.docanalyzer.handle.AnalyzeEnumConstantHandle;
import com.spldeolin.allison1875.docanalyzer.handle.MoreJpdvAnalysisHandle;
import com.spldeolin.allison1875.docanalyzer.handle.ObtainConcernedResponseBodyHandle;
import com.spldeolin.allison1875.docanalyzer.handle.SpecificFieldDescriptionsHandle;
import com.spldeolin.allison1875.docanalyzer.handle.impl.DefaultAnalyzeCustomValidationHandle;
import com.spldeolin.allison1875.docanalyzer.handle.impl.DefaultObtainConcernedResponseBodyHandle;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.EnumAncestorHandle;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.EnumNameJpdvAnalysisHandle;
import com.spldeolin.beginningmind.allison1875.handle.docanalyzer.PageHelperDescriptionsHandle;

/**
 * @author Deolin 2020-12-06
 */
public class BmDocAnalyzerModule extends DocAnalyzerModule {

    @Override
    protected void configure() {
        bind(AnalyzeCustomValidationHandle.class).to(DefaultAnalyzeCustomValidationHandle.class);
        bind(AnalyzeEnumConstantHandle.class).to(EnumAncestorHandle.class);
        bind(MoreJpdvAnalysisHandle.class).to(EnumNameJpdvAnalysisHandle.class);
        bind(ObtainConcernedResponseBodyHandle.class).to(DefaultObtainConcernedResponseBodyHandle.class);
        bind(SpecificFieldDescriptionsHandle.class).to(PageHelperDescriptionsHandle.class);
    }

}
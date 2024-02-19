package com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer;

import org.apache.commons.lang3.ArrayUtils;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.javabean.AnalyzeEnumConstantRetval;
import com.spldeolin.allison1875.docanalyzer.service.EnumConstantAnalyzerService;
import com.spldeolin.beginningmind.ancestor.EnumAncestor;
import com.spldeolin.beginningmind.ancestor.javabean.CodeAndTitle;

/**
 * @author Deolin 2020-09-21
 */
@Singleton
public class EnumConstantAnalyzerServiceImpl2 implements EnumConstantAnalyzerService {

    @Override
    public boolean isSupport(Class<?> enumType) {
        return ArrayUtils.contains(enumType.getInterfaces(), EnumAncestor.class);
    }

    @Override
    public AnalyzeEnumConstantRetval analyzeEnumConstant(Object enumConstant) {
        CodeAndTitle codeAndTitle = ((EnumAncestor<?>) enumConstant).asJavabean();
        AnalyzeEnumConstantRetval result = new AnalyzeEnumConstantRetval();
        result.setCode(codeAndTitle.getCode().toString());
        result.setTitle(codeAndTitle.getTitle());
        return result;
    }

}
package com.spldeolin.beginningmind.allison1875.handle.docanalyzer;

import org.apache.commons.lang3.ArrayUtils;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.handle.AnalyzeEnumConstantHandle;
import com.spldeolin.allison1875.docanalyzer.javabean.EnumCodeAndTitleDto;
import com.spldeolin.beginningmind.ancestor.EnumAncestor;
import com.spldeolin.beginningmind.ancestor.javabean.CodeAndTitle;

/**
 * @author Deolin 2020-09-21
 */
@Singleton
public class EnumAncestorHandle extends AnalyzeEnumConstantHandle {

    @Override
    public boolean supportEnumType(Class<?> enumType) {
        return ArrayUtils.contains(enumType.getInterfaces(), EnumAncestor.class);
    }

    @Override
    public EnumCodeAndTitleDto analyzeEnumConstant(Object enumConstant) {
        CodeAndTitle codeAndTitle = ((EnumAncestor) enumConstant).asJavabean();
        EnumCodeAndTitleDto result = new EnumCodeAndTitleDto();
        result.setCode(codeAndTitle.getCode().toString());
        result.setTitle(codeAndTitle.getTitle());
        return result;
    }

}
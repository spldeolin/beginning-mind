package com.spldeolin.beginningmind.allison1875.handle.docanalyzer;

import java.lang.reflect.Field;
import com.google.common.base.CaseFormat;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.docanalyzer.handle.MoreJpdvAnalysisHandle;

/**
 * @author Deolin 2020-12-02
 */
@Singleton
public class EnumNameJpdvAnalysisHandle extends MoreJpdvAnalysisHandle {

    @Override
    public Object moreAnalysisFromField(Field field) {
        if (field.getType().isEnum()) {
            String enumName = field.getType().getSimpleName();
            if (enumName.endsWith("Enum")) {
                enumName = enumName.substring(0, enumName.length() - 4);
            }
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, enumName);
        }
        return null;
    }

    @Override
    public String moreJpdvToString(Object dto, DocAnalyzerConfig docAnalyzerConfig) {
        if (dto == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("枚举名\n");
        String appName = docAnalyzerConfig.getGlobalUrlPrefix().replace("/", "");
        sb.append("\t").append(appName).append(":").append(dto);
        return sb.toString();
    }

}
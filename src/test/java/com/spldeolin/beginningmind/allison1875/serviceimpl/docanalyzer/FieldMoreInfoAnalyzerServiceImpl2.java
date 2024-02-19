package com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import com.google.common.base.CaseFormat;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.docanalyzer.service.FieldMoreInfoAnalyzerService;

/**
 * @author Deolin 2020-12-02
 */
@Singleton
public class FieldMoreInfoAnalyzerServiceImpl2 implements FieldMoreInfoAnalyzerService {

    @Inject
    private DocAnalyzerConfig config;

    @Override
    public Object moreAnalyzerField(Field field) {
        Class<?> fieldType = field.getType();
        if (field.getType().isEnum()) {
            return getEnumName(fieldType);
        } else {
            // 仅考虑private List<XxxEnum> xxxes;
            // 不考虑List<List<XxxEnum>> xxxes; 和 List xxxes;
            Type genericType = field.getGenericType();
            if (isCollectionLike(fieldType) && genericType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    Type actualTypeArgument = actualTypeArguments[0];
                    if (actualTypeArgument instanceof Class) {
                        return getEnumName((Class<?>) actualTypeArgument);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String formatMoreInfo(Object dto) {
        if (dto == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("枚举名\n");
        String appName = config.getGlobalUrlPrefix().replace("/", "");
        sb.append("\t").append(appName).append(":").append(dto);
        return sb.toString();
    }

    private String getEnumName(Class<?> fieldType) {
        if (fieldType.isEnum()) {
            String enumName = fieldType.getSimpleName();
            if (enumName.endsWith("Enum")) {
                enumName = enumName.substring(0, enumName.length() - 4);
            }
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, enumName);
        }
        return null;
    }

    private boolean isCollectionLike(Type type) {
        if (type instanceof Class) {
            return Collection.class.isAssignableFrom((Class<?>) type);
        }
        return false;
    }

}
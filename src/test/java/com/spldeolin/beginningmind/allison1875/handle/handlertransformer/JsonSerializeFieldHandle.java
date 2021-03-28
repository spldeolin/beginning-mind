package com.spldeolin.beginningmind.allison1875.handle.handlertransformer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.handlertransformer.enums.JavabeanTypeEnum;
import com.spldeolin.allison1875.handlertransformer.handle.FieldHandle;
import com.spldeolin.allison1875.handlertransformer.handle.javabean.BeforeJavabeanCuBuildResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2021-01-29
 */
@Singleton
@Slf4j
public class JsonSerializeFieldHandle implements FieldHandle {

    private boolean anyEquals(Object one, Object... others) {
        for (Object other : others) {
            if (one == other) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BeforeJavabeanCuBuildResult beforeJavabeanCuBuild(FieldDeclaration field, JavabeanTypeEnum javabeanType) {
        BeforeJavabeanCuBuildResult result = new BeforeJavabeanCuBuildResult();
        result.setField(field);
        try {
            boolean isRespOrInResp = anyEquals(javabeanType, JavabeanTypeEnum.RESP_DTO,
                    JavabeanTypeEnum.NEST_DTO_IN_RESP);

            if (field.getCommonType().toString().equals("Long") && isRespOrInResp) {
                field.addAnnotation(
                        StaticJavaParser.parseAnnotation("@JsonSerialize(using = ToStringSerializer.class)"));
                result.getAppendImports().add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                result.getAppendImports().add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }
        } catch (Exception e) {
            log.warn("field.getCommonType()", e);
        }
        return result;
    }

}
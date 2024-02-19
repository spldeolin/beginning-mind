package com.spldeolin.beginningmind.allison1875.serviceimpl.docanalyzer;

import java.util.Optional;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.service.impl.ConcernedResponseBodyAnalyzerServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2020-11-30
 */
@Singleton
@Slf4j
public class ConcernedResponseBodyAnalyzerServiceImpl2 extends ConcernedResponseBodyAnalyzerServiceImpl {

    @Override
    public ResolvedType analyzeConcernedResponseBodyType(MethodDeclaration mvcHandlerMd) {
        Type type = mvcHandlerMd.getType();
        if (type.isClassOrInterfaceType()) {
            ClassOrInterfaceType coit = type.asClassOrInterfaceType();
            if (coit.getNameAsString().equals("RequestResult")) {
                Optional<NodeList<Type>> typeArguments = coit.getTypeArguments();
                if (typeArguments.isPresent()) {
                    NodeList<Type> types = typeArguments.get();
                    if (types.size() == 1) {
                        Type typeArgument = types.get(0);
                        if (typeArgument.isClassOrInterfaceType()) {
                            if (!typeArgument.toString().equals("Object") && !typeArgument.toString().equals("Void")) {
                                try {
                                    return typeArgument.resolve();
                                } catch (Exception e) {
                                    log.warn("fail to resolve, type={} returnType={}", typeArgument, type, e);
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.analyzeConcernedResponseBodyType(mvcHandlerMd);
    }

}
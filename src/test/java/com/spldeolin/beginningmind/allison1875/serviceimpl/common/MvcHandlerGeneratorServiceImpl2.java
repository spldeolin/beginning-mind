package com.spldeolin.beginningmind.allison1875.serviceimpl.common;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import com.spldeolin.allison1875.common.javabean.GenerateMvcHandlerArgs;
import com.spldeolin.allison1875.common.javabean.GenerateMvcHandlerRetval;
import com.spldeolin.allison1875.common.service.impl.MvcHandlerGeneratorServiceImpl;

/**
 * @author Deolin 2024-02-17
 */
public class MvcHandlerGeneratorServiceImpl2 extends MvcHandlerGeneratorServiceImpl {

    @Override
    public GenerateMvcHandlerRetval generateMvcHandler(GenerateMvcHandlerArgs args) {
        GenerateMvcHandlerRetval result = super.generateMvcHandler(args);
        Type returnType = result.getMvcHandler().getType();
        returnType.replace(StaticJavaParser.parseType(
                String.format("com.spldeolin.beginningmind.api.common.RequestResult<%s>", returnType)));
        Expression returnExpr = result.getMvcHandler().getBody().get().getStatements().get(0).asReturnStmt()
                .getExpression().get();
        returnExpr.replace(StaticJavaParser.parseExpression(String.format("RequestResult.success(%s)", returnExpr)));
        return result;
    }

}
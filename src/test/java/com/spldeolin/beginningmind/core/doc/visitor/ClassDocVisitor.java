package com.spldeolin.beginningmind.core.doc.visitor;

import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/25
 */
@Log4j2
public class ClassDocVisitor {

    public static MethodDoc getMethod(ClassDoc classDoc, Method method) {
        for (MethodDoc methodDoc : classDoc.methods(false)) {
            if (methodDoc.name().endsWith(method.getName())) {
                return methodDoc;
            }
        }
        throw new RuntimeException("类" + classDoc.qualifiedName() + " 中找不到方法" + method.getName());
    }

    public static String getComment(ClassDoc classDoc) {
        String result = classDoc.commentText();
        if (StringUtils.isBlank(result)) {
            log.warn("{} 未指定类注释", classDoc.qualifiedName());
            result = "开发者未指定";
        }
        return result;
    }

    public static List<String> listAuthors(ClassDoc classDoc) {
        List<String> result = Lists.newArrayList();
        Tag[] tags = classDoc.tags("author");
        for (Tag tag : tags) {
            result.add(tag.text());
        }
        if (result.size() == 0) {
            log.warn("{} 未指定@author注解", classDoc.qualifiedName());
        }
        return result;
    }

}

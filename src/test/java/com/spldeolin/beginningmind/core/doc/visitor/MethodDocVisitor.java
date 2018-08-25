package com.spldeolin.beginningmind.core.doc.visitor;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/25
 */
@Log4j2
public class MethodDocVisitor {

    public static String getComment(MethodDoc methodDoc) {
        String result = methodDoc.commentText();
        if (StringUtils.isBlank(result)) {
            log.warn("{} 未指定方法注释", methodDoc.qualifiedName());
            result = "开发者未指定";
        }
        return result;
    }

    public static List<String> listAuthors(MethodDoc methodDoc) {
        List<String> result = Lists.newArrayList();
        Tag[] tags = methodDoc.tags("author");
        for (Tag tag : tags) {
            result.add(tag.text());
        }
        return result;
    }

}

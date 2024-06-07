package com.spldeolin.beginningmind.service.allison1875.serviceimpl.persistencegenerator;

import java.util.regex.Pattern;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.persistencegenerator.javabean.InformationSchemaDto;
import com.spldeolin.allison1875.persistencegenerator.service.impl.CommentServiceImpl;

/**
 * @author Deolin 2021-05-24
 */
@Singleton
public class CommentServiceImpl2 extends CommentServiceImpl {

    @Override
    public String analyzeColumnComment(InformationSchemaDto infoSchema) {
        String comment = super.analyzeColumnComment(infoSchema);

        comment = Pattern.compile("T\\((.+?)\\)").matcher(comment).replaceFirst("").trim();
        comment = Pattern.compile("E\\((.+?)\\)").matcher(comment).replaceFirst("").trim();

        return comment;
    }

}
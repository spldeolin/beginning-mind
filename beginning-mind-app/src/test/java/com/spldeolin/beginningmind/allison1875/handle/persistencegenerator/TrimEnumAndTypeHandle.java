package com.spldeolin.beginningmind.allison1875.handle.persistencegenerator;

import java.util.regex.Pattern;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.persistencegenerator.handle.CommentHandle;
import com.spldeolin.allison1875.persistencegenerator.javabean.InformationSchemaDto;

/**
 * @author Deolin 2021-05-24
 */
@Singleton
public class TrimEnumAndTypeHandle extends CommentHandle {

    @Override
    public String resolveColumnComment(InformationSchemaDto infoSchema) {
        String comment = super.resolveColumnComment(infoSchema);

        comment = Pattern.compile("T\\((.+?)\\)").matcher(comment).replaceFirst("").trim();
        comment = Pattern.compile("E\\((.+?)\\)").matcher(comment).replaceFirst("").trim();

        return comment;
    }

}
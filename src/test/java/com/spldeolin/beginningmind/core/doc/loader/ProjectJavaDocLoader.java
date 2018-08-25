package com.spldeolin.beginningmind.core.doc.loader;

import static com.spldeolin.beginningmind.core.constant.Abbreviation.sep;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/20
 */
@Log4j2
public class ProjectJavaDocLoader extends Doclet {

    private static List<ClassDoc> classDocs = Lists.newArrayList();

    public static boolean start(RootDoc root) {
        Collections.addAll(classDocs, root.classes());
        return true;
    }

    public static List<ClassDoc> listClassDocsRecursively(String packageReference) {
        String packagePath = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "java" + sep +
                packageReference.replace('.', File.separatorChar);

        List<File> javaFiles = Lists.newArrayList(
                FileUtils.iterateFiles(new File(packagePath), new String[]{"java"}, true));

        String[] args = new String[javaFiles.size() + 2];
        args[0] = "-doclet";
        args[1] = ProjectJavaDocLoader.class.getName();
        for (int i = 0; i < javaFiles.size(); i++) {
            args[i + 2] = javaFiles.get(i).getPath();
        }
        com.sun.tools.javadoc.Main.execute(args);

        return classDocs;
    }

}

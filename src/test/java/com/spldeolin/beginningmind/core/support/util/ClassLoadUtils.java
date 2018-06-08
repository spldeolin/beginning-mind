package com.spldeolin.beginningmind.core.support.util;

import static com.spldeolin.beginningmind.core.constant.Abbreviation.sep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.controller.SignController;
import com.spldeolin.beginningmind.core.controller.TestController;
import com.spldeolin.beginningmind.core.controller.UrlForwardToExceptionController;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClassLoadUtils {

    private static final List<Class> EXCLUDED_CLASSES = Lists.newArrayList(UrlForwardToExceptionController.class,
            TestController.class, SignController.class);

    /**
     * jar中的文件路径分隔符
     */
    private static final char SEP = '/';

    /**
     * 包名分隔符
     */
    private static final char DOT = '.';

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param packagePath 包的路径
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> loadClassesInFile(String packagePath, String packageReference, boolean recursive) {
        Path path = Paths.get(packagePath);
        List<Class> result = loadClassesInFile(path, packageReference, recursive);
        result.removeAll(EXCLUDED_CLASSES);
        return result;
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param path 包的路径
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    private static List<Class> loadClassesInFile(Path path, String packageName, boolean recursive) {
        if (!Files.exists(path)) {
            return Collections.emptyList();
        }
        List<Class> classList = new ArrayList<>();
        if (Files.isDirectory(path)) {
            if (!recursive) {
                return Collections.emptyList();
            }
            try {
                //获取目录下的所有文件
                Stream<Path> stream = Files.list(path);
                Iterator<Path> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    classList.addAll(loadClassesInFile(iterator.next(), packageName, recursive));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                //由于传入的文件可能是相对路径, 这里要拿到文件的实际路径, 如果不存在则报IOException
                path = path.toRealPath();
                String pathStr = path.toString();
                //这里拿到的一般的"aa:\bb\...\cc.class"格式的文件名, 要去除末尾的类型后缀(.class)
                int lastDotIndex = pathStr.lastIndexOf(DOT);
                //Class.forName只允许使用用'.'分隔的类名的形式
                String className = pathStr.replace(sep, "\\.");
                //获取包名的起始位置
                int beginIndex = className.indexOf(packageName);
                if (beginIndex == -1) {
                    return Collections.emptyList();
                }
                className = lastDotIndex == -1 ? className.substring(beginIndex) : className.substring(beginIndex,
                        lastDotIndex);
                classList.add(Class.forName(className));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param filePath 包的路径
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> loadClassesInJar(String filePath, String packageReference, boolean recursive) {
        try {
            JarFile jar = new JarFile(filePath);
            List<Class> result = loadClassesInJar(jar, packageReference, recursive);
            result.removeAll(EXCLUDED_CLASSES);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param jar jar对象
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    private static List<Class> loadClassesInJar(JarFile jar, String packageReference, boolean recursive) {
        List<Class> classList = new ArrayList<>();
        //该迭代器会递归得到该jar底下所有的目录和文件
        Enumeration<JarEntry> iterator = jar.entries();
        while (iterator.hasMoreElements()) {
            //这里拿到的一般的"aa/bb/.../cc.class"格式的Entry或 "包路径"
            JarEntry jarEntry = iterator.nextElement();
            if (!jarEntry.isDirectory()) {
                String name = jarEntry.getName();
                //对于拿到的文件,要去除末尾的.class
                int lastDotClassIndex = name.lastIndexOf(".class");
                if (lastDotClassIndex != -1) {
                    int lastSlashIndex = name.lastIndexOf(SEP);
                    name = name.replace(SEP, DOT);
                    if (name.startsWith(packageReference)) {
                        if (recursive || packageReference.length() == lastSlashIndex) {
                            String className = name.substring(0, lastDotClassIndex);
                            log.info(className);
                            try {
                                classList.add(Class.forName(className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return classList;
    }

}

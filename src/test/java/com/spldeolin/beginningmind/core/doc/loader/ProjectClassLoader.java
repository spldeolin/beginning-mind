package com.spldeolin.beginningmind.core.doc.loader;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;

/**
 * 当前项目下类的加载器
 *
 * @author Deolin 2018/08/20
 */
@Log4j2
public class ProjectClassLoader {

    /**
     * list当前项目中，指定的包及其子包下的所有Class对象
     *
     * @param packageReference 用'.'分隔的包名
     * @return 该包名下的所有类
     */
    public static List<Class> listClassesRecursively(String packageReference) throws Exception {
        List<Class> result = Lists.newArrayList();
        //获取当前线程的类装载器中相应包名对应的资源
        Enumeration<URL> iterator = Thread.currentThread().getContextClassLoader()
                .getResources(dotToSep(packageReference));
        while (iterator.hasMoreElements()) {
            URL url = iterator.nextElement();
            String protocol = url.getProtocol();
            System.out.println(protocol);
            List<Class> classes = Lists.newArrayList();
            boolean recursive = true;
            switch (protocol) {
                case "file":
                    classes = getClassInFile(url, packageReference, recursive);
                    break;
                case "jar":
                    classes = getClassInJar(url, packageReference, recursive);
                    break;
                default:
                    //在某些WEB服务器中运行WAR包时，它不会像TOMCAT一样将WAR包解压为目录的，如JBOSS7，它是使用了一种叫VFS的协议
                    log.warn("unknown protocol " + protocol);
                    break;
            }
            result.addAll(classes);
        }
        return result;
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param packagePath 包的路径
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> getClassInFile(String packagePath, String packageReference, boolean recursive)
            throws IOException, ClassNotFoundException {
        Path path = Paths.get(packagePath);
        return getClassInFile(path, packageReference, recursive);
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param url 包的统一资源定位符
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    private static List<Class> getClassInFile(URL url, String packageReference, boolean recursive)
            throws URISyntaxException, IOException, ClassNotFoundException {
        Path path = Paths.get(url.toURI());
        return getClassInFile(path, packageReference, recursive);
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param path 包的路径
     * @param packageReference 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    private static List<Class> getClassInFile(Path path, String packageReference, boolean recursive)
            throws IOException, ClassNotFoundException {
        if (!Files.exists(path)) {
            return Collections.emptyList();
        }
        List<Class> classList = Lists.newArrayList();
        if (Files.isDirectory(path)) {
            if (!recursive) {
                return Collections.emptyList();
            }
            //获取目录下的所有文件
            Stream<Path> stream = Files.list(path);
            Iterator<Path> iterator = stream.iterator();
            while (iterator.hasNext()) {
                classList.addAll(getClassInFile(iterator.next(), packageReference, recursive));
            }
        } else {
            //由于传入的文件可能是相对路径, 这里要拿到文件的实际路径, 如果不存在则报IOException
            path = path.toRealPath();
            String pathStr = path.toString();
            //这里拿到的一般的"aa:\bb\...\cc.class"格式的文件名, 要去除末尾的类型后缀(.class)
            int lastDotIndex = pathStr.lastIndexOf('.');
            //Class.forName只允许使用用'.'分隔的类名的形式
            String className = sepToDot(pathStr);
            //获取包名的起始位置
            int beginIndex = className.indexOf(packageReference);
            if (beginIndex == -1) {
                return Collections.emptyList();
            }
            className = lastDotIndex == -1 ? className.substring(beginIndex)
                    : className.substring(beginIndex, lastDotIndex);
            classList.add(Class.forName(className));
        }
        return classList;
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param jarFilePath 包的路径
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> getClassInJar(String jarFilePath, String packageName, boolean recursive)
            throws ClassNotFoundException, IOException {
        JarFile jar = new JarFile(jarFilePath);
        return getClassInJar(jar, packageName, recursive);
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param url jar包的统一资源定位符
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> getClassInJar(URL url, String packageName, boolean recursive)
            throws IOException, ClassNotFoundException {
        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
        return getClassInJar(jar, packageName, recursive);
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param jar jar对象
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> getClassInJar(JarFile jar, String packageName, boolean recursive)
            throws ClassNotFoundException {
        List<Class> classList = new ArrayList<Class>();
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
                    int lastSlashIndex = name.lastIndexOf(File.separatorChar);
                    name = sepToDot(name);
                    if (name.startsWith(packageName)) {
                        if (recursive || packageName.length() == lastSlashIndex) {
                            String className = name.substring(0, lastDotClassIndex);
                            System.out.println(className);
                            classList.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        return classList;
    }

    private static String dotToSep(String string) {
        return string.replace('.', File.separatorChar);
    }

    private static String sepToDot(String string) {
        return string.replace(File.separatorChar, '.');
    }

}

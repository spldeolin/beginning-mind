package com.spldeolin.beginningmind.support;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.controller.TestController;
import com.spldeolin.beginningmind.controller.UrlForwardToExceptionController;
import com.spldeolin.beginningmind.controller.annotation.PermissionDisplayName;
import com.spldeolin.beginningmind.model.SecurityPermission;

public class Javas {

    private static final Class[] EXCLUDED_CLASSES = {UrlForwardToExceptionController.class, TestController.class};

    public static void main(String[] args) {
        String packageName = "com.spldeolin.beginningmind.controller";
        // 获取所有Class
        List<Class> classes = listClasses(packageName, true);
        // 过滤掉范围外的类和无效控制器（没有注解声明或内部没有请求方法）
        Iterator<Class> it = classes.iterator();
        while (it.hasNext()) {
            Class clazz = it.next();
            if (ArrayUtils.contains(EXCLUDED_CLASSES, clazz)) {
                it.remove();
                break;
            }
            boolean isController = false;
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof RestController || annotation instanceof Controller) {
                    isController = true;
                    break;
                }
            }
            if (!isController) {
                it.remove();
                break;
            }
            Iterator<Method> it2 = Lists.newArrayList(clazz.getMethods()).iterator();
            while (it2.hasNext()) {
                Method method = it2.next();
                boolean isRequestMethod = false;
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof RequestMapping || annotation instanceof GetMapping ||
                            annotation instanceof PostMapping || annotation instanceof PutMapping ||
                            annotation instanceof DeleteMapping) {
                        isRequestMethod = true;
                        break;
                    }
                }
                if (!isRequestMethod) {
                    it2.remove();
                }
            }
            // 没有请求方法
            if (Lists.newArrayList(it2).size() == 0) {
                it.remove();
            }
        }
        // 开始解析控制器
        for (Class controller : classes) {
            RequestMapping requestMapping = (RequestMapping) controller.getAnnotation(RequestMapping.class);
            String[] controllerMappings = requestMapping == null ? new String[] {""} : requestMapping.value();
            if (controllerMappings.length > 1) {
                throw new ServiceException("控制器" + controller.getName() + "有多个mapping，请手动指定它的权限");
            }
            String controllerMapping = checkMappingSepChar(controllerMappings[0]);

            for (Method requestMethod : controller.getMethods()) {
                PermissionDisplayName PermissionDisplayName = requestMethod.getAnnotation(PermissionDisplayName.class);
                String permName;
                if (PermissionDisplayName == null) {
                    //throw new ServiceException("请求方法" + requestMapping + "未声明@PermissionDisplayName");
                    permName = controllerMapping + getMapping(requestMethod);
                } else {
                    permName = PermissionDisplayName.value();
                }
                String permMapping = controllerMapping + getMapping(requestMethod);
                permMapping = permMapping.replace('/', ':').substring(1, permMapping.length());
                SecurityPermission securityPermission = SecurityPermission.builder().name(
                        permName).requiresPermissionsMapping(permMapping).build();
                System.out.println(securityPermission);
            }
        }

    }

    private static String getMapping(Method requestMethod) {
        String[] mappings = null;
        for (Annotation annotation : requestMethod.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                mappings = ((RequestMapping) annotation).value();
                break;
            }
            if (annotation instanceof GetMapping) {
                mappings = ((GetMapping) annotation).value();
                break;
            }
            if (annotation instanceof PostMapping) {
                mappings = ((PostMapping) annotation).value();
                break;
            }
            if (annotation instanceof PutMapping) {
                mappings = ((PutMapping) annotation).value();
                break;
            }
            if (annotation instanceof DeleteMapping) {
                mappings = ((DeleteMapping) annotation).value();
                break;
            }
        }
        String methodName = requestMethod.getName();
        if (mappings == null) {
            throw new ServiceException("方法" + methodName);
        }
        if (mappings.length == 0) {
            throw new RuntimeException(
                    "请求方法" + requestMethod.getDeclaringClass().getSimpleName() + "#" + methodName +
                            "的mapping为空，请手动添加它的权限");
        }
        if (mappings.length > 1) {
            throw new ServiceException("请求方法" + requestMethod.getDeclaringClass().getSimpleName() + "#" + methodName +
                    "有多个mapping，请手动添加它的权限");
        }
        return checkMappingSepChar(mappings[0]);
    }

    private static String checkMappingSepChar(String mapping) {
        if (mapping.length() > 0) {
            if (!mapping.startsWith("/")) {
                mapping = "/" + mapping;
            }
            if (mapping.endsWith("/")) {
                mapping = mapping.substring(0, mapping.length() - 1);
            }
        }
        return mapping;
    }

    /**
     * jar中的文件路径分隔符
     */
    private static final char SEP = '/';

    /**
     * 包名分隔符
     */
    private static final char DOT = '.';

    /**
     * 在当前项目中寻找指定包下的所有类
     *
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class> listClasses(String packageName, boolean recursive) {
        List<Class> classList = new ArrayList<>();
        try {
            //获取当前线程的类装载器中相应包名对应的资源
            Enumeration<URL> iterator = Thread.currentThread().getContextClassLoader().getResources(
                    packageName.replace(DOT, File.separatorChar));
            while (iterator.hasMoreElements()) {
                URL url = iterator.nextElement();
                String protocol = url.getProtocol();
                System.out.println(protocol);
                List<Class<?>> childClassList = Collections.emptyList();
                switch (protocol) {
                    case "file":
                        childClassList = getClassInFile(url, packageName, recursive);
                        break;
                    case "jar":
                        childClassList = getClassInJar(url, packageName, recursive);
                        break;
                    default:
                        //在某些WEB服务器中运行WAR包时，它不会像TOMCAT一样将WAR包解压为目录的，如JBOSS7，它是使用了一种叫VFS的协议
                        System.out.println("unknown protocol " + protocol);
                        break;
                }
                classList.addAll(childClassList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param filePath 包的路径
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInFile(String filePath, String packageName, boolean recursive) {
        Path path = Paths.get(filePath);
        return getClassInFile(path, packageName, recursive);
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param url 包的统一资源定位符
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInFile(URL url, String packageName, boolean recursive) {
        try {
            Path path = Paths.get(url.toURI());
            return getClassInFile(path, packageName, recursive);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 在给定的文件或文件夹中寻找指定包下的所有类
     *
     * @param path 包的路径
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInFile(Path path, String packageName, boolean recursive) {
        if (!Files.exists(path)) {
            return Collections.emptyList();
        }
        List<Class<?>> classList = new ArrayList<Class<?>>();
        if (Files.isDirectory(path)) {
            if (!recursive) {
                return Collections.emptyList();
            }
            try {
                //获取目录下的所有文件
                Stream<Path> stream = Files.list(path);
                Iterator<Path> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    classList.addAll(getClassInFile(iterator.next(), packageName, recursive));
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
                String className = pathStr.replace(File.separatorChar, DOT);
                //获取包名的起始位置
                int beginIndex = className.indexOf(packageName);
                if (beginIndex == -1) {
                    return Collections.emptyList();
                }
                className = lastDotIndex == -1 ? className.substring(beginIndex) : className.substring(beginIndex,
                        lastDotIndex);
                classList.add(Class.forName(className));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param filePath 包的路径
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInJar(String filePath, String packageName, boolean recursive) {
        try {
            JarFile jar = new JarFile(filePath);
            return getClassInJar(jar, packageName, recursive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param url jar包的统一资源定位符
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInJar(URL url, String packageName, boolean recursive) {
        try {
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
            return getClassInJar(jar, packageName, recursive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 在给定的jar包中寻找指定包下的所有类
     *
     * @param jar jar对象
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public static List<Class<?>> getClassInJar(JarFile jar, String packageName, boolean recursive) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
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
                    if (name.startsWith(packageName)) {
                        if (recursive || packageName.length() == lastSlashIndex) {
                            String className = name.substring(0, lastDotClassIndex);
                            System.out.println(className);
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

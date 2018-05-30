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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.controller.SignController;
import com.spldeolin.beginningmind.controller.TestController;
import com.spldeolin.beginningmind.controller.UrlForwardToExceptionController;
import com.spldeolin.beginningmind.controller.annotation.Permission;
import com.spldeolin.beginningmind.model.SecurityPermission;
import com.spldeolin.beginningmind.model.SecurityRoles2permissions;
import com.spldeolin.beginningmind.service.SecurityPermissionService;
import com.spldeolin.beginningmind.service.SecurityRoles2permissionsService;
import com.spldeolin.beginningmind.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class PermissionsInserter {

    private final Class[] EXCLUDED_CLASS = {UrlForwardToExceptionController.class, TestController.class, SignController.class};

    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Autowired
    private SecurityRoles2permissionsService securityRoles2permissionsService;

    @Test
    public void insert() {
        String packageName = "com.spldeolin.beginningmind.controller";
        // 获取所有Class
        List<Class> classes = listClasses(packageName, true);
        // 找出所有符合要求的控制器与请求方法
        List<ControllerDefinition> controllerDefinitions = new ArrayList<>();
        classes.removeAll(Lists.newArrayList(EXCLUDED_CLASS));
        for (Class clazz : classes) {
            if (isContoller(clazz)) {
                List<Method> requestMethods = new ArrayList<>();
                for (Method method : clazz.getDeclaredMethods()) {
                    if (isRequestMethod(method)) {
                        requestMethods.add(method);
                    }
                }
                if (requestMethods.size() > 0) {
                    ControllerDefinition controllerDefinition = new ControllerDefinition();
                    controllerDefinition.setController(clazz);
                    controllerDefinition.setRequestMethods(requestMethods);
                    controllerDefinitions.add(controllerDefinition);
                }
            }
        }
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        // 解析控制器
        for (ControllerDefinition controllerDefinition : controllerDefinitions) {
            Class controller = controllerDefinition.getController();
            RequestMapping requestMapping = (RequestMapping) controller.getAnnotation(RequestMapping.class);
            String[] controllerMappings = requestMapping == null ? new String[] {""} : requestMapping.value();
            if (controllerMappings.length > 1) {
                throw new ServiceException("控制器" + controller.getName() + "有多个mapping，请手动指定它的权限");
            }
            String controllerMapping = checkMappingSepChar(controllerMappings[0]);

            for (Method requestMethod : controllerDefinition.getRequestMethods()) {
                String permissionMapping = controllerMapping + getMapping(requestMethod);
                permissionMapping = permissionMapping.replaceAll("\\{.*}", "*");
                Permission Permission = requestMethod.getAnnotation(Permission.class);
                String displayName;
                if (Permission == null) {
                    //throw new ServiceException("请求方法" + requestMapping + "未声明@Permission");
                    log.warn("请求方法" + requestMapping + "未声明@Permission，使用缺省方式命名");
                    displayName = permissionMapping.replace('/', ':').substring(1, permissionMapping.length());
                } else {
                    displayName = Permission.displayName();
                }
                String mark = generateUniqueMark();
                SecurityPermission securityPermission = SecurityPermission.builder().displayName(
                        displayName).mapping(permissionMapping).mark(mark).build();
                securityPermissions.add(securityPermission);
                log.info(securityPermission);
            }
        }
        // 删除接口已经不存在的多余权限
        List<String> mappings = securityPermissions.stream().map(SecurityPermission::getMapping).collect(
                Collectors.toList());
        for (SecurityPermission existPermission : securityPermissionService.listAll()) {
            if (!mappings.contains(existPermission.getMapping())) {
                log.info("权限[" + existPermission.getMapping() + "] 对应接口已不存在，删除这个权限");
                Long existPermissionId = existPermission.getId();
                securityPermissionService.delete(existPermissionId);
                // 删除关联关系
                securityRoles2permissionsService.delete(securityRoles2permissionsService.searchBatch("permissionId",
                        existPermissionId).stream().map(SecurityRoles2permissions::getId).collect(Collectors.toList()));
            }
        }
        // 插入`security_permission`
        for (SecurityPermission securityPermission : securityPermissions) {
            String mapping = securityPermission.getMapping();
            if (securityPermissionService.searchOne("mapping", mapping).isPresent()) {
                log.warn("[" + mapping + "] 已存在，不再插入");
            } else {
                securityPermissionService.create(securityPermission);
                log.info(securityPermission.getDisplayName() + "[" + mapping + "] 插入数据库");
            }
        }
    }

    private boolean isContoller(Class clazz) {
        boolean isController = false;
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof RestController || annotation instanceof Controller) {
                isController = true;
                break;
            }
        }
        return isController;
    }

    private boolean isRequestMethod(Method method) {
        boolean isRequestMethod = false;
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping || annotation instanceof GetMapping ||
                    annotation instanceof PostMapping || annotation instanceof PutMapping ||
                    annotation instanceof DeleteMapping) {
                isRequestMethod = true;
                break;
            }
        }
        return isRequestMethod;
    }

    private String getMapping(Method requestMethod) {
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

    private String checkMappingSepChar(String mapping) {
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

    private String generateUniqueMark() {
        String mark = StringRandomUtils.generateLowEnNum(3);
        while (securityPermissionService.searchOne("mark", mark).isPresent()) {
            mark = StringRandomUtils.generateLowEnNum(3);
        }
        return mark;
    }

    /**
     * jar中的文件路径分隔符
     */
    private final char SEP = '/';

    /**
     * 包名分隔符
     */
    private final char DOT = '.';

    /**
     * 在当前项目中寻找指定包下的所有类
     *
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public List<Class> listClasses(String packageName, boolean recursive) {
        List<Class> classList = new ArrayList<>();
        try {
            //获取当前线程的类装载器中相应包名对应的资源
            Enumeration<URL> iterator = Thread.currentThread().getContextClassLoader().getResources(
                    packageName.replace(DOT, File.separatorChar));
            while (iterator.hasMoreElements()) {
                URL url = iterator.nextElement();
                String protocol = url.getProtocol();
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
                        log.info("unknown protocol " + protocol);
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
    public List<Class<?>> getClassInFile(String filePath, String packageName, boolean recursive) {
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
    public List<Class<?>> getClassInFile(URL url, String packageName, boolean recursive) {
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
    public List<Class<?>> getClassInFile(Path path, String packageName, boolean recursive) {
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
     * @param packageName 用'.'分隔的包名
     * @param recursive 是否递归搜索
     * @return 该包名下的所有类
     */
    public List<Class<?>> getClassInJar(String filePath, String packageName, boolean recursive) {
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
    public List<Class<?>> getClassInJar(URL url, String packageName, boolean recursive) {
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
    public List<Class<?>> getClassInJar(JarFile jar, String packageName, boolean recursive) {
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

package com.spldeolin.beginningmind.security;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.spldeolin.beginningmind.entity.PermissionEntity;
import com.spldeolin.beginningmind.mapper.PermissionMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
 * 当前权限、寄存权限同步
 *
 * @author Deolin 2018/12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
public class PermissionSync {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void sync() {
        // 通过Spring组件获取的当前权限
        Set<PermissionEntity> actualPermissions = Sets.newHashSet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            String mappingPath = getFirstPath(mappingInfo);
            if (mappingPath.startsWith("/error")) {
                continue;
            }
            String mappingMethod = getFirstMethod(mappingInfo);
            PermissionEntity permission = new PermissionEntity(mappingMethod, mappingPath);
            actualPermissions.add(permission);
        }

        // 在数据库中保存的既存权限
        Set<PermissionEntity> persistentPermissions = Sets.newHashSet(permissionMapper.selectList(null));

        // 待删除权限、待创建权限
        List<Long> toDeleteIds = Lists.newArrayList();
        List<PermissionEntity> toCreatePermissions = Lists.newArrayList();

        // 当前权限与既存权限的差集
        Set<PermissionEntity> differences = Sets.symmetricDifference(actualPermissions, persistentPermissions);
        if (differences.size() == 0) {
            log.info("当前权限与寄存权限 已一致");
        }
        for (PermissionEntity difference : differences) {
            Long id = difference.getId();
            if (null != id) {
                log.info("删除 {} {}", difference.getMappingMethod(), difference.getMappingPath());
                toDeleteIds.add(id);
            } else {
                log.info("创建 {} {}", difference.getMappingMethod(), difference.getMappingPath());
                difference.setDisplay("未命名");
                difference.setIsForbidden(false);
                difference.setIsGrantedForAll(false);
                toCreatePermissions.add(difference);
            }
        }

        // 删除、插入操作
        if (toDeleteIds.size() > 0) {
            permissionMapper.deleteBatchIds(toDeleteIds);
        }
        if (toCreatePermissions.size() > 0) {
            toCreatePermissions.forEach(permissionMapper::insert);
        }
    }

    private String getFirstPath(RequestMappingInfo mappingInfo) {
        return Lists.newArrayList(mappingInfo.getPatternsCondition().getPatterns()).get(0);
    }

    private String getFirstMethod(RequestMappingInfo mappingInfo) {
        return Lists.newArrayList(mappingInfo.getMethodsCondition().getMethods()).get(0).name();
    }

}
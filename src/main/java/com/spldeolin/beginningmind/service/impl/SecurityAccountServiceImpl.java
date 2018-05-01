package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.SecurityAccountMapper;
import com.spldeolin.beginningmind.model.SecurityAccount;
import com.spldeolin.beginningmind.model.SecurityAccounts2roles;
import com.spldeolin.beginningmind.model.SecurityPermission;
import com.spldeolin.beginningmind.model.SecurityRoles2permissions;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.service.SecurityAccounts2rolesService;
import com.spldeolin.beginningmind.service.SecurityPermissionService;
import com.spldeolin.beginningmind.service.SecurityRoles2permissionsService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “帐号（用于登录的信息）”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SecurityAccountServiceImpl extends CommonServiceImpl<SecurityAccount> implements SecurityAccountService {

    @Autowired
    private SecurityAccountMapper securityAccountMapper;

    @Autowired
    private SecurityAccounts2rolesService accounts2rolesService;

    @Autowired
    private SecurityRoles2permissionsService roles2permissionsService;

    @Autowired
    private SecurityPermissionService permissionService;

    @Override
    public Long createEX(SecurityAccount securityAccount) {
        /* 业务校验 */
        super.create(securityAccount);
        return securityAccount.getId();
    }

    @Override
    public void updateEX(SecurityAccount securityAccount) {
        if (!isExist(securityAccount.getId())) {
            throw new ServiceException("帐号（用于登录的信息）不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityAccount)) {
            throw new ServiceException("帐号（用于登录的信息）数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("帐号（用于登录的信息）不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityAccount> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的帐号（用于登录的信息）全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityAccount> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(SecurityAccount.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(securityAccountMapper.selectBatchByCondition(condition));
    }

    @Override
    public List<String> listAccountPermissionMappings(Long accountId) {
        List<String> result = Lists.newArrayList();
        // account
        SecurityAccount account = this.get(accountId).orElseThrow(() -> new ServiceException("帐号不存在或是已被删除"));
        // account 2 role
        List<SecurityAccounts2roles> roleAssociations = accounts2rolesService.searchBatch("accountId", account.getId());
        if (roleAssociations.size() == 0) {
            return result;
        }
        // role 2 perm
        List<Long> roleIds = Lists.newArrayList();
        for (SecurityAccounts2roles roleAssociation : roleAssociations) {
            roleIds.add(roleAssociation.getRoleId());
        }
        Condition condition = new Condition(SecurityRoles2permissions.class);
        condition.createCriteria().andIn("roleId", roleIds);
        List<SecurityRoles2permissions> permissionAssociations = roles2permissionsService.searchBatch(condition);
        if (permissionAssociations.size() == 0) {
            return result;
        }
        // perm
        List<Long> permissionIds = Lists.newArrayList();
        for (SecurityRoles2permissions permissionAssociation : permissionAssociations) {
            permissionIds.add(permissionAssociation.getPermissionId());
        }
        List<SecurityPermission> permissions = permissionService.get(permissionIds);
        if (permissions.size() == 0) {
            return result;
        }
        // perm mapping
        for (SecurityPermission permission : permissions) {
            result.add(permission.getRequiresPermissionsMapping());
        }
        return result;
    }

}
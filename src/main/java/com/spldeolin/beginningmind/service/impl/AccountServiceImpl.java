package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.AccountMapper;
import com.spldeolin.beginningmind.model.Account;
import com.spldeolin.beginningmind.model.Accounts2roles;
import com.spldeolin.beginningmind.model.Permission;
import com.spldeolin.beginningmind.model.Roles2permissions;
import com.spldeolin.beginningmind.service.AccountService;
import com.spldeolin.beginningmind.service.Accounts2rolesService;
import com.spldeolin.beginningmind.service.PermissionService;
import com.spldeolin.beginningmind.service.Roles2permissionsService;
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
public class AccountServiceImpl extends CommonServiceImpl<Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private Accounts2rolesService accounts2rolesService;

    @Autowired
    private Roles2permissionsService roles2permissionsService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Long createEX(Account account) {
        /* 业务校验 */
        super.create(account);
        return account.getId();
    }

    @Override
    public void updateEX(Account account) {
        if (!isExist(account.getId())) {
            throw new ServiceException("帐号（用于登录的信息）不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(account)) {
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
        List<Account> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的帐号（用于登录的信息）全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<Account> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(Account.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(accountMapper.selectBatchByCondition(condition));
    }

    @Override
    public List<String> listAccountPermissionMappings(Long accountId) {
        List<String> result = Lists.newArrayList();
        // account
        Account account = this.get(accountId).orElseThrow(() -> new ServiceException("帐号不存在或是已被删除"));
        // account 2 role
        List<Accounts2roles> roleAssociations = accounts2rolesService.searchBatch("accountId", account.getId());
        if (roleAssociations.size() == 0) {
            return result;
        }
        // role 2 perm
        List<Long> roleIds = Lists.newArrayList();
        for (Accounts2roles roleAssociation : roleAssociations) {
            roleIds.add(roleAssociation.getRoleId());
        }
        Condition condition = new Condition(Roles2permissionsService.class);
        condition.createCriteria().andIn("roleId", roleIds);
        List<Roles2permissions> permissionAssociations = roles2permissionsService.searchBatch(condition);
        if (permissionAssociations.size() == 0) {
            return result;
        }
        // perm
        List<Long> permissionIds = Lists.newArrayList();
        for (Roles2permissions permissionAssociation : permissionAssociations) {
            permissionIds.add(permissionAssociation.getPermissionId());
        }
        List<Permission> permissions = permissionService.get(permissionIds);
        if (permissions.size() == 0) {
            return result;
        }
        // perm mapping
        for (Permission permission : permissions) {
            result.add(permission.getRequiresPermissionsMapping());
        }
        return result;
    }

}
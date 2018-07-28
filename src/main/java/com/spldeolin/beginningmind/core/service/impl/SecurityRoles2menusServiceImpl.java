/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.dao.SecurityRoles2menusMapper;
import com.spldeolin.beginningmind.core.model.SecurityRoles2menus;
import com.spldeolin.beginningmind.core.service.SecurityRoles2menusService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * “角色与菜单的关联”业务实现
 *
 * @author Deolin 2018/7/10
 */
@Service
@Log4j2
public class SecurityRoles2menusServiceImpl extends CommonServiceImpl<SecurityRoles2menus> implements
        SecurityRoles2menusService {

    @Autowired
    private SecurityRoles2menusMapper securityRoles2menusMapper;

    @Override
    public Long createEX(SecurityRoles2menus securityRoles2menus) {
        /* 业务校验 */
        super.create(securityRoles2menus);
        return securityRoles2menus.getId();
    }

    @Override
    public SecurityRoles2menus getEX(Long id) {
        return super.get(id).orElseThrow(() -> new ServiceException("角色与菜单的关联不存在或是已被删除"));
    }

    @Override
    public void updateEX(SecurityRoles2menus securityRoles2menus) {
        if (!isExist(securityRoles2menus.getId())) {
            throw new ServiceException("角色与菜单的关联不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityRoles2menus)) {
            throw new ServiceException("角色与菜单的关联数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("角色与菜单的关联不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityRoles2menus> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的角色与菜单的关联全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityRoles2menus> page(PageParam pageParam) {
        Condition condition = new Condition(SecurityRoles2menus.class);
        condition.createCriteria()/* 添加条件 */;
        pageParam.startPage();
        return Page.wrap(securityRoles2menusMapper.selectBatchByCondition(condition));
    }

}
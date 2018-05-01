package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.dao.RoleMapper;
import com.spldeolin.beginningmind.model.Role;
import com.spldeolin.beginningmind.service.RoleService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.api.dto.Page;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * “角色”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class RoleServiceImpl extends CommonServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Long createEX(Role role) {
        /* 业务校验 */
        super.create(role);
        return role.getId();
    }

    @Override
    public void updateEX(Role role) {
        if (!isExist(role.getId())) {
            throw new ServiceException("角色不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(role)) {
            throw new ServiceException("角色数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("角色不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<Role> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的角色全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<Role> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(Role.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(roleMapper.selectBatchByCondition(condition));
    }

}
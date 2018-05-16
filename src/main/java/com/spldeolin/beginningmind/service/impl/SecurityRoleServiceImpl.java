package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.bm1.SecurityRoleMapper;
import com.spldeolin.beginningmind.model.SecurityRole;
import com.spldeolin.beginningmind.service.SecurityRoleService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “角色”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SecurityRoleServiceImpl extends CommonServiceImpl<SecurityRole> implements SecurityRoleService {

    @Autowired
    private SecurityRoleMapper securityRoleMapper;

    @Override
    public Long createEX(SecurityRole securityRole) {
        /* 业务校验 */
        super.create(securityRole);
        return securityRole.getId();
    }

    @Override
    public void updateEX(SecurityRole securityRole) {
        if (!isExist(securityRole.getId())) {
            throw new ServiceException("角色不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityRole)) {
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
        List<SecurityRole> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的角色全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityRole> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(SecurityRole.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(securityRoleMapper.selectBatchByCondition(condition));
    }

}
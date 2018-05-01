package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.SecurityRoles2permissionsMapper;
import com.spldeolin.beginningmind.model.SecurityRoles2permissions;
import com.spldeolin.beginningmind.service.SecurityRoles2permissionsService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “角色与权限的关联”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SecurityRoles2permissionsServiceImpl extends CommonServiceImpl<SecurityRoles2permissions> implements
        SecurityRoles2permissionsService {

    @Autowired
    private SecurityRoles2permissionsMapper securityRoles2permissionsMapper;

    @Override
    public Long createEX(SecurityRoles2permissions securityRoles2permissions) {
        /* 业务校验 */
        super.create(securityRoles2permissions);
        return securityRoles2permissions.getId();
    }

    @Override
    public void updateEX(SecurityRoles2permissions securityRoles2permissions) {
        if (!isExist(securityRoles2permissions.getId())) {
            throw new ServiceException("角色与权限的关联不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityRoles2permissions)) {
            throw new ServiceException("角色与权限的关联数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("角色与权限的关联不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityRoles2permissions> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的角色与权限的关联全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityRoles2permissions> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(SecurityRoles2permissions.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(securityRoles2permissionsMapper.selectBatchByCondition(condition));
    }

}
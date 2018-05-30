package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.dao.SecurityPermissionMapper;
import com.spldeolin.beginningmind.core.model.SecurityPermission;
import com.spldeolin.beginningmind.core.service.SecurityPermissionService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “权限”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SecurityPermissionServiceImpl extends CommonServiceImpl<SecurityPermission> implements
        SecurityPermissionService {

    @Autowired
    private SecurityPermissionMapper securityPermissionMapper;

    @Override
    public Long createEX(SecurityPermission securityPermission) {
        /* 业务校验 */
        super.create(securityPermission);
        return securityPermission.getId();
    }

    @Override
    public void updateEX(SecurityPermission securityPermission) {
        if (!isExist(securityPermission.getId())) {
            throw new ServiceException("权限不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityPermission)) {
            throw new ServiceException("权限数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("权限不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityPermission> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的权限全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityPermission> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(SecurityPermission.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(securityPermissionMapper.selectBatchByCondition(condition));
    }

}
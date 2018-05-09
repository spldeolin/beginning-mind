package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.bm1.SecurityAccounts2rolesMapper;
import com.spldeolin.beginningmind.model.SecurityAccounts2roles;
import com.spldeolin.beginningmind.service.SecurityAccounts2rolesService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “帐号与权限的关联”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SecurityAccounts2rolesServiceImpl extends CommonServiceImpl<SecurityAccounts2roles> implements
        SecurityAccounts2rolesService {

    @Autowired
    private SecurityAccounts2rolesMapper securityAccounts2rolesMapper;

    @Override
    public Long createEX(SecurityAccounts2roles securityAccounts2roles) {
        /* 业务校验 */
        super.create(securityAccounts2roles);
        return securityAccounts2roles.getId();
    }

    @Override
    public void updateEX(SecurityAccounts2roles securityAccounts2roles) {
        if (!isExist(securityAccounts2roles.getId())) {
            throw new ServiceException("帐号与权限的关联不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityAccounts2roles)) {
            throw new ServiceException("帐号与权限的关联数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("帐号与权限的关联不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityAccounts2roles> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的帐号与权限的关联全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityAccounts2roles> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(SecurityAccounts2roles.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(securityAccounts2rolesMapper.selectBatchByCondition(condition));
    }

}
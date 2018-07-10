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
import com.spldeolin.beginningmind.core.dao.SecurityMenuMapper;
import com.spldeolin.beginningmind.core.model.SecurityMenu;
import com.spldeolin.beginningmind.core.service.SecurityMenuService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * “菜单”业务实现
 *
 * @author Deolin 2018/7/10
 */
@Service
@Log4j2
public class SecurityMenuServiceImpl extends CommonServiceImpl<SecurityMenu> implements SecurityMenuService {

    @Autowired
    private SecurityMenuMapper securityMenuMapper;

    @Override
    public Long createEX(SecurityMenu securityMenu) {
        /* 业务校验 */
        super.create(securityMenu);
        return securityMenu.getId();
    }

    @Override
    public SecurityMenu getEX(Long id) {
        return super.get(id).orElseThrow(() -> new ServiceException("菜单不存在或是已被删除"));
    }

    @Override
    public void updateEX(SecurityMenu securityMenu) {
        if (!isExist(securityMenu.getId())) {
            throw new ServiceException("菜单不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(securityMenu)) {
            throw new ServiceException("菜单数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("菜单不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<SecurityMenu> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的菜单全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<SecurityMenu> page(PageParam pageParam) {
        Condition condition = new Condition(SecurityMenu.class);
        condition.createCriteria()/* 添加条件 */;
        pageParam.startPage();
        return Page.wrap(securityMenuMapper.selectBatchByCondition(condition));
    }

}
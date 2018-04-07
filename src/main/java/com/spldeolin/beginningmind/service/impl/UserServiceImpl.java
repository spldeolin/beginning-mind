package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.dao.UserMapper;
import com.spldeolin.beginningmind.model.User;
import com.spldeolin.beginningmind.service.UserService;
import com.spldeolin.cadeau.library.dto.Page;
import com.spldeolin.cadeau.library.exception.ServiceException;
import com.spldeolin.cadeau.library.inherited.CommonServiceImpl;
import com.spldeolin.cadeau.library.util.FieldExtractUtil;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * “用户”业务实现
 *
 * @author Deolin 2018/4/7
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createEX(User user) {
        /* 业务校验 */
        return super.create(user);
    }

    @Override
    public void updateEX(User user) {
        if (!isExist(user.getId())) {
            throw new ServiceException("用户不存在或是已被删除");
        }
        /* 业务校验 */
        super.update(user);
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("用户不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<User> exist = get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的用户全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(FieldExtractUtil.extractId(exist));
        return "操作成功";
    }

    @Override
    public Page<User> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(User.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIsNull("deletedAt");
        /* 其他条件 */
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(userMapper.selectByCondition(condition));
    }

}
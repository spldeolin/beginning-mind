package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.model.User;
import com.spldeolin.cadeau.library.dto.Page;
import com.spldeolin.cadeau.library.inherited.CommonService;

/**
 * “用户”业务
 *
 * @author Deolin 2018/4/7
 * @generator Cadeau Support
 */
public interface UserService extends CommonService<User> {

    /**
     * 创建一个“用户”
     * （附带业务校验）
     *
     * @param user 待创建“用户”
     * @return 自增ID
     */
    Long createEX(User user);

    /**
     * 更新一个“用户”
     * （附带业务校验）
     *
     * @param user 待更新“用户”
     */
    void updateEX(User user);

    /**
     * 删除一个“用户”
     *
     * @param id 待删除“用户”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源
     * （附带业务校验，并返回详细情况）
     *
     * @param ids 待删除资源的ID列表
     * @return 删除情况
     */
    String deleteEX(List<Long> ids);

    /**
     * 分页获取资源
     *
     * @param pageNo 页码
     * @param pageSize 分页尺寸
     * @return Page 分页对象
     */
    Page<User> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}
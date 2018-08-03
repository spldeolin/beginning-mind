/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.model.SecurityMenu;

/**
 * “菜单”业务
 *
 * @author Deolin 2018/7/10
 */
public interface SecurityMenuService extends CommonService<SecurityMenu> {

    /**
     * 创建一个“菜单” （附带业务校验）
     *
     * @param securityMenu 待创建“菜单”
     * @return 自增ID
     */
    Long createEX(SecurityMenu securityMenu);

    /**
     * 获取一个“菜单” （附带业务校验）
     *
     * @param id 待获取“菜单”的ID
     * @return “菜单”
     */
    SecurityMenu getEX(Long id);

    /**
     * 更新一个“菜单” （附带业务校验）
     *
     * @param securityMenu 待更新“菜单”
     */
    void updateEX(SecurityMenu securityMenu);

    /**
     * 删除一个“菜单” （附带业务校验）
     *
     * @param id 待删除“菜单”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源 （附带业务校验，并返回详细情况）
     *
     * @param ids 待删除资源的ID列表
     * @return 删除情况
     */
    String deleteEX(List<Long> ids);

    /**
     * 分页获取资源
     *
     * @param pageParam 页码和每页条目数
     * @return Page 分页对象
     */
    Page<SecurityMenu> page(PageParam pageParam); // 根据具体需求拓展这个方法（追加搜索用参数等）

}
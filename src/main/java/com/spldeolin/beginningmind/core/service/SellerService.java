package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.Seller;

/**
 * “卖家”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface SellerService extends CommonService<Seller> {

    /**
     * 创建一个“卖家”
     * （附带业务校验）
     *
     * @param seller 待创建“卖家”
     * @return 自增ID
     */
    Long createEX(Seller seller);

    /**
     * 更新一个“卖家”
     * （附带业务校验）
     *
     * @param seller 待更新“卖家”
     */
    void updateEX(Seller seller);

    /**
     * 删除一个“卖家”
     *
     * @param id 待删除“卖家”的ID
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
    Page<Seller> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}
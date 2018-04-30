package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.api.CommonService;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.model.Buyer;

/**
 * “买家”业务
 *
 * @author Deolin 2018/4/30
 * @generator Cadeau Support
 */
public interface BuyerService extends CommonService<Buyer> {

    /**
     * 创建一个“买家”
     * （附带业务校验）
     *
     * @param buyer 待创建“买家”
     * @return 自增ID
     */
    Long createEX(Buyer buyer);

    /**
     * 更新一个“买家”
     * （附带业务校验）
     *
     * @param buyer 待更新“买家”
     */
    void updateEX(Buyer buyer);

    /**
     * 删除一个“买家”
     *
     * @param id 待删除“买家”的ID
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
    Page<Buyer> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}
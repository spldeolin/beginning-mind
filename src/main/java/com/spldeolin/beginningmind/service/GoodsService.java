package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.model.Goods;
import com.spldeolin.beginningmind.api.CommonService;
import com.spldeolin.beginningmind.api.dto.Page;

/**
 * “商品”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface GoodsService extends CommonService<Goods> {

    /**
     * 创建一个“商品”
     * （附带业务校验）
     *
     * @param goods 待创建“商品”
     * @return 自增ID
     */
    Long createEX(Goods goods);

    /**
     * 更新一个“商品”
     * （附带业务校验）
     *
     * @param goods 待更新“商品”
     */
    void updateEX(Goods goods);

    /**
     * 删除一个“商品”
     * 
     *
     * @param id 待删除“商品”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源
     （附带业务校验，并返回详细情况）
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
    Page<Goods> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

	// 其他方法声明

}
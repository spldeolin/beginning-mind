package com.spldeolin.beginningmind.service.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.service.entity.OrderdetailsEntity;
import com.spldeolin.beginningmind.service.javabean.record.QueryOrderdetailsRecord;

/**
 * <p>orderdetails
 *
 * @author Deolin 2024-04-05
 */
public interface OrderdetailsMapper {

    /**
     * 插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insert(OrderdetailsEntity entity);

    /**
     * 批量插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsert(@Param("entities") List<OrderdetailsEntity> entities);

    /**
     * 批量插入，为null的属性会被作为null插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsertEvenNull(@Param("entities") List<OrderdetailsEntity> entities);

    /**
     * 批量根据ID更新数据
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdate(@Param("entities") List<OrderdetailsEntity> entities);

    /**
     * 批量根据ID更新数据，为null对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdateEvenNull(@Param("entities") List<OrderdetailsEntity> entities);

    /**
     * 根据ID查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    OrderdetailsEntity queryById(@Param("orderNumber") Integer orderNumber, @Param("courseCode") String courseCode);

    /**
     * 根据ID更新数据，忽略值为null的属性
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateById(OrderdetailsEntity entity);

    /**
     * 根据ID更新数据，为null属性对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateByIdEvenNull(OrderdetailsEntity entity);

    /**
     * 根据实体内的属性查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<OrderdetailsEntity> queryByEntity(OrderdetailsEntity entity);

    /**
     * 获取全部
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<OrderdetailsEntity> listAll();

    /**
     * 尝试插入，若指定了id并存在，则更新，即INSERT ON DUPLICATE KEY UPDATE
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insertOrUpdate(OrderdetailsEntity entity);

    /**
     * QT1001S-6EE40EE2
     */
    List<QueryOrderdetailsRecord> queryOrderdetails(@Param("orderNumber") List<Integer> orderNumber);
}

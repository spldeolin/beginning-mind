package com.spldeolin.beginningmind.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.app.entity.PaymentsEntity;

/**
 * <p>payments
 *
 * @author Deolin 2024-04-05
 */
public interface PaymentsMapper {

    /**
     * 插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insert(PaymentsEntity entity);

    /**
     * 批量插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsert(@Param("entities") List<PaymentsEntity> entities);

    /**
     * 批量插入，为null的属性会被作为null插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsertEvenNull(@Param("entities") List<PaymentsEntity> entities);

    /**
     * 批量根据ID更新数据
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdate(@Param("entities") List<PaymentsEntity> entities);

    /**
     * 批量根据ID更新数据，为null对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdateEvenNull(@Param("entities") List<PaymentsEntity> entities);

    /**
     * 根据ID查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    com.spldeolin.beginningmind.app.entity.PaymentsEntity queryById(@Param("studentNumber") Integer studentNumber,
            @Param("checkNumber") String checkNumber);

    /**
     * 根据ID更新数据，忽略值为null的属性
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateById(PaymentsEntity entity);

    /**
     * 根据ID更新数据，为null属性对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateByIdEvenNull(PaymentsEntity entity);

    /**
     * 根据实体内的属性查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<PaymentsEntity> queryByEntity(PaymentsEntity entity);

    /**
     * 获取全部
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<PaymentsEntity> listAll();

    /**
     * 尝试插入，若指定了id并存在，则更新，即INSERT ON DUPLICATE KEY UPDATE
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insertOrUpdate(PaymentsEntity entity);
}

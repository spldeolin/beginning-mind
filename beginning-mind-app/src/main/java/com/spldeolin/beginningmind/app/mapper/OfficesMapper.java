package com.spldeolin.beginningmind.app.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.app.entity.OfficesEntity;

/**
 * <p>offices
 *
 * @author Deolin 2024-04-05
 */
public interface OfficesMapper {

    /**
     * 插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insert(OfficesEntity entity);

    /**
     * 批量插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsert(@Param("entities") List<OfficesEntity> entities);

    /**
     * 批量插入，为null的属性会被作为null插入
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchInsertEvenNull(@Param("entities") List<OfficesEntity> entities);

    /**
     * 批量根据ID更新数据
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdate(@Param("entities") List<OfficesEntity> entities);

    /**
     * 批量根据ID更新数据，为null对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int batchUpdateEvenNull(@Param("entities") List<OfficesEntity> entities);

    /**
     * 根据ID查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    com.spldeolin.beginningmind.app.entity.OfficesEntity queryById(String officeCode);

    /**
     * 根据ID更新数据，忽略值为null的属性
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateById(OfficesEntity entity);

    /**
     * 根据ID更新数据，为null属性对应的字段会被更新为null
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int updateByIdEvenNull(OfficesEntity entity);

    /**
     * 根据多个ID查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<OfficesEntity> queryByIds(@Param("officeCodes") List<String> officeCodes);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    @MapKey("officeCode")
    Map<String, OfficesEntity> queryByIdsEachId(@Param("officeCodes") List<String> officeCodes);

    /**
     * 根据实体内的属性查询
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<OfficesEntity> queryByEntity(OfficesEntity entity);

    /**
     * 获取全部
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    List<OfficesEntity> listAll();

    /**
     * 尝试插入，若指定了id并存在，则更新，即INSERT ON DUPLICATE KEY UPDATE
     * <p>
     * <p>Any modifications may be overwritten by future code generations.
     */
    int insertOrUpdate(OfficesEntity entity);
}

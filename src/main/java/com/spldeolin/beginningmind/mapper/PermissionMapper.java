package com.spldeolin.beginningmind.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.entity.PermissionEntity;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
public interface PermissionMapper {

    int deleteBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 权限
     * @return 插入条数
     */
    int insert(PermissionEntity entity);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）权限
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<PermissionEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id 主键
     * @return （多个）权限
     */
    PermissionEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 权限
     * @return 更新条数
     */
    int updateById(PermissionEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 权限
     * @return 更新条数
     */
    int updateByIdEvenNull(PermissionEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）权限
     */
    List<PermissionEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）（以ID为key）权限
     */
    @MapKey("id")
    Map<Long, PermissionEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）权限
     */
    List<PermissionEntity> queryByEntity(PermissionEntity entity);

}

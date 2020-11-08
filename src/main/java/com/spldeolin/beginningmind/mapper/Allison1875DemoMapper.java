package com.spldeolin.beginningmind.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.entity.Allison1875DemoEntity;

/**
 * 为Allison 1875准备的示例表
 *
 * @author Deolin 2020-11-06
 * @see Allison1875DemoEntity
 */
public interface Allison1875DemoMapper {

    /**
     * 插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 为Allison 1875准备的示例表
     * @return 插入条数
     */
    int insert(Allison1875DemoEntity entity);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）为Allison 1875准备的示例表
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<Allison1875DemoEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id 主键
     * @return （多个）为Allison 1875准备的示例表
     */
    Allison1875DemoEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 为Allison 1875准备的示例表
     * @return 更新条数
     */
    int updateById(Allison1875DemoEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 为Allison 1875准备的示例表
     * @return 更新条数
     */
    int updateByIdEvenNull(Allison1875DemoEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）为Allison 1875准备的示例表
     */
    List<Allison1875DemoEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）（以ID为key）为Allison 1875准备的示例表
     */
    @MapKey("id")
    Map<Long, Allison1875DemoEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）为Allison 1875准备的示例表
     */
    List<Allison1875DemoEntity> queryByEntity(Allison1875DemoEntity entity);

}

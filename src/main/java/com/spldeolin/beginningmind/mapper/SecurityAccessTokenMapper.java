package com.spldeolin.beginningmind.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
public interface SecurityAccessTokenMapper {

    List<SecurityAccessTokenEntity> listAll();

    /**
     * 插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     * @return 插入条数
     */
    int insert(SecurityAccessTokenEntity entity);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<SecurityAccessTokenEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id 主键
     * @return （多个）代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     */
    SecurityAccessTokenEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     * @return 更新条数
     */
    int updateById(SecurityAccessTokenEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     * @return 更新条数
     */
    int updateByIdEvenNull(SecurityAccessTokenEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     */
    List<SecurityAccessTokenEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）（以ID为key）代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     */
    @MapKey("id")
    Map<Long, SecurityAccessTokenEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
     */
    List<SecurityAccessTokenEntity> queryByEntity(SecurityAccessTokenEntity entity);

}

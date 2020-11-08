package com.spldeolin.beginningmind.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.entity.UserEntity;

/**
 * 用户
 *
 * @author Deolin 2018/11/15
 */
@Mapper
public interface UserMapper {

    Optional<UserEntity> getByNameOrMobileOrEmail(String nameOrMobileOrEmail);

    Optional<Long> getIdByName(String name);

    Optional<Long> getIdByEmail(String email);

    Optional<Long> getIdByMobile(String mobile);

    /**
     * 插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户
     * @return 插入条数
     */
    int insert(UserEntity entity);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）用户
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<UserEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id 主键
     * @return （多个）用户
     */
    UserEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户
     * @return 更新条数
     */
    int updateById(UserEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户
     * @return 更新条数
     */
    int updateByIdEvenNull(UserEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）用户
     */
    List<UserEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）（以ID为key）用户
     */
    @MapKey("id")
    Map<Long, UserEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）用户
     */
    List<UserEntity> queryByEntity(UserEntity entity);

}

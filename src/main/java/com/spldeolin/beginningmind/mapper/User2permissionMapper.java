package com.spldeolin.beginningmind.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.spldeolin.beginningmind.entity.User2permissionEntity;

/**
 * 用户与权限的关联关系
 *
 * @author Deolin 2018/12/07
 */
public interface User2permissionMapper {

    List<User2permissionEntity> aaa3(@Param("ids") Collection<Long> ids, @Param("userIds") Collection<Long> userIds);

    List<User2permissionEntity> aaa2(@Param("ids") Collection<Long> ids, @Param("userIds") Collection<Long> userIds);

    List<User2permissionEntity> aaa1(@Param("ids") Collection<Long> ids, @Param("userIds") Collection<Long> userIds);

    List<User2permissionEntity> aaa(@Param("ids") Collection<Long> ids, @Param("userIds") Collection<Long> userIds);

    List<Long> listPermissionsByUsers(Long userId);

    /**
     * 插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户与权限的关联关系
     * @return 插入条数
     */
    int insert(User2permissionEntity entity);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）用户与权限的关联关系
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<User2permissionEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id 主键
     * @return （多个）用户与权限的关联关系
     */
    User2permissionEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户与权限的关联关系
     * @return 更新条数
     */
    int updateById(User2permissionEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 用户与权限的关联关系
     * @return 更新条数
     */
    int updateByIdEvenNull(User2permissionEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）用户与权限的关联关系
     */
    List<User2permissionEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）主键
     * @return （多个）（以ID为key）用户与权限的关联关系
     */
    @MapKey("id")
    Map<Long, User2permissionEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据用户ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param userId 用户ID
     * @return 用户与权限的关联关系
     */
    List<User2permissionEntity> queryByUserId(Long userId);

    /**
     * 根据用户ID删除
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param userId 用户ID
     * @return 删除条数
     */
    int deleteByUserId(Long userId);

    /**
     * 根据多个用户ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param userIds （多个）用户ID
     * @return （多个）用户与权限的关联关系
     */
    List<User2permissionEntity> queryByUserIds(@Param("userIds") Collection<Long> userIds);

    /**
     * 根据权限ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param permissionId 权限ID
     * @return 用户与权限的关联关系
     */
    List<User2permissionEntity> queryByPermissionId(Long permissionId);

    /**
     * 根据权限ID删除
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param permissionId 权限ID
     * @return 删除条数
     */
    int deleteByPermissionId(Long permissionId);

    /**
     * 根据多个权限ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param permissionIds （多个）权限ID
     * @return （多个）用户与权限的关联关系
     */
    List<User2permissionEntity> queryByPermissionIds(@Param("permissionIds") Collection<Long> permissionIds);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）用户与权限的关联关系
     */
    List<User2permissionEntity> queryByEntity(User2permissionEntity entity);

}

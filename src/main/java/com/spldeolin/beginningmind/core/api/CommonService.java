package com.spldeolin.beginningmind.core.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.TooManyResultsException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Deolin
 */
public interface CommonService<E extends CommonEntity> {

    /**
     * 创建一个实体
     *
     * @param entity 待创建实体
     */
    void create(E entity);

    /**
     * 创建一批实体
     *
     * @param entities 待创建的实体
     */
    void create(Collection<E> entities);

    /**
     * 获取一个实体
     *
     * @param id 实体ID
     * @return 实体
     */
    Optional<E> get(Long id);

    /**
     * 获取多个实体
     *
     * @param ids 实体ID列表
     * @return 实体列表
     */
    List<E> list(Collection<Long> ids);

    /**
     * 获取全部实体
     *
     * @return 实体列表
     */
    List<E> listAll();

    /**
     * 更新一个实体，本方法不校验实体是否存在
     *
     * @param entity 待更新实体
     * @return TRUE：更新成功，FALSE：更新失败（实体不存在或是乐观锁）
     */
    boolean update(E entity);

    /**
     * 删除一个实体，本方法不校验实体是否存在
     *
     * @param id 待删除实体的ID
     * @return TRUE：删除成功，FALSE：删除失败（实体不存在）
     */
    boolean delete(Long id);

    /**
     * 删除多个实体，本方法不校验实体是否存在
     *
     * @param ids 待删除实体的ID列表
     * @return TRUE：有实体删除成功，FALSE：所有实体都删除失败（实体都不存在）
     */
    boolean delete(Collection<Long> ids);

    /**
     * 根据若干个条件，检索一个实体
     *
     * @param entity 存放检索条件的对象
     * @return 满足条件的实体
     * @throws TooManyResultsException 满足条件的实体不止一个时（e.g.: Expected one result (or null) to be returned by selectOne(),
     * but found: 2 ）
     */
    Optional<E> searchOne(E entity) throws TooManyResultsException;

    /**
     * 根据参数字段和参数字段值，检索一个实体
     *
     * @param field 字段的Lambda表达式（e.g.: UserEntity::getId）
     * @param value 字段的值
     * @return 满足条件的实体
     * @throws TooManyResultsException 满足条件的实体不止一个时（e.g.: Expected one result (or null) to be returned by selectOne(),
     * but found: 2 ）
     */
    Optional<E> searchOne(SFunction<E, ?> field, Object value) throws TooManyResultsException;

    /**
     * 根据若干个条件，检索多个实体
     *
     * @param entity 存放检索条件的对象
     * @return 满足条件的实体列表
     */
    List<E> searchBatch(E entity);

    /**
     * 根据参数字段和参数字段值，检索多个实体
     *
     * @param field 字段的Lambda表达式（e.g.: UserEntity::getSex）
     * @param value 字段的值
     * @return 满足条件的列表
     */
    List<E> searchBatch(SFunction<E, ?> field, Object value);

    /**
     * 自由检索
     * <pre>
     * 当单表查询的条件比较复杂，或涉及到distinct，order等限定时，建议使用本方法
     * </pre>
     *
     * @param query 条件对象
     * @return 满足条件的实体列表
     */
    List<E> searchBatch(Wrapper<E> query);

    /**
     * 判断实体是否存在
     *
     * @param id 实体ID
     * @return true存在，false不存在
     */
    boolean isExist(Long id);

    /**
     * 判断满足条件的实体是否存在
     *
     * @param entity 存放条件的对象
     * @return true存在，false不存在
     */
    boolean isExist(E entity);

    /**
     * 检索实体个数
     *
     * @param entity 存放条件的对象
     * @return true存在，false不存在
     */
    int count(E entity);

    /**
     * 分页
     *
     * @param page 分页参数
     * @param query 条件对象
     * @return 分页对象
     */
    IPage<E> page(Page page, Wrapper<E> query);

}
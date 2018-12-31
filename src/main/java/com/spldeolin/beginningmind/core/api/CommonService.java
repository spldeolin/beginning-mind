package com.spldeolin.beginningmind.core.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.exceptions.TooManyResultsException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Deolin
 */
public interface CommonService<T> {

    /**
     * 创建一个资源
     *
     * @param model 待创建资源
     */
    void create(T model);

    /**
     * 创建一批资源
     *
     * @param models 待创建的资源
     */
    void create(Collection<T> models);

    /**
     * 获取一个资源
     *
     * @param id 资源ID
     * @return 资源
     */
    Optional<T> get(Long id);

    /**
     * 获取多个资源
     *
     * @param ids 资源ID列表
     * @return 资源列表
     */
    List<T> list(Collection<Long> ids);

    /**
     * 获取多个资源，并以资源ID为key，组成映射表
     *
     * @param ids 资源ID列表
     * @return 资源映射表
     */
    Map<Long, T> map(Collection<Long> ids);

    /**
     * 获取全部资源
     *
     * @return 资源列表
     */
    List<T> listAll();

    /**
     * 获取全部资源，并以资源ID为key，组成映射表
     *
     * @return 资源映射表
     */
    Map<Long, T> mapAll();

    /**
     * 更新一个资源，本方法不校验资源是否存在
     *
     * @param model 待更新资源
     * @return TRUE：更新成功，FALSE：更新失败（资源不存在或是乐观锁）
     */
    boolean update(T model);

    /**
     * 删除一个资源，本方法不校验资源是否存在
     *
     * @param id 待删除资源的ID
     * @return TRUE：删除成功，FALSE：删除失败（资源不存在）
     */
    boolean delete(Long id);

    /**
     * 删除多个资源，本方法不校验资源是否存在
     *
     * @param ids 待删除资源的ID列表
     * @return TRUE：有资源删除成功，FALSE：所有资源都删除失败（资源都不存在）
     */
    boolean delete(Collection<Long> ids);

    /**
     * 根据若干个条件，检索一个资源
     *
     * @param m 存放检索条件的对象
     * @return 满足条件的资源
     * @throws TooManyResultsException 满足条件的资源不止一个时
     */
    Optional<T> searchOne(T m) throws TooManyResultsException;

    /**
     * 根据若干个条件，检索多个资源
     *
     * @param m 存放检索条件的对象
     * @return 满足条件的资源列表
     */
    List<T> searchBatch(T m);

    /**
     * 自由检索
     * <pre>
     * 当单表查询的条件比较复杂，或涉及到distinct，order等限定时，建议使用本方法
     * </pre>
     *
     * @param queryWrapper 条件对象
     * @return 满足条件的资源列表
     */
    List<T> searchBatch(Wrapper<T> queryWrapper);

    /**
     * 判断资源是否存在
     *
     * @param id 资源ID
     * @return true存在，false不存在
     */
    boolean isExist(Long id);

    /**
     * 判断满足条件的资源是否存在
     *
     * @param model 存放条件的对象
     * @return true存在，false不存在
     */
    boolean isExist(T model);

    /**
     * 检索资源个数
     *
     * @param model 存放条件的对象
     * @return true存在，false不存在
     */
    int count(T model);

    IPage<T> page(Page page, Wrapper<T> queryWrapper);

}
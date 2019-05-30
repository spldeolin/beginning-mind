package com.spldeolin.beginningmind.core.common;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/**
 * 数据访问层的通用抽象类
 *
 * @author Deolin
 */
public abstract class CommonDao<E extends CommonEntity> {

    private static final Integer MAX_BATCH_SIZE = 1000;

    @Autowired
    private BaseMapper<E> baseMapper;

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public CommonDao() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<E>) pt.getActualTypeArguments()[0];
    }

    /**
     * 创建一个实体
     *
     * @param entity 待创建实体
     */
    public void create(E entity) {
        baseMapper.insert(entity);
    }

    /**
     * 创建一批实体
     *
     * @param entities 待创建的实体
     */
    public void create(Collection<E> entities) {
        if (entities.size() == 0) {
            throw new IllegalArgumentException("entities长度不应为0");
        }

        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (E anEntityList : entities) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % MAX_BATCH_SIZE == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
    }

    /**
     * 获取一个实体
     *
     * @param id 实体ID
     * @return 实体
     */
    public Optional<E> get(Long id) {
        return Optional.ofNullable(baseMapper.selectById(id));
    }

    /**
     * 获取多个实体
     *
     * @param ids 实体ID列表
     * @return 实体列表
     */
    public List<E> list(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        return baseMapper.selectBatchIds(ids);
    }

    /**
     * 获取全部实体
     *
     * @return 实体列表
     */
    public List<E> listAll() {
        return baseMapper.selectList(null);
    }

    /**
     * 更新一个实体，本方法不校验实体是否存在
     *
     * @param entity 待更新实体
     * @return TRUE：更新成功，FALSE：更新失败（实体不存在或是乐观锁）
     */
    public boolean update(E entity) {
        boolean updated = baseMapper.updateById(entity) != 0;
        return updated;
    }

    /**
     * 删除一个实体，本方法不校验实体是否存在
     *
     * @param id 待删除实体的ID
     * @return TRUE：删除成功，FALSE：删除失败（实体不存在）
     */
    public boolean delete(Long id) {
        boolean deleted = baseMapper.deleteById(id) != 0;
        return deleted;
    }

    /**
     * 删除多个实体，本方法不校验实体是否存在
     *
     * @param ids 待删除实体的ID列表
     * @return TRUE：有实体删除成功，FALSE：所有实体都删除失败（实体都不存在）
     */
    public boolean delete(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }
        boolean deleted = baseMapper.deleteBatchIds(ids) != 0;
        return deleted;
    }

    /**
     * 检索实体个数
     *
     * @param entity 存放条件的对象
     * @return true存在，false不存在
     */
    public int count(E entity) {
        return baseMapper.selectCount(new QueryWrapper<>(entity));
    }

    /**
     * 查询实体是否存在
     *
     * @param id 实体的ID
     * @return TRUE：存在，FALSE：不存在
     */
    public boolean isExist(Long id) {
        LambdaQueryWrapper<E> query = new LambdaQueryWrapper<>();
        query.eq(E::getId, id);
        return baseMapper.selectCount(query) > 0;
    }

    /**
     * 更新多个实体
     *
     * @param entity 存放所有需要更新的值
     * @param query 条件对象
     * @return 成功更新的条目数
     */
    protected int update(E entity, Wrapper<E> query) {
        return baseMapper.update(entity, query);
    }

    /**
     * 根据若干个条件，检索一个实体
     *
     * @param query 条件对象
     * @return 满足条件的实体
     * @throws TooManyResultsException 满足条件的实体不止一个时（e.g.: Expected one result (or null) to be returned by selectOne(),
     * but found: 2 ）
     */
    protected Optional<E> searchOne(Wrapper<E> query) throws TooManyResultsException {
        return batchToOne(searchBatch(query));
    }

    /**
     * 自由检索
     * <pre>
     * 当单表查询的条件比较复杂，或涉及到distinct，order等限定时，建议使用本方法
     * </pre>
     *
     * @param query 条件对象
     * @return 满足条件的实体列表
     */
    protected List<E> searchBatch(Wrapper<E> query) {
        return baseMapper.selectList(query);
    }

    /**
     * 分页
     *
     * @param page 分页参数
     * @param query 条件对象
     * @return 分页对象
     */
    @SuppressWarnings("unchecked")
    protected IPage<E> page(Page page, Wrapper<E> query) {
        return baseMapper.selectPage(page, query);
    }

    private Optional<E> batchToOne(Collection<E> entities) {
        if (entities.size() == 0) {
            return Optional.empty();
        }
        if (entities.size() > 1) {
            throw new TooManyResultsException("满足条件的资源不止一个");
        }
        return Optional.of(entities.iterator().next());
    }

    private Class<E> currentModelClass() {
        return entityClass;
    }

    private String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    private SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

}
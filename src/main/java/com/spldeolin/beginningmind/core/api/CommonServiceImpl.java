package com.spldeolin.beginningmind.core.api;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author Deolin
 */
public class CommonServiceImpl<E> extends ServiceImpl<BaseMapper<E>, E> implements CommonService<E> {

    @Autowired
    private BaseMapper<E> baseMapper;

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public CommonServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<E>) pt.getActualTypeArguments()[0];
    }

    @Override
    protected Class<E> currentModelClass() {
        return entityClass;
    }

    @Override
    public void create(E entity) {
        baseMapper.insert(entity);
    }

    @Override
    public void create(Collection<E> entities) {
        if (entities.size() == 0) {
            throw new IllegalArgumentException("entities长度不应为0");
        }

        super.saveBatch(entities);
    }

    @Override
    public Optional<E> get(Long id) {
        return Optional.ofNullable(baseMapper.selectById(id));
    }

    @Override
    public List<E> list(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public boolean update(E entity) {
        boolean updated = baseMapper.updateById(entity) != 0;
        return updated;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Long id) {
        boolean deleted = baseMapper.deleteById(id) != 0;
        return deleted;
    }

    @Override
    public boolean delete(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }
        boolean deleted = super.removeByIds(ids);
        return deleted;
    }

    @Override
    public Optional<E> searchOne(E entity) throws TooManyResultsException {
        return batchToOne(searchBatch(entity));
    }

    @Override
    public List<E> searchBatch(E entity) {
        return baseMapper.selectList(new QueryWrapper<>(entity));
    }

    @Override
    public List<E> searchBatch(Wrapper<E> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<E> listAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean isExist(Long id) {
        return baseMapper.selectCount(new QueryWrapper<E>().eq("id", id)) > 0;
    }

    @Override
    public boolean isExist(E entity) {
        return count(entity) > 0;
    }

    @Override
    public int count(E entity) {
        return baseMapper.selectCount(new QueryWrapper<>(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public IPage<E> page(Page page, Wrapper<E> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<E> searchBatch(SFunction<E, ?> field, Object value) {
        LambdaQueryWrapper<E> query = new LambdaQueryWrapper<>();
        query.eq(field, value);

        return baseMapper.selectList(query);
    }

    @Override
    public Optional<E> searchOne(SFunction<E, ?> field, Object value) {
        LambdaQueryWrapper<E> query = new LambdaQueryWrapper<>();
        query.eq(field, value);

        return Optional.ofNullable(baseMapper.selectOne(query));
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

}
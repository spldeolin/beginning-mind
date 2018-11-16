package com.spldeolin.beginningmind.core.api;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.api.dto.LogicDeleteDocument;

/**
 * @author Deolin
 */
public class CommonServiceImpl<T> extends ServiceImpl<BaseMapper<T>, T> implements CommonService<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> modelClass;

    private boolean isIdGetable;

    private String tableName;

    @SuppressWarnings("unchecked")
    public CommonServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
        isIdGetable = ArrayUtils.contains(modelClass.getInterfaces(), IdGetable.class);

        TableName tableName = modelClass.getAnnotation(TableName.class);
        if (tableName != null) {
            this.tableName = tableName.value();
        } else {
            this.tableName = modelClass.getSimpleName();
        }
    }

    @Override
    protected Class<T> currentModelClass() {
        return modelClass;
    }

    @Override
    public void create(T model) {
        baseMapper.insert(model);
    }

    @Override
    public void create(Collection<T> models) {
        if (models.size() == 0) {
            throw new IllegalArgumentException("models长度不应为0");
        }

        super.saveBatch(models);
    }

    @Override
    public Optional<T> get(Long id) {
        return Optional.ofNullable(baseMapper.selectById(id));
    }

    @Override
    public List<T> list(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public Map<Long, T> map(Collection<Long> ids) {
        ensureIsIdGetable();
        return collectionToMap(list(ids));
    }

    @Override
    public boolean update(T model) {
        boolean updated = baseMapper.updateById(model) != 0;
        return updated;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        T model = baseMapper.selectById(id);

        boolean deleted = baseMapper.deleteById(id) != 0;
        if (deleted) {
            LogicDeleteDocument document = new LogicDeleteDocument(model);
            mongoTemplate.insert(document, "ld_" + tableName);
        }

        return deleted;
    }

    @Override
    public boolean delete(Collection<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        Collection<T> models = this.list(ids);

        boolean deleted = super.removeByIds(ids);
        if (deleted) {
            List<LogicDeleteDocument> documents = Lists.newArrayListWithCapacity(models.size());
            LocalDateTime now = LocalDateTime.now();
            models.forEach(model -> documents.add(new LogicDeleteDocument(now, model)));
            mongoTemplate.insert(documents, "ld_" + tableName);
        }

        return deleted;
    }

    @Override
    public Optional<T> searchOne(T model) throws TooManyResultsException {
        return batchToOne(searchBatch(model));
    }

    @Override
    public Optional<T> searchOne(String modelFieldName, Object value) {
        return batchToOne(searchBatch(modelFieldName, value));
    }

    @Override
    public List<T> searchBatch(T model) {
        return baseMapper.selectList(new QueryWrapper<>(model));
    }

    @Override
    public List<T> searchBatch(String modelFieldName, Object value) {
        Wrapper<T> wrapper = new QueryWrapper<T>().eq(modelFieldName, value);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<T> searchBatch(QueryWrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<T> listAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public Map<Long, T> mapAll() {
        ensureIsIdGetable();

        return collectionToMap(listAll());
    }

    @Override
    public boolean isExist(T model) {
        return count(model) > 0;
    }

    @Override
    public int count(T model) {
        return baseMapper.selectCount(new QueryWrapper<>(model));
    }

    private void ensureIsIdGetable() {
        if (!isIdGetable) {
            throw new IllegalArgumentException(modelClass.getSimpleName() + " 未实现IdGetable接口");
        }
    }

    private Map<Long, T> collectionToMap(Collection<T> list) {
        Map<Long, T> map = Maps.newHashMapWithExpectedSize(list.size());
        list.forEach(t -> map.put(((IdGetable) t).getId(), t));
        return map;
    }

    private Optional<T> batchToOne(Collection<T> models) {
        if (models.size() == 0) {
            return Optional.empty();
        }
        if (models.size() > 1) {
            throw new TooManyResultsException("满足条件的资源不止一个");
        }
        return Optional.of(models.iterator().next());
    }

}
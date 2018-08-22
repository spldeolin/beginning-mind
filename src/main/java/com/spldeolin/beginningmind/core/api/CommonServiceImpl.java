package com.spldeolin.beginningmind.core.api;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

@Log4j2
public class CommonServiceImpl<M> implements CommonService<M> {

    @Autowired
    private CommonMapper<M> mapper;

    private Class<M> clazz;

    private boolean isIdGetable;

    @SuppressWarnings("unchecked")
    public CommonServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<M>) pt.getActualTypeArguments()[0];
        isIdGetable = ArrayUtils.contains(clazz.getInterfaces(), IdGetable.class);
    }

    @Override
    public void create(M model) {
        mapper.insert(model);
    }

    @Override
    public void create(List<M> models) {
        if (models.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }
        mapper.insertBatch(models);
    }

    @Override
    public Optional<M> get(Long id) {
        return Optional.ofNullable(mapper.selectById(id));
    }

    @Override
    public List<M> list(List<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        String idsSQL = Strings.join(ids, ',');
        return mapper.selectBatchByIds(idsSQL);
    }

    @Override
    public Map<Long, M> map(List<Long> ids) {
        if (!isIdGetable) {
            throw new ServiceException(clazz.getSimpleName() + " 不含有ID属性");
        }

        return listToMap(list(ids));
    }

    @Override
    public boolean update(M model) {
        return mapper.updateByIdSelective(model) != 0;
    }

    @Override
    public boolean delete(Long id) {
        return mapper.deleteById(id) != 0;
    }

    @Override
    public boolean delete(List<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("models长度不应为0");
        }
        String idsSQL = Strings.join(ids, ',');
        return mapper.deleteBatchByIds(idsSQL) != 0;
    }

    @Override
    public boolean physicallyDelete(Long id) {
        M model = mapper.selectById(id);
        if (model != null) {
            log.warn("物理删除：" + model);
            return mapper.physicallyDelete(id) != 0;
        }
        return false;
    }

    @Override
    public Optional<M> searchOne(M model) throws TooManyResultsException {
        return batchToOne(searchBatch(model));
    }

    @Override
    public Optional<M> searchOne(String modelFieldName, Object value) {
        return batchToOne(searchBatch(modelFieldName, value));
    }

    @Override
    public List<M> searchBatch(M model) {
        return mapper.selectBatchByModel(model);
    }

    @Override
    public List<M> searchBatch(String modelFieldName, Object value) {
        Condition condition = new Condition(clazz);
        condition.createCriteria().andEqualTo(modelFieldName, value);
        return mapper.selectBatchByCondition(condition);
    }

    @Override
    public List<M> searchBatch(Condition condition) {
        return mapper.selectBatchByCondition(condition);
    }

    @Override
    public List<M> listAll() {
        return mapper.selectAll();
    }

    @Override
    public Map<Long, M> mapAll() {
        if (!isIdGetable) {
            throw new ServiceException(clazz.getSimpleName() + " 不含有ID属性");
        }

        return listToMap(listAll());
    }

    @Override
    public boolean isExist(Long id) {
        Condition condition = new Condition(clazz);
        condition.selectProperties("id");
        condition.createCriteria().andEqualTo("id", id);
        return mapper.selectCountByCondition(condition) > 0;
    }

    @Override
    public boolean isExist(M model) {
        return count(model) > 0;
    }

    @Override
    public int count(M model) {
        return mapper.selectCountByModel(model);
    }

    private Optional<M> batchToOne(List<M> models) {
        if (models.size() == 0) {
            return Optional.empty();
        }
        if (models.size() > 1) {
            throw new TooManyResultsException("满足条件的资源不止一个");
        }
        return Optional.of(models.get(0));
    }

    private Map<Long, M> listToMap(List<M> list) {
        Map<Long, M> map = Maps.newHashMapWithExpectedSize(list.size());
        list.forEach(m -> map.put(((IdGetable) m).getId(), m));
        return map;
    }

}

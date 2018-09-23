package com.spldeolin.beginningmind.core.api;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.dto.CacheStatsDTO;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * @author Deolin
 */
@Log4j2
public class CommonServiceImpl<M> implements CommonService<M> {

    @Autowired
    private CommonMapper<M> mapper;

    @Autowired
    private CoreProperties coreProperties;

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
            throw new IllegalArgumentException("models长度不应为0");
        }
        mapper.insertBatch(models);
    }

    @Override
    public Optional<M> get(Long id) {
        return selectByIdCacheable.getUnchecked(id);
    }

    @Override
    public List<M> list(List<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        StringBuilder idsSQL = new StringBuilder(64);
        for (Long id : ids) {
            idsSQL.append(id).append(",");
        }
        idsSQL.deleteCharAt(idsSQL.length() - 1);
        return selectBatchByIdsCacheable.getUnchecked(idsSQL.toString());
    }

    @Override
    public Map<Long, M> map(List<Long> ids) {
        ensureIsIdGetable();

        return listToMap(list(ids));
    }

    @Override
    public boolean update(M model) {
        boolean updated = mapper.updateByIdSelective(model) != 0;
        if (updated) {
            this.clearCache();
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = mapper.deleteById(id) != 0;
        if (deleted) {
            this.clearCache();
        }
        return deleted;
    }

    @Override
    public boolean delete(List<Long> ids) {
        if (ids.size() == 0) {
            throw new IllegalArgumentException("ids长度不应为0");
        }

        StringBuilder idsSQL = new StringBuilder(64);
        for (Long id : ids) {
            idsSQL.append(id).append(",");
        }
        idsSQL.deleteCharAt(idsSQL.length() - 1);

        boolean deleted = mapper.deleteBatchByIds(idsSQL.toString()) != 0;

        if (deleted) {
            this.clearCache();
        }

        return deleted;
    }

    @Override
    public boolean physicallyDelete(Long id) {
        M model = mapper.selectById(id);
        if (model != null) {
            log.warn("物理删除：" + model);

            boolean physicallyDeleted = mapper.physicallyDelete(id) != 0;
            if (physicallyDeleted) {
                this.clearCache();
            }
            return physicallyDeleted;
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
        return selectBatchByModelCacheable.getUnchecked(model);
    }

    @Override
    public List<M> searchBatch(String modelFieldName, Object value) {
        Condition condition = new Condition(clazz);
        condition.createCriteria().andEqualTo(modelFieldName, value);
        return searchBatch(condition);
    }

    @Override
    public List<M> searchBatch(Condition condition) {
        return selectBatchByConditionCacheable.getUnchecked(condition);
    }

    @Override
    public List<M> listAll() {
        if (selectAllCacheable != null) {
            return selectAllCacheable;
        } else {
            return mapper.selectAll();
        }
    }

    @Override
    public Map<Long, M> mapAll() {
        ensureIsIdGetable();

        return listToMap(listAll());
    }

    @Override
    public boolean isExist(Long id) {
        Condition condition = new Condition(clazz);
        condition.selectProperties("id");
        condition.createCriteria().andEqualTo("id", id);
        return selectCountByConditionCacheable.getUnchecked(condition) > 0;
    }

    @Override
    public boolean isExist(M model) {
        return count(model) > 0;
    }

    @Override
    public int count(M model) {
        return selectCountByModelCacheable.getUnchecked(model);
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

    private void ensureIsIdGetable() {
        if (!isIdGetable) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " 未实现IdGetable接口");
        }
    }

    private final LoadingCache<Long, Optional<M>> selectByIdCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<Long, Optional<M>>() {
                @Override
                public Optional<M> load(Long id) {
                    return Optional.ofNullable(mapper.selectById(id));
                }
            });

    private final LoadingCache<String, List<M>> selectBatchByIdsCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<String, List<M>>() {
                @Override
                public List<M> load(String ids) {
                    return mapper.selectBatchByIds(ids);
                }
            });

    private List<M> selectAllCacheable;

    private final LoadingCache<M, List<M>> selectBatchByModelCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<M, List<M>>() {
                @Override
                public List<M> load(M model) {
                    return mapper.selectBatchByModel(model);
                }
            });

    private final LoadingCache<Condition, List<M>> selectBatchByConditionCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<Condition, List<M>>() {
                @Override
                public List<M> load(Condition condition) {
                    return mapper.selectBatchByCondition(condition);
                }
            });

    private final LoadingCache<M, Integer> selectCountByModelCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<M, Integer>() {
                @Override
                public Integer load(M condition) {
                    return mapper.selectCountByModel(condition);
                }
            });

    private final LoadingCache<Condition, Integer> selectCountByConditionCacheable = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(64)
            .build(new CacheLoader<Condition, Integer>() {
                @Override
                public Integer load(Condition condition) {
                    return mapper.selectCountByCondition(condition);
                }
            });

    private void clearCache() {
        selectByIdCacheable.invalidateAll();
        selectBatchByIdsCacheable.invalidateAll();
        selectAllCacheable = null;
        selectBatchByModelCacheable.invalidateAll();
        selectBatchByConditionCacheable.invalidateAll();
        selectCountByModelCacheable.invalidateAll();
        selectCountByConditionCacheable.invalidateAll();
    }

    public Map<String, CacheStatsDTO> listCacheStatses() {
        Map<String, CacheStatsDTO> result = Maps.newHashMap();
        result.put("selectByIdCacheable", CacheStatsDTO.fromCacheStats(selectByIdCacheable.stats()));
        result.put("selectBatchByIdsCacheable", CacheStatsDTO.fromCacheStats(selectBatchByIdsCacheable.stats()));
        result.put("selectBatchByModelCacheable", CacheStatsDTO.fromCacheStats(selectBatchByModelCacheable.stats()));
        result.put("selectBatchByConditionCacheable",
                CacheStatsDTO.fromCacheStats(selectBatchByConditionCacheable.stats()));
        result.put("selectCountByModelCacheable", CacheStatsDTO.fromCacheStats(selectCountByModelCacheable.stats()));
        result.put("selectCountByConditionCacheable",
                CacheStatsDTO.fromCacheStats(selectCountByConditionCacheable.stats()));
        return result;
    }

}
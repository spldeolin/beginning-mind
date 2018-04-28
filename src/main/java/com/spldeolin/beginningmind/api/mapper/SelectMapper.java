package com.spldeolin.beginningmind.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import com.spldeolin.beginningmind.api.mapper.provider.SelectMapperProvider;
import tk.mybatis.mapper.entity.Condition;

public interface SelectMapper<M> {

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    M selectById(Long id);

    /**
     * @param ids e.g.: 1, 2, 3, 4, 5, 6
     */
    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    List<M> selectBatchByIds(String ids);

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    List<M> selectAll();

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    List<M> selectBatchByModel(M model);

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    List<M> selectBatchByCondition(Condition condition);

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    int selectCountByModel(M model);

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    int selectCountByCondition(Condition condition);

}

package com.spldeolin.beginningmind.api;

import com.spldeolin.beginningmind.api.mapper.DeleteMapper;
import com.spldeolin.beginningmind.api.mapper.InsertMapper;
import com.spldeolin.beginningmind.api.mapper.PhysicallyDeleteMapper;
import com.spldeolin.beginningmind.api.mapper.SelectMapper;
import com.spldeolin.beginningmind.api.mapper.UpdateMapper;

/**
 * 通用Mapper，所有Mapper接口都应该继承这个接口。
 *
 * <pre>
 * BaseMapper接口：使mapper包含完整的CRUD方法
 * ConditionMapper接口：使mapper支持Condition类型参数
 * MySqlMapper接口：使mapper支持MySQL特有的批量插入和返回自增字段
 * IdsMapper接口：使mapper支持批量ID操作
 * </pre>
 *
 * @param <M> Model类.class
 * @author Deolin
 */
public interface CommonMapper<M> extends DeleteMapper<M>, InsertMapper<M>, PhysicallyDeleteMapper, SelectMapper<M>,
        UpdateMapper<M> {
}

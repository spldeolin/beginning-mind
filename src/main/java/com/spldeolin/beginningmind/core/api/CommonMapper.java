package com.spldeolin.beginningmind.core.api;

import com.spldeolin.beginningmind.core.api.mapper.ClearMapper;
import com.spldeolin.beginningmind.core.api.mapper.DeleteMapper;
import com.spldeolin.beginningmind.core.api.mapper.InsertMapper;
import com.spldeolin.beginningmind.core.api.mapper.PhysicallyDeleteMapper;
import com.spldeolin.beginningmind.core.api.mapper.SelectMapper;
import com.spldeolin.beginningmind.core.api.mapper.UpdateMapper;

/**
 * 通用Mapper，所有Mapper接口都应该继承这个接口。
 *
 * @param <M> Model类.class
 * @author Deolin
 */
public interface CommonMapper<M> extends DeleteMapper<M>, InsertMapper<M>, PhysicallyDeleteMapper, SelectMapper<M>,
        UpdateMapper<M>, ClearMapper<M> {

}

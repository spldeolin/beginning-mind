package com.spldeolin.beginningmind.dao;

import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.Goods;

import org.apache.ibatis.annotations.Mapper;
/**
 * “商品”数据库映射
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Mapper
public interface GoodsMapper extends CommonMapper<Goods> {
}
package com.ltj.mybatis.common.mapper;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-11-09 21:47
 * @describe： 通用Mapper
 * @version: 1.0
 */
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {
}

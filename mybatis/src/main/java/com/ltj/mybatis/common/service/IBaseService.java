package com.ltj.mybatis.common.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-11-09 21:20
 * @describe： 通用service接口
 * @version: 1.0
 */
public interface IBaseService<T, ID> {

    List<T> findAll();

    PageInfo<T> findAll(T t);

    T findById(ID id);

    T save(T entity);

    T update(T entity);

    Integer deleteById(ID id);

    boolean existsById(ID id);

}

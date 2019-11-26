package com.ltj.mybatis.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ltj.mybatis.common.entity.BaseEntity;
import com.ltj.mybatis.common.exception.CrudException;
import com.ltj.mybatis.common.mapper.TkMapper;
import com.ltj.mybatis.common.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-11-09 21:35
 * @describe： service 通用实现类
 * @version: 1.0
 */

@Slf4j
public class BaseServiceImpl<T extends BaseEntity, ID, M extends TkMapper> implements IBaseService<T, ID> {

    @Autowired
    protected M baseMapper;

    @Override
    public List<T> findAll() {
        return baseMapper.selectAll();
    }

    @Override
    public PageInfo<T> findAll(T t) {
        if (t.getPage() != null && t.getSize() != null) {
            PageHelper.startPage(t.getPage(), t.getSize());
        }
        return new PageInfo<T>(baseMapper.selectAll());
    }

    @Override
    public T findById(ID id) {
        return (T) baseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public T save(T entity) throws CrudException {
        if (entity == null) {
            throw new CrudException("不能保存空的实体类");
        }
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpTime(now);
        baseMapper.insertSelective(entity);
        log.info("保存: {}", entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public T update(T entity) {
        if (entity == null) {
            throw new CrudException("不能修改空的实体类");
        }
        Date now = new Date();
        entity.setUpTime(now);
        baseMapper.updateByPrimaryKeySelective(entity);
        log.info("修改: {}", entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer deleteById(ID id) {
        if (id == null || !baseMapper.existsWithPrimaryKey(id)) {
            throw new CrudException("ID 不存在, 不能删除数据");
        }
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public boolean existsById(ID id) {
        log.info("存在此 ID 的数据: {}", id);
        return baseMapper.existsWithPrimaryKey(id);
    }
}

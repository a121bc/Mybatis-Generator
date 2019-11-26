package com.ltj.mybatis.common.controller;

import com.ltj.mybatis.common.entity.BaseEntity;
import com.ltj.mybatis.common.entity.Result;
import com.ltj.mybatis.common.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-11-09 23:01
 * @describe： 通用 增删改查 控制器
 * @version: 1.0
 */

@Slf4j
public class BaseController<T extends BaseEntity, ID, S extends IBaseService<T, ID>> {

    @Autowired
    protected S baseService;

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") ID id) {
        log.info("get ID : {}", id);
        return Result.success(baseService.findById(id));
    }

    @GetMapping("/all")
    public Result findAll() {
        return Result.success(baseService.findAll());
    }

    @GetMapping("/page")
    public Result findAll(T t) {
        log.info("page :  {} size : {}", t.getPage(), t.getSize());
        return Result.success(baseService.findAll(t));
    }

    @PostMapping()
    public Result save(@RequestBody T entity) {
        log.info("save :  {}", entity);
        return Result.success(baseService.save(entity));
    }

    @PutMapping()
    public Result update(@RequestBody T entity) {
        log.info("update:  {}", entity);
        return Result.success(baseService.update(entity));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") ID id) {
        log.info("delete:  {}", id);
        baseService.deleteById(id);
        return Result.success();
    }
}

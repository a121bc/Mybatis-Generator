package com.ltj.mybatis.common.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ltj.mybatis.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-12-12 11:51
 * @describe： 通用 增删改查 控制器
 * @version: 1.0
 */

@Slf4j
public class BaseController<T, S extends IService<T>> {

    @Autowired(required = false)
    protected S baseService;

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") Serializable id) {
        log.info("get ID : {}", id);
        return Result.success(baseService.getById(id));
    }

    @GetMapping("/all")
    public Result findAll() {
        return Result.success(baseService.listObjs());
    }


    @PostMapping()
    public Result save(@RequestBody T entity) {
        log.info("save :  {}", entity);
        return Result.success(baseService.save(entity));
    }

    @PutMapping()
    public Result update(@RequestBody T entity) {
        log.info("update:  {}", entity);
        return Result.success(baseService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Serializable id) {
        log.info("delete:  {}", id);
        baseService.removeById(id);
        return Result.success();
    }
}
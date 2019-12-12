package com.ltj.mybatis.module.tables.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltj.mybatis.module.tables.po.Tables;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TablesMapper extends BaseMapper<Tables> {

    //查询所有表
    List<Tables> listTable();

    //查询数据库名
    String selectDataBaseName();
}

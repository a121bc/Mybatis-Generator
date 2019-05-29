package com.ltj.mybatis.module.Tables.mapper;

import com.ltj.mybatis.framework.util.MyMapper;
import com.ltj.mybatis.module.Tables.po.Tables;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TablesMapper extends MyMapper<Tables> {

    //查询所有表
    List<Tables> listTable();

    //查询数据库名
    String selectDataBaseName();
}
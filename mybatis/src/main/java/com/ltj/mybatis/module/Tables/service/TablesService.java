package com.ltj.mybatis.module.Tables.service;

import com.ltj.mybatis.module.Tables.po.Tables;

import java.util.List;

public interface TablesService {

    //查询所有记录
    List<Tables> listTable();

    //查询数据库名
    String selectDataBaseName();

}
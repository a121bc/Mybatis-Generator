package com.ltj.mybatis.module.tables.service;

import com.ltj.mybatis.module.tables.po.Tables;

import java.util.List;

public interface TablesService {

    //查询所有记录
    List<Tables> listTable();

    //查询数据库名
    String selectDataBaseName();

    //创建实体
//    Boolean createBean(String tablename, String prefix, Integer extend);

    //创建所有
    String createAllBean(String tablenames, String prefix, Integer extend);


}

package com.ltj.mybatis.module.Columns.mapper;

import com.ltj.mybatis.framework.util.MyMapper;
import com.ltj.mybatis.module.Columns.po.Columns;
import com.ltj.mybatis.module.Columns.po.ColumnsExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ColumnsMapper extends MyMapper<Columns> {

    //查询表字段信息
    List<ColumnsExtend> listTableColumn(@Param("tablename") String tablename);
}
package com.ltj.mybatis.module.columns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltj.mybatis.module.columns.po.Columns;
import com.ltj.mybatis.module.columns.po.ColumnsExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ColumnsMapper extends BaseMapper<Columns> {

    //查询表字段信息
    List<ColumnsExtend> listTableColumn(@Param("tablename") String tablename);
}

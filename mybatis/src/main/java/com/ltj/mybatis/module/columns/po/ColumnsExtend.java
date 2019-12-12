package com.ltj.mybatis.module.columns.po;

import lombok.Data;

/**
 * 描 述
 * 创 建 人 刘天珺
 * 创建时间 2019-5-29 0029 16:24
 */
@Data
public class ColumnsExtend extends Columns {

    private String jdbcType;

    private String javaType;
}

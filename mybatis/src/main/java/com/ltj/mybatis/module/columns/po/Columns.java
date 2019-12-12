package com.ltj.mybatis.module.columns.po;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Columns{

    /* 登记目录 */
    private String table_catalog;

    /* 数据库名 */
    private String table_schema;

    /* 表名 */
    private String table_name;

    /* 字段名 */
    private String column_name;

    /* 字段标识 */
    private BigInteger ordinal_position;

    /* 字段默认值 */
    private String column_default;

    /* 是否可以为null */
    private String is_nullable;

    /* 数据类型 */
    private String data_type;

    /* 字段最大字符数 */
    private BigInteger character_maximum_length;

    /* 字段最大字节数 */
    private BigInteger character_octet_length;

    /* 数字精度 */
    private BigInteger numeric_precision;

    /* 小数位 */
    private BigInteger numeric_scale;

    /* 子类型代码 */
    private BigInteger datetime_precision;

    /* 字符集 */
    private String character_set_name;

    /* 字符集排序规则 */
    private String collation_name;

    /* 字段类型 */
    private String column_type;

    /* 索引类型 */
    private String column_key;

    /* 其他信息 */
    private String extra;

    /* 权限 */
    private String privileges;

    /* 字段注释 */
    private String column_comment;

    /* 组合字段公式 */
    private String generation_expression;

}

package com.ltj.mybatis.module.tables.po;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class Tables{

    /* 登记目录 */
    private String table_catalog;

    /* 数据库名 */
    private String table_schema;

    /* 表名 */
    private String table_name;

    /* 表类型 */
    private String table_type;

    /* 存储引擎 */
    private String engine;

    /* 版本 */
    private BigInteger version;

    /* 行格式 */
    private String row_format;

    /* 数据量 */
    private BigInteger table_rows;

    /* 平局长度 */
    private BigInteger avg_row_length;

    /* 数据长度 */
    private BigInteger data_length;

    /* 最大数据长度 */
    private BigInteger max_data_length;

    /* 索引长度 */
    private BigInteger index_length;

    /* 空间碎片 */
    private BigInteger data_free;

    /* 自增主键当前值 */
    private BigInteger auto_increment;

    /* 创建时间 */
    private Date create_time;

    /* 修改时间 */
    private Date update_time;

    /* 检查时间 */
    private Date check_time;

    /* 字符校验编码集 */
    private String table_collation;

    /* 校验和 */
    private BigInteger checksum;

    /* 创建选项 */
    private String create_options;

    /* 注释 */
    private String table_comment;

}

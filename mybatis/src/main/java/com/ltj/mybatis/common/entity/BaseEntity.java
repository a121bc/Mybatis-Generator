package com.ltj.mybatis.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author: Liu Tian Jun
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {

    @Column(name = "create_time")
    protected Date createTime;

    @Column(name = "up_time")
    protected Date upTime;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer size = 10;
}

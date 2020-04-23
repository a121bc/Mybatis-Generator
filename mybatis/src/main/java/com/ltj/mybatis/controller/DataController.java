package com.ltj.mybatis.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.ltj.mybatis.module.data.po.DataSourceDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.Set;

/**
 * @describe： TODO
 * @author: Liu Tian Jun
 * @Date: 2020-04-23 11:13
 * @version: 1.0
 */

@Controller
@RequestMapping("/Data")
@AllArgsConstructor
public class DataController {

    private final DataSource dataSource;
    private final DataSourceCreator dataSourceCreator;

    /**
     * @Description 获取当前所有数据源
     * @param
     * @return java.util.Set<java.lang.String>
     * @author Liu Tian Jun
     * @date 11:12 2020-04-23 0023
     **/
    @GetMapping
    @ResponseBody
    public Set<String> now() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getCurrentDataSources().keySet();
    }

    /**
     * @Description 通用添加数据源（推荐）
     * @param dto
     * @return java.util.Set<java.lang.String>
     * @author Liu Tian Jun
     * @date 11:13 2020-04-23 0023
     **/
    @PostMapping("/add")
    public String add(DataSourceDTO dto) {
        dto.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        return "redirect:/Tables/?ds="+dto.getPollName();
    }

}

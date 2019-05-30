package com.ltj.mybatis.module.Tables.service.impl;

import com.ltj.mybatis.framework.util.FileManageUtils;
import com.ltj.mybatis.module.Columns.mapper.ColumnsMapper;
import com.ltj.mybatis.module.Columns.po.ColumnsExtend;
import com.ltj.mybatis.module.Tables.mapper.TablesMapper;
import com.ltj.mybatis.module.Tables.po.Tables;
import com.ltj.mybatis.module.Tables.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TablesServiceImpl implements TablesService {

	@Autowired
	private TablesMapper tablesMapper;
	@Autowired
	private ColumnsMapper columnsMapper;

	private static Map<String,String> javaTypeMap;

	private static Map<String,String> jdbcTypeMap;

	static {
		javaTypeMap = new HashMap<>();
		jdbcTypeMap = new HashMap<>();


		javaTypeMap.put("int","Integer");
		javaTypeMap.put("tinyint","byte");
		javaTypeMap.put("varchar","String");
		javaTypeMap.put("double","double");
		javaTypeMap.put("date","Date");
		javaTypeMap.put("datetime","Date");
		javaTypeMap.put("timestamp","Date");

		jdbcTypeMap.put("int","INTEGER");
		jdbcTypeMap.put("tinyint","TINYINT");
		jdbcTypeMap.put("varchar","VARCHAR");
		jdbcTypeMap.put("double","DOUBLE");
		jdbcTypeMap.put("date","TIMESTAMP");
		jdbcTypeMap.put("datetime","TIMESTAMP");
		jdbcTypeMap.put("timestamp","TIMESTAMP");

	}

	/**
	 * @Description 查询所有记录
	 * @param
	 * @return java.util.List<com.ltj.mybatis.module.Tables.po.Tables>
	 * @author 刘天珺
	 * @Date 11:17 2019-5-29 0029
	 **/
	@Override
	public List<Tables> listTable() {
		return tablesMapper.listTable();
	}

	/**
	 * @Description 查询数据库名
	 * @param
	 * @return java.lang.String
	 * @author 刘天珺
	 * @Date 11:31 2019-5-29 0029
	 **/
	@Override
	public String selectDataBaseName() {
		return tablesMapper.selectDataBaseName();
	}

	/**
	 * @Description 创建实体
	 * @param tablename
	 * @param prefix
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 14:54 2019-5-29 0029
	 **/
	@Override
	public Map<String, Object> createPo(String tablename, String prefix) {
		Map<String,Object> map = new HashMap<>();
		List<ColumnsExtend> columnsList = columnsMapper.listTableColumn(tablename);
		for (ColumnsExtend cole : columnsList) {
			String data_type = cole.getData_type();
			cole.setJdbcType(jdbcTypeMap.get(data_type));
			cole.setJavaType(javaTypeMap.get(data_type));
		}
		map.put("columnsList",columnsList);
		String javaPath = FileManageUtils.getJavaPath();
		FileManageUtils.fillInTemplate("test",map,javaPath+"com.test.test.txt");
		return map;
	}

}
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
import org.springframework.util.StringUtils;

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

	public Map<String, Object> createBean(String tablename, String prefix) {
		Map<String,Object> map = new HashMap<>();
		List<ColumnsExtend> columnsList = columnsMapper.listTableColumn(tablename);
		for (ColumnsExtend cole : columnsList) {
			String data_type = cole.getData_type();
			cole.setJdbcType(jdbcTypeMap.get(data_type));
			cole.setJavaType(javaTypeMap.get(data_type));
		}
		String utablename = FileManageUtils.toUpperCaseFirstOne(tablename);
		map.put("tablename",tablename);
		map.put("utablename",utablename);
		map.put("prefix",prefix);
		map.put("columnsList",columnsList);
		String javaPath = FileManageUtils.getJavaPath();

		createMyMapper(map,javaPath);//创建mymapper.java
		createPo(map,javaPath);//创建实体
		createMapper(map,javaPath);//创建mapper.java
		createMapperXml(map,javaPath);//创建mapper.java
		createService(map,javaPath);//创建mapper.java
		createServiceImpl(map,javaPath);//创建mapper.java


		return map;
	}

	/**
	 * @Description 创建myMapper
	 * @param map
	 * @param javaPath
	 * @return boolean
	 * @author 刘天珺
	 * @Date 16:19 2019-5-30 0030
	 **/
	public boolean createMyMapper(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("myMapper", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/framework/util/";

		//路径拼接
		return FileManageUtils.createFile(path,"MyMapper.java",data);
	}


	/**
	 * @Description 创建实体
	 * @param map
	 * @param javaPath
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 14:54 2019-5-29 0029
	 **/
	public boolean createPo(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("pojo", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/module/"+ map.get("utablename") +"/po/";
		//路径拼接
		return FileManageUtils.createFile(path,map.get("utablename")+".java",data);
	}


	/**
	 * @Description 创建mapper文件
	 * @param map
	 * @param javaPath
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 15:42 2019-5-30 0030
	 **/
	public boolean createMapper(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("mapperJava", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/module/"+ map.get("utablename") +"/mapper/";
		//路径拼接
		return FileManageUtils.createFile(path,map.get("utablename")+"Mapper.java",data);
	}

	/**
	 * @Description 创建xml
	 * @param map
	 * @param javaPath
	 * @return boolean
	 * @author 刘天珺
	 * @Date 16:34 2019-5-30 0030
	 **/
	public boolean createMapperXml(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("mypperXml", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/module/"+ map.get("utablename") +"/mapper/";
		//路径拼接
		return FileManageUtils.createFile(path,map.get("utablename")+"Mapper.xml",data);
	}

	/**
	 * @Description 创建service接口
	 * @param map
	 * @param javaPath
	 * @return boolean
	 * @author 刘天珺
	 * @Date 16:42 2019-5-30 0030
	 **/
	public boolean createService(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("service", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/module/"+ map.get("utablename") +"/service/";
		//路径拼接
		return FileManageUtils.createFile(path,map.get("utablename")+"Service.java",data);
	}

	/**
	 * @Description 创建service实现
	 * @param map
	 * @param javaPath
	 * @return boolean
	 * @author 刘天珺
	 * @Date 16:42 2019-5-30 0030
	 **/
	public boolean createServiceImpl(Map map,String javaPath) {
		String data = FileManageUtils.fillInTemplate("serviceImpl", map);
		//包名转路径
		String pprefix = StringUtils.replace((String)map.get("prefix"),".","/");
		//实体路径
		String path = javaPath+pprefix+"/module/"+ map.get("utablename") +"/service/impl/";
		//路径拼接
		return FileManageUtils.createFile(path,map.get("utablename")+"ServiceImpl.java",data);
	}

}
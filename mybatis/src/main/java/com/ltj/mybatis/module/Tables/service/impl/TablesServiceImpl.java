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

import java.io.IOException;
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
		//java类型
		javaTypeMap.put("int","Integer");
		javaTypeMap.put("tinyint","byte");
		javaTypeMap.put("varchar","String");
		javaTypeMap.put("double","double");
		javaTypeMap.put("date","Date");
		javaTypeMap.put("datetime","Date");
		javaTypeMap.put("timestamp","Date");

		//jdbc类型
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
	 * @Description 查询表数据并创建Bean
	 * @param tablename
	 * @param prefix
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 11:22 2019-5-31 0031
	 **/
	public Boolean createBean(String tablename, String prefix, Integer extend) {
		Map<String,Object> map = new HashMap<>();
		List<ColumnsExtend> columnsList = columnsMapper.listTableColumn(tablename);
		for (ColumnsExtend cole : columnsList) {
			String data_type = cole.getData_type();
			cole.setJdbcType(jdbcTypeMap.get(data_type));
			cole.setJavaType(javaTypeMap.get(data_type));
		}
		String tablehump = FileManageUtils.lineToHump(tablename);
		String utablename = FileManageUtils.toUpperCaseFirstOne(tablehump);
		String beanbefor = tablename.substring(tablename.indexOf("_")+1);
		String beanname = FileManageUtils.lineToHump(beanbefor);
		String ubeanname = FileManageUtils.toUpperCaseFirstOne(beanname);

		map.put("tablename",tablename);
		map.put("utablename",utablename);
		map.put("beanname",beanname);
		map.put("ubeanname",ubeanname);
		map.put("prefix",prefix);
		map.put("extend",extend);
		map.put("columnsList",columnsList);
		//创建文件
		return createAll(map);
	}

	/**
	 * @Description 根据数据创建文件
	 * @param map
	 * @return boolean
	 * @author 刘天珺
	 * @Date 09:25 2019-5-31 0031
	 **/
	public boolean createAll(Map map) {
		Integer extend = (Integer) map.get("extend");

		String javaPath = FileManageUtils.getJavaPath();
		String utablename = (String) map.get("utablename");
		String ubeanname = (String) map.get("ubeanname");
		String prefix = (String) map.get("prefix");
		String path = javaPath + StringUtils.replace(prefix,".","/");

		try {
			//创建myMapper
			String myMapperPath = path+"/framework/util/";
			String myMapperData = FileManageUtils.fillInTemplate("myMapper", map);
			FileManageUtils.createFile(myMapperPath,"MyMapper.java",myMapperData);

			//包路径
			String packPath = path+"/module/"+ utablename;

			//创建实体
			String poPath = packPath +"/po/";
			String pojoData = FileManageUtils.fillInTemplate("pojo", map);
			FileManageUtils.createFile(poPath,utablename+".java",pojoData);
			//创建mapper.java文件
			String mapperpath = packPath +"/mapper/";
			String mapperJavaData = FileManageUtils.fillInTemplate("mapperJava", map);
			FileManageUtils.createFile(mapperpath,utablename+"Mapper.java",mapperJavaData);
			//创建mapper.xml文件
			String mapperXmlData = FileManageUtils.fillInTemplate("mypperXml", map);
			FileManageUtils.createFile(mapperpath,utablename+"Mapper.xml",mapperXmlData);
			//创建service.java文件
			String servicePath = packPath +"/service/";
			String serviceData = FileManageUtils.fillInTemplate("service", map);
			FileManageUtils.createFile(servicePath,utablename+"Service.java",serviceData);
			//创建serviceImpl.java文件
			String serviceImplPath = servicePath+"/impl/";
			String serviceImplData = FileManageUtils.fillInTemplate("serviceImpl", map);
			FileManageUtils.createFile(serviceImplPath,utablename+"ServiceImpl.java",serviceImplData);

			if(extend == 1){
				//创建controller
				String controllerPath = path +"/controller/";
				String controllerData = FileManageUtils.fillInTemplate("controller", map);
				FileManageUtils.createFile(controllerPath,ubeanname+"Controller.java",controllerData);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}


	}


}
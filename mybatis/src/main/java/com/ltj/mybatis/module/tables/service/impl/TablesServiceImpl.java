package com.ltj.mybatis.module.tables.service.impl;

import com.ltj.mybatis.common.utils.FileManageUtil;
import com.ltj.mybatis.common.utils.ZipUtil;
import com.ltj.mybatis.module.columns.mapper.ColumnsMapper;
import com.ltj.mybatis.module.columns.po.ColumnsExtend;
import com.ltj.mybatis.module.tables.mapper.TablesMapper;
import com.ltj.mybatis.module.tables.po.Tables;
import com.ltj.mybatis.module.tables.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
		javaTypeMap.put("double","Double");
		javaTypeMap.put("date","Date");
		javaTypeMap.put("datetime","Date");
		javaTypeMap.put("timestamp","Date");
		javaTypeMap.put("text","String");

		//jdbc类型
		jdbcTypeMap.put("int","INTEGER");
		jdbcTypeMap.put("tinyint","TINYINT");
		jdbcTypeMap.put("varchar","VARCHAR");
		jdbcTypeMap.put("double","DOUBLE");
		jdbcTypeMap.put("date","TIMESTAMP");
		jdbcTypeMap.put("datetime","TIMESTAMP");
		jdbcTypeMap.put("timestamp","TIMESTAMP");
		jdbcTypeMap.put("text","LONGVARCHAR");

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
	 * @param map
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 11:22 2019-5-31 0031
	 **/
	public Map<String, Object> createBean(String tablename, Map<String, Object> map) {
		List<ColumnsExtend> columnsList = columnsMapper.listTableColumn(tablename);
		String priCol = "Fid";
		for (ColumnsExtend cole : columnsList) {
			String data_type = cole.getData_type();
			String jdbctype = jdbcTypeMap.get(data_type);
			String javaType = javaTypeMap.get(data_type);
			cole.setJdbcType(jdbctype==null?"VARCHAR":jdbctype);
			cole.setJavaType(javaType==null?"String":javaType);
			if ("PRI".equals(cole.getColumn_key())) {
				priCol = FileManageUtil.toUpperCaseFirstOne(cole.getColumn_name());
			}

		}
		String tablehump = FileManageUtil.lineToHump(tablename);
		String utablename = FileManageUtil.toUpperCaseFirstOne(tablehump);
		String ptablename = FileManageUtil.HeadAndlineToHump(tablename);
		String beanbefor = tablename.substring(tablename.indexOf("_")+1);
		String beanname = FileManageUtil.lineToHump(beanbefor);
		String ubeanname = FileManageUtil.toUpperCaseFirstOne(beanname);

		map.put("tablename",tablename);
		map.put("utablename",utablename);
		map.put("ptablename",ptablename);
		map.put("beanname",beanname);
		map.put("ubeanname",ubeanname);

		map.put("columnsList",columnsList);
		map.put("priCol",priCol);
		//创建文件
		return map;
	}

	/**
	 * @Description 根据表名集合创建数据
	 * @param tablenames
	 * @param prefix
	 * @param extend
	 * @return java.lang.Boolean
	 * @author Liu Tian Jun
	 * @date 13:25 2020-03-04 0004
	 **/
	@Override
	public String createAllBean(String tablenames, String prefix, Integer extend) {
		Map<String,Object> map = new HashMap<>();
		String javaPath = FileManageUtil.getJavaPath();
		String path = javaPath + StringUtils.replace(prefix,".","/");
		map.put("prefix",prefix);
		map.put("extend",extend);
		map.put("path", path);
		String[] tableArr = tablenames.split(",");

		try {
			for (String ta: tableArr) {
				// 生成数据
				createBean(ta, map);
				// 生成文件
				createAll(map);
			}

			if (extend == 1) {
				createExtend(path,map);
			}
			return ZipUtil.zipFile(javaPath);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @Description 创建补充包
	 * @param path
	 * @param map
	 * @return boolean
	 * @author Liu Tian Jun
	 * @date 13:32 2020-03-04 0004
	 **/
	public void createExtend(String path, Map<String, Object> map) throws IOException {
		// 创建 result
		String resultPath = path+"/common/entity/";
		String resultData = FileManageUtil.fillInTemplate("result", map);
		FileManageUtil.createFile(resultPath,"Result.java",resultData);

		// 创建 MybatisPlusConfig
		String pageconfigPath = path+"/common/config/";
		String pageconfigData = FileManageUtil.fillInTemplate("mybatisPlusConfig", map);
		FileManageUtil.createFile(pageconfigPath,"MybatisPlusConfig.java",pageconfigData);

		// 创建 GlobalExceptionController
		String globalExceptionControllerPath = path+"/common/controller/";
		String globalExceptionControllerData = FileManageUtil.fillInTemplate("globalExceptionController", map);
		FileManageUtil.createFile(globalExceptionControllerPath,"GlobalExceptionController.java",globalExceptionControllerData);

		// 创建 basecontroller
		String baseControllerPath = path+"/common/controller/";
		String baseControllerData = FileManageUtil.fillInTemplate("baseController", map);
		FileManageUtil.createFile(baseControllerPath,"BaseController.java",baseControllerData);
	}

	/**
	 * @Description 根据数据创建文件
	 * @param map
	 * @return boolean
	 * @author 刘天珺
	 * @Date 09:25 2019-5-31 0031
	 **/
	public void createAll(Map map) throws IOException {
		Integer extend = (Integer) map.get("extend");
		String utablename = (String) map.get("utablename");
		String ptablename = (String) map.get("ptablename");
		String ubeanname = (String) map.get("ubeanname");
		String path = (String) map.get("path");

		//包路径
		String packPath = path+"/module/"+ ptablename;

		//创建实体
		String poPath = packPath +"/po/";
		String pojoData = FileManageUtil.fillInTemplate("pojo", map);
		FileManageUtil.createFile(poPath,utablename+".java",pojoData);
		//创建mapper.java文件
		String mapperpath = packPath +"/mapper/";
		String mapperJavaData = FileManageUtil.fillInTemplate("mapperJava", map);
		FileManageUtil.createFile(mapperpath,utablename+"Mapper.java",mapperJavaData);
		//创建mapper.xml文件
		String mapperXmlData = FileManageUtil.fillInTemplate("mypperXml", map);
		FileManageUtil.createFile(mapperpath,utablename+"Mapper.xml",mapperXmlData);
		//创建service.java文件
		String servicePath = packPath +"/service/";
		String serviceData = FileManageUtil.fillInTemplate("service", map);
		FileManageUtil.createFile(servicePath,"I"+utablename+"Service.java",serviceData);
		//创建serviceImpl.java文件
		String serviceImplPath = servicePath+"/impl/";
		String serviceImplData = FileManageUtil.fillInTemplate("serviceImpl", map);
		FileManageUtil.createFile(serviceImplPath,utablename+"ServiceImpl.java",serviceImplData);

		if(extend == 1) {
			//创建controller
			String controllerPath = path + "/controller/";
			String controllerData = FileManageUtil.fillInTemplate("controller", map);
			FileManageUtil.createFile(controllerPath, ubeanname + "Controller.java", controllerData);
		}

	}


}

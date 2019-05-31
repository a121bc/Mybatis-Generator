package com.ltj.mybatis.controller;

import com.ltj.mybatis.module.Tables.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Tables")
public class TablesController {

	@Autowired
	private TablesService tablesService;

	/**
	 * @Description 查询所有表
	 * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author 刘天珺
	 * @Date 11:05 2019-5-29 0029
	 **/
	@GetMapping("/")
	public String listTable(Model model){
		model.addAttribute("dataBaseName",tablesService.selectDataBaseName());
		model.addAttribute("tableList",tablesService.listTable());
		return "tables";
    }

    /**
     * @Description 创建单个
     * @param tablenames
     * @param prefix
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author 刘天珺
     * @Date 11:23 2019-5-31 0031
     **/
	@PostMapping("/createBeans")
	@ResponseBody
	public Map<String,Object> createBeans(String tablenames,String prefix){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		if(StringUtils.isEmpty(tablenames)){
			flag =false;
		}else {
			String[] tableArr = tablenames.split(",");
			for (String ta: tableArr) {
				if(flag){
					flag = tablesService.createBean(ta,prefix);
				}
			}
		}

		map.put("flag",flag);
		map.put("message",flag?"创建成功":"创建失败");

		return map;

	}

}
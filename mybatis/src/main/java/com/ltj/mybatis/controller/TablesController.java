package com.ltj.mybatis.controller;

import com.ltj.mybatis.module.Tables.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@GetMapping("/listTable")
	public String listTable(Model model){
		model.addAttribute("dataBaseName",tablesService.selectDataBaseName());
		model.addAttribute("tableList",tablesService.listTable());
		return "tables";
    }

	@GetMapping("/createPo")
	@ResponseBody
	public Map<String,Object> createPo(String tablename,String prefix){
		return tablesService.createPo(tablename,prefix);
	}

}
package com.ltj.mybatis.module.columns.service.impl;
import com.ltj.mybatis.module.columns.mapper.ColumnsMapper;
import com.ltj.mybatis.module.columns.service.ColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ColumnsServiceImpl implements ColumnsService {

	@Autowired
	private ColumnsMapper columnsMapper;


}

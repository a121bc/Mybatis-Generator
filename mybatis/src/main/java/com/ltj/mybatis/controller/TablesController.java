package com.ltj.mybatis.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.ltj.mybatis.common.utils.FileManageUtil;
import com.ltj.mybatis.module.tables.service.TablesService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/Tables")
@AllArgsConstructor
public class TablesController {

    @Autowired
    private TablesService tablesService;

    private final DataSource dataSource;

    /**
     * @Description 查询所有表
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author 刘天珺
     * @Date 11:05 2019-5-29 0029
     **/
    @GetMapping("/")
    @DS("#ds")
    public String listTable(Model model,String ds){
        model.addAttribute("dataBaseName",tablesService.selectDataBaseName());
        model.addAttribute("tableList",tablesService.listTable());
        model.addAttribute("dataSources",((DynamicRoutingDataSource) dataSource).getCurrentDataSources().keySet());
        model.addAttribute("ds",ds);
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
    @DS("#ds")
    public ResponseEntity<byte[]> createBeans(String tablenames, String prefix, Integer extend,String ds){
        if(Objects.isNull(extend)) {
            extend = 0;
        }
        String filename = tablesService.createAllBean(tablenames, prefix, extend);
        File file = new File(filename);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 文件名编码
        // 直接下载
        bodyBuilder.header("Content-Disposition","attachment;filename*="+filename.substring(filename.lastIndexOf(File.separator )+1));
        // 下载成功返回二进制流
        try {
            ResponseEntity<byte[]> body = bodyBuilder.body(FileUtils.readFileToByteArray(file));
            String substring = filename.substring(0, filename.lastIndexOf("src"));
            FileManageUtil.deleteFolder(substring);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
            // 下载失败直接返回错误的请求
            return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
        }


    }

}

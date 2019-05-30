package com.ltj.mybatis.framework.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

/**
 * 描 述 根据模板生成文件
 * 创 建 人 刘天珺
 * 创建时间 2019-5-30 0030 10:30
 */
public class FileManageUtils {

    /**
     * @Description 用数据填充模板
     * @param template
     * @param map
     * @param path
     * @return boolean
     * @author 刘天珺
     * @Date 11:10 2019-5-30 0030
     **/
    public static String fillInTemplate(String template,Map map,String path) {
        Locale locale = Locale.getDefault();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setPrefix("mybatis/src/main/resources/templates/");
        templateResolver.setSuffix(".txt");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCharacterEncoding("utf-8");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        String data = templateEngine.process(template, new Context(locale,map));
        return data ;
    }

    /**
     * @Description 根据路径创建文件
     * @param url
     * @return boolean
     * @author 刘天珺
     * @Date 10:57 2019-5-30 0030
     **/
    public static boolean createFile(String url, String data) {
        Path path = Paths.get(url);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.createFile(path);
            BufferedWriter bfw=Files.newBufferedWriter(path);
            bfw.write(data);
            bfw.flush();
            bfw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description 获取src目录
     * @param
     * @return java.lang.String
     * @author 刘天珺
     * @Date 11:34 2019-5-30 0030
     **/
    public static String getJavaPath() {
        String path = FileManageUtils.class.getClass().getResource("/").getPath();
        String getSrcPath = path.substring(0,path.length()-15)+"src/main/java/";
        return  getSrcPath;
    }




}

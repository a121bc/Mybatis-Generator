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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @return boolean
     * @author 刘天珺
     * @Date 11:10 2019-5-30 0030
     **/
    public static String fillInTemplate(String template,Map map) {
        Locale locale = Locale.getDefault();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setPrefix("mybatis/src/main/resources/templates/");
        templateResolver.setSuffix(".tmp");
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
    public static boolean createFile(String url,String filename, String data) throws IOException {
        Path dirPath = Paths.get(url);
        Path filePath = Paths.get(url+filename);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);
        BufferedWriter bfw=Files.newBufferedWriter(filePath);
        bfw.write(data);
        bfw.flush();
        bfw.close();
        return true;
    }

    /**
     * @Description 获取java目录
     * @param
     * @return java.lang.String
     * @author 刘天珺
     * @Date 11:34 2019-5-30 0030
     **/
    public static String getJavaPath() {
        String path = FileManageUtils.class.getClass().getResource("/").getPath();
        String getSrcPath = path.substring(1,path.indexOf("mybatis")+8)+"src/main/java/";
        return  getSrcPath;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**下划线转驼峰*/
    public static String lineToHump(String str){
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 前缀大写和下划线转驼峰
    public static String HeadAndlineToHump(String str){
        Matcher matcher = Pattern.compile("_(\\w)|^([A-Za-z]+)").matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}

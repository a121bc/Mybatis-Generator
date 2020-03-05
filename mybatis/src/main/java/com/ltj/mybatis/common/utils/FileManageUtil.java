package com.ltj.mybatis.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Map;

/**
 * 描 述 根据模板生成文件
 * 创 建 人 刘天珺
 * 创建时间 2019-5-30 0030 10:30
 */

@Configuration
@Component
public class FileManageUtil {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    public SpringResourceTemplateResolver springTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setTemplateMode(TemplateMode.LEGACYHTML5);
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }

    @Bean
    public SpringResourceTemplateResolver fileTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setSuffix(".tmp");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }


    /**
     * @Description 用数据填充模板
     * @param template
     * @param map
     * @return boolean
     * @author 刘天珺
     * @Date 11:10 2019-5-30 0030
     **/
    public String fillInTemplate(String template,Map map) {
        Locale locale = Locale.getDefault();
//        FileTemplateResolver templateResolver = new FileTemplateResolver();

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(fileTemplateResolver());
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

    // 删除文件夹
    public static void deleteFolder(String Foleder) throws IOException {
        Path start = Paths.get(Foleder);
        if (Files.notExists(start)) {
            System.out.println("文件夹不存在！");
        }

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override //构成了一个内部类
            // 处理文件
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            // 再处理目录
            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                    throws IOException {
                if (e == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw e;
                }
            }
        });
        System.out.println("删除成功！");
    }

    /**
     * @Description 获取java目录
     * @param
     * @return java.lang.String
     * @author 刘天珺
     * @Date 11:34 2019-5-30 0030
     **/
    public static String getJavaPath() {
        String path = FileManageUtil.class.getResource("/").getPath();
//        String getSrcPath = path.substring(1,path.indexOf("mybatis")+8)+"src/main/java/";
        String getSrcPath = path+ RandomStringUtils.randomAlphanumeric(10)+"/src/main/java/";
        return  getSrcPath;
    }




}

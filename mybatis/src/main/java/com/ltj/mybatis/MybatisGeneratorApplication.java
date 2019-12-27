package com.ltj.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.net.InetAddress;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class,scanBasePackages = {"com.ltj"})
@MapperScan(basePackages = "com.ltj.mybatis.module.*.mapper")
@Slf4j
public class MybatisGeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MybatisGeneratorApplication.class, args);
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String url = "http://"+ localHost.getHostAddress() +":"+ ctx.getEnvironment().getProperty("server.port") +"/Tables/";
            log.info("请打开地址: {}", url);

            // 获取操作系统的名字
            String osName = System.getProperty("os.name", "");
            if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("cmd   /c   start   " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

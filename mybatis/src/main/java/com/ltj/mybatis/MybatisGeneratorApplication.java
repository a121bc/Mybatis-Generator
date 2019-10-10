package com.ltj.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class,scanBasePackages = {"com.ltj"})
@MapperScan(basePackages = "com.ltj.mybatis.module.*.mapper")
@Slf4j
public class MybatisGeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MybatisGeneratorApplication.class, args);
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            log.info("请打开地址 http://"+ localHost.getHostAddress() +":"+ ctx.getEnvironment().getProperty("server.port") +"/Tables/");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}

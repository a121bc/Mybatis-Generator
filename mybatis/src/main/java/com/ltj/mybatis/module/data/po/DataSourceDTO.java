package com.ltj.mybatis.module.data.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author TaoYu
 * @date 2020/1/27
 */
@Data
public class DataSourceDTO {

  @NotBlank
  /* 连接池名称 */
  private String pollName;

  @NotBlank
  /* JDBC driver */
  private String driverClassName;

  @NotBlank
  /* url地址 */
  private String url;

  @NotBlank
  /* 用户名 */
  private String username;

  /* 密码 */
  private String password;

}

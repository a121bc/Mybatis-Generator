package com.ltj.mybatis.common.exception;

/**
 * @author: Liu Tian Jun
 * @Date: 2019-11-09 22:30
 * @describe： 增删改查异常
 * @version: 1.0
 */

public class CrudException extends RuntimeException {

    public CrudException(String message) {
        super(message);
    }
}

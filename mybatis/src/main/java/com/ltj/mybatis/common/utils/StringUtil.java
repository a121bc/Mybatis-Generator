package com.ltj.mybatis.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @describe： TODO
 * @author: Liu Tian Jun
 * @Date: 2020-03-05 11:05
 * @version: 1.0
 */
public class StringUtil {

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
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

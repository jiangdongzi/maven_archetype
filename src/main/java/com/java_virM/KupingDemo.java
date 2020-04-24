package com.java_virM;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;


/**
 * Created by chy on 2018/10/23.
 * 网关验签有一个时间限制，若参数中的时间戳与当前时间戳之间的相差较大，则会返回请求过期
 * 1. 将除了sign和appKey以外的，一级请求参数（不包含对象数组等）**根据key值按照字母表的顺序(a~z)排序**，将其对应参数值（仅value，不要key）拼接到一起，得到字符串A
 * 2. 拼接 appKey+A+appSecret ，得到字符串B
 * 3. 将字符串B 进行**urlencode(utf-8)编码**，结果转化为大写，得到字符串C。*除了字符、数字、下划线全部编码*
 * 4. 将字符串C 进行**MD5编码**，结果转化为大写，得到字符串D
 *
 * 注意：排序规则只针对**JSON的一级属性**，对于嵌套属性(属性值是对象或者数组) 不加入字符串A的拼接。
 *
 */
public class KupingDemo
{

    public static Boolean verify(Map metaPlain, String appSecret, String sign) {
        metaPlain.remove("sign");
        return MD5Sign(metaPlain, appSecret).equalsIgnoreCase(sign);
    }

    private static String sortStr(Map<String, String> maps) {
        List<String> paramNames = new ArrayList<>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            paramNames.add(entry.getKey());
        }
        Collections.sort(paramNames);
        StringBuilder paramStr = new StringBuilder();
        for (String paramName : paramNames) {
            Object value = maps.get(paramName);
            paramStr.append(value);
        }
        return paramStr.toString();
    }

    /**
     * @param metaPlain 原始数据
     * @param appSecret OtOSaaS提供
     * @return
     */
    public static String MD5Sign(Map metaPlain, String appSecret) {
        String appKey = (String) metaPlain.remove("appKey");
        String A = sortStr(metaPlain);
        String erPlain = appKey + A + appSecret;
        String sign = DigestUtils.md5Hex(erPlain.toUpperCase()).toUpperCase();
        metaPlain.put("appKey",appKey);
        return sign;
    }

    /**
     * 获取精确到秒的时间戳
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }



    public static void main(String[] args) {
        String appKey = "6065316726";
        String appSecret = "MRkPA8QPMgfeyagWROHxv0pgOUoUviG0";
        int timestamp = getSecondTimestampTwo(new Date());
        Map<String, Object> map = new LinkedHashMap<>(10);;
        map.put("app_id",449);
        map.put("campaign_id",2897);
        map.put("timestamp", timestamp);
        map.put("appKey",appKey);
        String sign = MD5Sign(map, appSecret);
        map.put("sign",sign);
        System.out.println("sign: "+sign);
        System.out.println("timestamp: "+timestamp);
    }




}


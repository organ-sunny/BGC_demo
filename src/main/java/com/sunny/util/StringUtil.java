package com.sunny.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class StringUtil {

    public static boolean isEmpty(String data) {
        return data == null || data.equals("");
    }

    public static Map<String, Object> toMap(String data) {
        return JSONObject.parseObject(data);
    }
}
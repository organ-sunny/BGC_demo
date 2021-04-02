package com.sunny.util;

import com.alibaba.fastjson.JSON;
import com.sunny.dto.ApiTestCaseDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetRequestUrl {

    public static String getRequestUrl(String baseUrl, String apiCaseRequestParam) {
        if (StringUtil.isEmpty(apiCaseRequestParam)) {
            return baseUrl;
        }

        List<?> list = JSON.parseObject(apiCaseRequestParam, List.class);
        System.out.println(list);

        String requestUrl = baseUrl;
        for (Object o : list) {
            if (o instanceof Map) {
                ApiTestCaseDTO.analysisApiCaseRequestParam analysisApiCaseRequestParam = JSON.parseObject(o.toString(), ApiTestCaseDTO.analysisApiCaseRequestParam.class);

                // 请求入参类型为拼在url后面的键值对
                if ("params".equals(analysisApiCaseRequestParam.getType())) {

                    Map<String, Object> contentMap = analysisApiCaseRequestParam.getContent();
                    for (String key : contentMap.keySet()) {
                        requestUrl = requestUrl + "?" + key + "=" + contentMap.get(key);
                    }
                }

                // 请求入参类型为body
                if ("body".equals(analysisApiCaseRequestParam.getType())) {
                    requestUrl = baseUrl;
                }
            }
        }
        return requestUrl;
    }

    @SuppressWarnings("unchecked")
    public static String getRequestUrl(String content) {
        String result = "";

        if (StringUtil.isEmpty(content)) {
            return result;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);
        if (map.size() != 0) {
            result = result + "?";
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                result = result + key + "=" + map.get(key) + "&";
            }
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

}

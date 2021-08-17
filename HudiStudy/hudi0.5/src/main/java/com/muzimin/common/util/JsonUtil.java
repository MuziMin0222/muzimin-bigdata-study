package com.muzimin.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : 李煌民
 * @date : 2021-04-28 14:27
 **/
public class JsonUtil {
    public static JSONObject getJsonData(String data) {
        try {
            return JSONObject.parseObject(data);
        } catch (Exception e) {
            return null;
        }
    }
}

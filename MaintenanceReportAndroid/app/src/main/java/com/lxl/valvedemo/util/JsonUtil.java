package com.lxl.valvedemo.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by yanglei on 2018/3/13.
 */

public class JsonUtil {

    public static JSONObject string2JSON(String str) {
        try {
            JSONObject jsonObject = JSON.parseObject(str);
            return jsonObject;
        } catch (Exception e) {
            Log.i("lxltest", "解析JSON失败：" + str);
            return new JSONObject();
        }
    }


}

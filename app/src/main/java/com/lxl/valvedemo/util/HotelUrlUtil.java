package com.lxl.valvedemo.util;

import java.util.HashMap;

/**
 * Created by yanglei on 2018/2/2.
 */

public class HotelUrlUtil {

    public static String urlMap2String(HashMap<String, Object> urlMap) {
        StringBuilder builder = new StringBuilder();
        for (String key : urlMap.keySet()) {
            builder.append("&");
            builder.append(key);
            builder.append("=");
            builder.append(urlMap.get(key));
        }
        return builder.toString();
    }

    public static String urlMap2String(String base, HashMap<String, Object> urlMap) {
        String s = urlMap2String(urlMap);
        return base + "?" + s;
    }

}

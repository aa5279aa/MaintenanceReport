package com.lxl.maintenance.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiangleiliu on 2017/5/8.
 */
public class MaintenanceConfig {

    public static String Save_Path = "C://img";

    public static Map<String, String> getIdAndNameMap() {
        Map<String, String> map = new HashMap<>();
        map.put("801", "wxd_ybzdhjltx");//维修队仪表自动化计量通讯专业检查表
        map.put("802", "wxd_sb_weihu");//维修队设备专业预防性维护检查表
        map.put("811", "zyq_ybzdhjltx");//作业区仪表计量通讯专业表
        map.put("812", "zyq_dq");//作业区电气专业巡检检查表
        map.put("813", "zyq_gd");//作业区管道专业巡检检查表
        map.put("814", "zyq_sb_xunjian");//作业区设备专业巡检检查表
        map.put("815", "zyq_sb_weihu");//作业区设备专业预防性维护表
        return map;
    }

    public static Set<String> getNoNeedKey() {
        Set<String> set = new HashSet<>();
        set.add("xuhao");
        set.add("jiancharen");
        set.add("jianchariqi");
        set.add("zuoyequ");
        set.add("zhanchang");
        set.add("location");
        set.add("lat_long");
        set.add("imgpath");
        return set;
    }
}

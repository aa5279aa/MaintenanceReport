package com.lxl.maintenance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxl.maintenance.config.MaintenanceConfig;
import com.lxl.maintenance.dao.MaintenanceDao;
import com.lxl.maintenance.dao.MaintenanceDaoImpl;
import com.lxl.maintenance.model.RecordModel;
import com.sun.org.apache.regexp.internal.RE;
import sun.applet.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceService {
    MaintenanceDao dao;

    public MaintenanceService() {
        dao = new MaintenanceDaoImpl();
    }

    public boolean actionInsertDB(String type, String area, String station, String checker, String date, String location, String lat_long, String imgpath, String data) {

        RecordModel recordModel = new RecordModel();
        recordModel.type = type;
        recordModel.area = area;
        recordModel.station = station;
        recordModel.checker = checker;
        recordModel.date = date;
        recordModel.location = location;
        recordModel.lat_long = lat_long;
        recordModel.imgpath = imgpath;
        JSONArray dataArray = JSON.parseArray(data);
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject jsonObject = dataArray.getJSONObject(i);
            String key = jsonObject.getString("key");
            String value = jsonObject.getString("value");
            String desc = jsonObject.getString("desc");
            recordModel.data.put(key, value);
        }
        recordModel.data.put("jiancharen", recordModel.checker);
        recordModel.data.put("jianchariqi", recordModel.date);
        recordModel.data.put("zuoyequ", recordModel.area);
        recordModel.data.put("zhanchang", recordModel.station);
        recordModel.data.put("location", recordModel.location);
        recordModel.data.put("lat_long", recordModel.lat_long);
        recordModel.data.put("imgpath", recordModel.imgpath);
        //根据不同的type插入不同的表格
        switch (type) {
            case "801":
                //维修队仪表自动化计量通讯专业检查表
                //wxd_ybzdhjltx
                return dao.insertWxdYbzdhjltx(recordModel) > 0;
            case "802":
                //维修队设备专业预防性维护检查表
                //wxd_sb_weihu
                return dao.insertWxdSbWeihu(recordModel) > 0;
            case "811":
                return dao.insertZyqYbzdhjltx(recordModel) > 0;
            case "812":
                return dao.insertZyqDq(recordModel) > 0;
            case "813":
                return dao.insertZyqGq(recordModel) > 0;
            case "814":
                return dao.insertZyqSbXunjian(recordModel) > 0;
            case "815":
                return dao.insertZyqSbWeihu(recordModel) > 0;
        }
        return false;
    }


    public String actionselectDB(String type, String area, String station, String checker, String date) {
        RecordModel recordModel = new RecordModel();
        recordModel.type = type;
        recordModel.area = area;
        recordModel.station = station;
        recordModel.checker = checker;
        recordModel.date = date;

        //根据不同的type插入不同的表格
        Map<String, String> idAndNameMap = MaintenanceConfig.getIdAndNameMap();
        String tableName = idAndNameMap.get(recordModel.type);
        List<HashMap<String, String>> resultList = dao.selectDataFromDbByTable(recordModel, tableName);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < resultList.size(); i++) {
            HashMap<String, String> resultMap = resultList.get(i);
            JSONArray jsonArray1 = new JSONArray();
            for (String key : resultMap.keySet()) {
                JSONObject itemJSONObj = new JSONObject();
                itemJSONObj.put("key", key);
                itemJSONObj.put("value", resultMap.get(key));
                jsonArray1.add(itemJSONObj);
            }
            jsonArray.add(jsonArray1);
        }
        return jsonArray.toJSONString();
    }


}

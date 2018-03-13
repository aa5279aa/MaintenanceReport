package com.lxl.maintenance.model;

import java.util.HashMap;

public class RecordModel {
    //    String type, String area, String station, String checker, String date, String location, String lat_long, String imgpath, String data
    public String type;
    public String area;
    public String station;
    public String checker;
    public String date;
    public String location;
    public String lat_long;
    public String imgpath;

    //其余信息，转换成map形式
    public HashMap<String, String> data = new HashMap<>();

}

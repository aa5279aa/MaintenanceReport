package com.lxl.valvedemo.model.viewmodel;

import java.io.Serializable;

/**
 * Created by xiangleiliu on 2017/9/29.
 */
public class LocationRecordModel implements Serializable {

    public double longitude = 0.0;
    public double latitude = 0.0;
    public String addressText = "";//位置名称
    public String cityText = "";//城市名称
    public String dataText = "";//位置名称

    public int x;
    public int y;

    public LocationRecordModel() {

    }

    public LocationRecordModel(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


}

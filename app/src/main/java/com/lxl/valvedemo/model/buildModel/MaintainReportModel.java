package com.lxl.valvedemo.model.buildModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 * 设备检查与维护保养记录 填表
 * type=1
 * 维修队板块-机械设备专业-阀门维护保养记录
 *
 */

public class MaintainReportModel {
    final public String workAreaName = "作业区";
    public String workAreaText = "";

    final public String stationName = "场站";
    public String stationText = "";

    final public String checkerName = "维护保养人员";
    public String checkerText = "";

    final public String dateName = "日期";
    public String dateText = "";

    public List<MaintainReportItemModel> maintainList = new ArrayList<>();//
}

package com.lxl.valvedemo.model.buildModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 * 保养填表
 * type=1
 */

public class MaintainReportModel {
    public String tableName;
    public String workAreaName = "作业区";
    public String workAreaText = "";

    public String stationName = "场站";
    public String stationText = "";

    public String maintainName = "维护保养人员";
    public String maintainText = "";

    public String dateName = "日期";
    public String dateText = "";

    public List<MaintainReportItemModel> maintainList = new ArrayList<>();
}

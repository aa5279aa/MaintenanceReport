package com.lxl.valvedemo.model.buildModel.inspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class InspectionReportModel {
    public String tableName = "集中巡检场站检查表（仪表自动化、计量、通信部分）";

    public final String workAreaName = "作业区";
    public String workAreaText = "";
    public final String stationName = "场站";
    public String stationText = "";
    public final String checkerName = "检查人";
    public String checkerText = "";
    public final String dateName = "日期";
    public String dateText = "";
    public List<InspectionReportTypeModel> typeModelList = new ArrayList<>();
}

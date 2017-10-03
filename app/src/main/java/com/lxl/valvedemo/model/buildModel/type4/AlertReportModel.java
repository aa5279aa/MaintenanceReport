package com.lxl.valvedemo.model.buildModel.type4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class AlertReportModel {

    public String stationName = "输气站";
    public String stationText = "";

    public String standardName = " 标准气浓度（%LEL）：";
    public String standardText = " 标准气浓度（%LEL）：";

    public String checkerName = "校对人";
    public String checkerText = "";

    public String checkDateName = "校对日期";
    public String checkDateText = "";


    public String desc = "注：1、允许示值误差±5%LEL；2、请将存在问题的报警器数量统计后上报安全科。";

    public final String positionName = "序号";
    public final String installPositionName = "安装位置";
    public final String showValueName = "显示值（%LEL）";
    public List<AlertReportItemModel> reportItemModelList = new ArrayList<>();

    public static class AlertReportItemModel {
        public int position = 0;
        public String installPosition = "";
        public String showValue = "";
    }
}

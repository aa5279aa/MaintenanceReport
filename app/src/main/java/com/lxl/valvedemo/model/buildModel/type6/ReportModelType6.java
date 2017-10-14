package com.lxl.valvedemo.model.buildModel.type6;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class ReportModelType6 {
    public String tableName = "维修队计量专业季度维护保养检查表";

    public final String workAreaName = "作业区：";
    public String workAreaText = "";
    public final String stationName = "场站：";
    public String stationText = "";
    public final String checkerName = "维护保养人员：";
    public String checkerText = "";
    public final String dateName = "日期：";
    public String dateText = "";
    public final String confirmSignName = "确认签字：";//
    public String confirmSign = "";
    public final String confirmName = "确认人：";//
    public String confirmText = "";


    public final String indexName = "序号";
    public final String checkInfoName = "维护保养项目和内容";
    public final String equipmentDescName = "设备情况";


    public List<ReportModelType6.ReportModelType6SubModel> reportItemModelList = new ArrayList<>();

    public static class ReportModelType6SubModel {
        public int index = 0;
        public String projectText = "";//维护保养项目和内容
        public List<String> checkInfoList = new ArrayList<>();
        public String checkDesc = "";//设备情况
    }
}

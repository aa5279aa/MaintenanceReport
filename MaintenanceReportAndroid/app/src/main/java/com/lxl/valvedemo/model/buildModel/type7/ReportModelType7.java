package com.lxl.valvedemo.model.buildModel.type7;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class ReportModelType7 {
    public String tableName = "电气设备季度维护修理大表";
    public final String workAreaName = "作业区：";
    public String workAreaText = "";
    public final String stationName = "场站：";
    public String stationText = "";
    public final String checkName = "检修人员：";
    public String checkText = "";
    public final String confirmName = "确认人：";
    public String confirmText = "";
    public final String dateName = "日期：";
    public String dateText = "";

    public final String descName = "备注";
    public String descText = "";


    public final String indexName = "序号";
    public final String checkInfoName = "电气设备定期维护项目";
    public final String equipmentDescName = "检查情况";

    public List<ReportModelType7.ReportModelType7SubModel> reportItemModelList = new ArrayList<>();

    //一、济柴燃气发电机组
    public static class ReportModelType7SubModel {
        public String indexStr = "";
        public String projectText = "";
        public List<ReportModelType7SubSubModel> checkInfoSubList = new ArrayList<>();
        public List<ReportModelType7SubItemModel> subItemModelList = new ArrayList<>();
    }

    //发动机
    public static class ReportModelType7SubSubModel {
        public String indexStr = "";
        public String projectText = "";//发动机
        public List<ReportModelType7SubItemModel> checkInfoSubList = new ArrayList<>();
        public String desc = "";//检查情况
    }

    //发动机
    public static class ReportModelType7SubItemModel {
        public String checkInfo = "";
        public String checkDescText = "";
    }
}

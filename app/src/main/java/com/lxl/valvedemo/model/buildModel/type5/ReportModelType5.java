package com.lxl.valvedemo.model.buildModel.type5;

import com.lxl.valvedemo.model.buildModel.type4.AlertReportModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class ReportModelType5 {
    public String tableName = "维修队计量专业季度维护保养检查表";

    public final String stationName = "场站";
    public String stationText = "";
    public final String owerName = "场站负责人";
    public String owerText = "";
    public final String dateName = "日期";
    public String dateText = "";
    public final String checkerName = "检查人";
    public String checkerText = "";


    public final String indexName = "序号";
    public final String projectName = "项目";
    public final String checkInfoName = "检查内容";
    public final String checkResultName = "检查结果";
    public final String checkDescName = "备注";

    public List<ReportModelType5.ReportModelType5SubModel> reportItemModelList = new ArrayList<>();

    public static class ReportModelType5SubModel {
        public int indexStart = 0;
        public int indexEnd = 0;
        public String projectText = "";
        public List<ReportModelType5CheckItem> checkInfoList = new ArrayList<>();
    }

    public static class ReportModelType5CheckItem {
        public int index = 0;
        public String checkInfo = "";
        public String checkResult = "";
        public String checkDesc = "";
    }
}

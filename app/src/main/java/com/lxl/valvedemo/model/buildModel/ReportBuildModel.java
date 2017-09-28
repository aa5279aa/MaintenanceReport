package com.lxl.valvedemo.model.buildModel;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class ReportBuildModel {

    public static final int BUILD_TYPE_ONE = 1;
    public static final int BUILD_TYPE_TWO = 2;
    public static final int BUILD_TYPE_THREE = 31;

    public int buildType = 1;
    public String tableName = "";
    public String dateStr = "";
    public MaintainReportModel maintainReportModel = new MaintainReportModel();

}

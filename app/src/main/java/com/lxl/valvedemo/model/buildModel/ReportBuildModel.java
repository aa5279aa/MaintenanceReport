package com.lxl.valvedemo.model.buildModel;

import com.lxl.valvedemo.model.buildModel.type2.InspectionReportModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;

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
    public MaintainReportModel maintainReportModel = new MaintainReportModel();//type1
    public InspectionReportModel inspectionReportModel = new InspectionReportModel();//type2
    public MaintainReportByAreaModel maintainReportByArea = new MaintainReportByAreaModel();//type3

}

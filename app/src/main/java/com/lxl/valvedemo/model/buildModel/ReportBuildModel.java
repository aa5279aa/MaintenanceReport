package com.lxl.valvedemo.model.buildModel;

import com.lxl.valvedemo.model.buildModel.type2.InspectionReportModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.model.buildModel.type4.AlertReportModel;
import com.lxl.valvedemo.model.buildModel.type5.ReportModelType5;
import com.lxl.valvedemo.model.buildModel.type6.ReportModelType6;
import com.lxl.valvedemo.model.buildModel.type7.ReportModelType7;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class ReportBuildModel {

    public static final int BUILD_TYPE_ONE = 1;
    public static final int BUILD_TYPE_TWO = 2;
    public static final int BUILD_TYPE_THREE = 3;
    public static final int BUILD_TYPE_FOUR = 4;
    public static final int BUILD_TYPE_FIVE = 5;
    public static final int BUILD_TYPE_SIX = 6;
    public static final int BUILD_TYPE_SEVEN = 7;

    public int buildType = 1;
    public String tableName = "";
    public String dateStr = "";
    public MaintainReportModel maintainReportModel = new MaintainReportModel();//type1
    public InspectionReportModel inspectionReportModel = new InspectionReportModel();//type2
    public MaintainReportByAreaModel maintainReportByArea = new MaintainReportByAreaModel();//type3
    public AlertReportModel alertReportModel = new AlertReportModel();//type4
    public ReportModelType5 reportModelType5 = new ReportModelType5();//type5
    public ReportModelType6 alertReportMode6 = new ReportModelType6();//type6
    public ReportModelType7 alertReportMode7 = new ReportModelType7();//type7
}

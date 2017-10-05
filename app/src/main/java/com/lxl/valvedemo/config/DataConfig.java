package com.lxl.valvedemo.config;

import java.util.Arrays;
import java.util.List;

public class DataConfig {

    public static void initMeterReport() {
//        ReportBuildModel model = new ReportBuildModel();
//        model.buildType = ReportBuildModel.BUILD_TYPE_TWO;
////        model.tableName = "";
//        model.inspectionReportModel.tableName = "集中巡检场站检查表（仪表自动化、计量、通信部分）";
//
//        InspectionReportTypeModel typeModel1 = new InspectionReportTypeModel();//仪表自动化设备设施
//        InspectionReportTypeModel typeModel2 = new InspectionReportTypeModel();//计量设备
//        InspectionReportTypeModel typeModel3 = new InspectionReportTypeModel();// 通信设备
//        model.inspectionReportModel.typeModelList.add(typeModel1);
//        model.inspectionReportModel.typeModelList.add(typeModel2);
//        model.inspectionReportModel.typeModelList.add(typeModel3);
//
//        typeModel1.typeName = "仪表自动化设备设施";
//        typeModel1.firstColumn = 3;
//        typeModel1.lastColumn = 67;
//        InspectionReportSubTypeModel subTypeModel=new InspectionReportSubTypeModel();
//        subTypeModel.subTypeName = "压力表";
//        双金属温度计
//                压力变送器
//
//        差压变送器
//                温度变送器
//
//
    }

    public static boolean isType7Start(String str) {
        String[] strs = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        List<String> strings = Arrays.asList(strs);
        return strings.contains(str);
    }

    public static boolean isType7SubStart(String str) {
        String[] strs = new String[]{"1.1", "1.2", "1.3", "8.1", "8.2"};
        List<String> strings = Arrays.asList(strs);
        return strings.contains(str);
    }

}

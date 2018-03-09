package com.lxl.valvedemo.config;

import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;

import java.util.ArrayList;
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

    public static List<LocationRecordModel> getRecordList() {
        List<LocationRecordModel> list = new ArrayList<>();
//        list.add(new LocationRecordModel(35.441273,119.449116));
//        list.add(new LocationRecordModel(35.441273,119.449116));
//        list.add(new LocationRecordModel(35.441273,119.449116));
//        list.add(new LocationRecordModel(35.441273,119.449116));
//        list.add(new LocationRecordModel(35.441377,119.449297));
//        list.add(new LocationRecordModel(35.442089,119.450083));
//        list.add(new LocationRecordModel(35.442092,119.450909));
//        list.add(new LocationRecordModel(35.439468,119.448356));
//        list.add(new LocationRecordModel(35.4421,119.435143));//
//        list.add(new LocationRecordModel(35.441697,119.449702));
//        list.add(new LocationRecordModel(35.442134,119.450395));
//        list.add(new LocationRecordModel(35.442134,119.450909));
        list.add(new LocationRecordModel(34.836237, 117.493237));
        list.add(new LocationRecordModel(34.836025, 117.491838));
        list.add(new LocationRecordModel(34.835968, 117.49113));
        list.add(new LocationRecordModel(34.836091, 117.492776));
        list.add(new LocationRecordModel(34.835959, 117.49101));
        list.add(new LocationRecordModel(34.835949, 117.490674));
        list.add(new LocationRecordModel(34.836101, 117.492791));
        list.add(new LocationRecordModel(34.836074, 117.492732));
        list.add(new LocationRecordModel(34.836082, 117.492732));
        list.add(new LocationRecordModel(34.836076, 117.492745));
        list.add(new LocationRecordModel(34.837446, 117.490139));
        list.add(new LocationRecordModel(34.838116, 117.489894));
        list.add(new LocationRecordModel(34.834255, 117.485855));
        return list;
    }

}

package com.lxl.valvedemo.config;

import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static HashMap<String, List<String>> getAreaStationMap() {
        HashMap<String, List<String>> map = new HashMap<>();

        List<String> list1 = new ArrayList<>();
        list1.add("安平站");
        list1.add("衡水站");

        List<String> list2 = new ArrayList<>();
        list2.add("德分站");
        list2.add("德末站");
        list2.add("武末站");

        List<String> list3 = new ArrayList<>();
        list3.add("泰安压气站");
        list3.add("泰安分输站");
        list3.add("济南站");
        list3.add("肥城站");

        List<String> list4 = new ArrayList<>();
        list4.add("曲阜站");
        list4.add("济末站");
        list4.add("小雪站");

        List<String> list5 = new ArrayList<>();
        list5.add("菏泽站");
        list5.add("济宁站");
        list5.add("巨野站");

        List<String> list6 = new ArrayList<>();
        list6.add("枣庄站");
        list6.add("临沂站");
        list6.add("滕州站站");

        List<String> list7 = new ArrayList<>();
        list7.add("濮阳站");
        list7.add("北杨集站");
        list7.add("沧州站");


        map.put("衡水作业区", list1);
        map.put("武城作业区", list2);
        map.put("泰安作业区", list3);
        map.put("曲阜作业区", list4);
        map.put("菏泽作业区", list5);
        map.put("枣庄作业区", list6);
        map.put("中沧作业区", list7);


        return map;
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

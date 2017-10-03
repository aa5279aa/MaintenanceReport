package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 * 中原输油气分公司仪表自动化设备季度维护记录表（冀宁线）
 * 中原输油气分公司仪表自动化设备季度维护记录表（平泰线）
 */

public class MaintainReportByAreaModel {
    /**
     * 中原输油气分公司仪表自动化设备季度维护记录表（冀宁线）
     * 中原输油气分公司仪表自动化设备季度维护记录表（平泰线）
     */
    public String tableName = "中原输油气分公司仪表自动化设备季度维护记录表（冀宁线）";

    public final String stationName = "场站";
    public String stationText = "";
    public final String dateName = "测试日期";
    public String dateText = "";
    public final String productName = "生产科";
    public String productText = "";
    public final String checkerName = "测试人";
    public String checkerText = "";

    public final String colum1Name = "检查项目";
    public final String colum2Name = "检查内容";
    public final String colum3Name = "检查结论（如有故障详细记录故障状态）";

    public final String meterName = "仪表设备";
    public String meterPressureName = "压力表";
    public String meterTemperatureName = "温度表";
    public String meterPressureTransferName = "压力变送器";
    public String meterTemperatureTransferName = "温度变送器";
    public String meterIndicatorLightName = "清管球通过指示灯";

    public String meterPressureDesc = "压力表指示正常，安装牢固无漏气，耐震压力表阻";
    public String meterTemperatureDesc = "温度表指示正常，连接牢固无松动";
    public String meterPressureTransferDesc = "压力变送器外观正常，连接牢固无松动，无漏气，密封良好无进水，显示屏工作正常，SCADA系统显示数值正确";
    public String meterTemperatureTransferDesc = "温度变送器外观正常，连接牢固无松动，密封良好无进水，显示屏工作正常，SCADA系统显示数值正确";
    public String meterIndicatorLightDesc = "外观正常，连接牢固无松动，密封良好无进水，显示屏工作正常，测试功能";

    public String meterPressureText = "";
    public String meterTemperatureText = "";
    public String meterPressureTransferText = "";
    public String meterTemperatureTransferText = "";
    public String meterIndicatorLightText = "";

    public List<MaintainReportBySCADA> scadaList = new ArrayList<>();
    public String maintainDescName = "维护结论：";
    public String maintainDescText = "";
    public String maintainOtherName = "其他事项：";
    public String maintainOtherText = "";
    public String stationConfirmName = "场站确认结论：";
    public String stationConfirmText = "";
}

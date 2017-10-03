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

    public String maintainDescName = "维护结论：";
    public String maintainDescText = "";
    public String maintainOtherName = "其他事项：";
    public String maintainOtherText = "";
    public String stationConfirmName = "场站确认结论：";
    public String stationConfirmText = "";

    public List<MaintainReportBySCADA> scadaList = new ArrayList<>();
}

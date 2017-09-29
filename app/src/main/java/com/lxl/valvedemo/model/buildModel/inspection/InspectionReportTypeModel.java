package com.lxl.valvedemo.model.buildModel.inspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class InspectionReportTypeModel {
    public String typeName = "仪表自动化设备设施";
    public int firstRow = 0;
    public int lastRow = 0;
    public List<InspectionReportSubTypeModel> subTypeModelList = new ArrayList<>();
}

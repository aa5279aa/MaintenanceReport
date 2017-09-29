package com.lxl.valvedemo.model.buildModel.inspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class InspectionReportSubTypeModel {
    public String subTypeName = "压力表";
    public List<InspectionReportCellModel> cellModelList = new ArrayList<>();
    public int firstRow = 0;
    public int lastRow = 0;


    public static class InspectionReportCellModel {
        public String requireDesc = "现场标准/要求";
        public String checkRecord = "检查记录";
        public String checkDesc = "备注";
    }
}



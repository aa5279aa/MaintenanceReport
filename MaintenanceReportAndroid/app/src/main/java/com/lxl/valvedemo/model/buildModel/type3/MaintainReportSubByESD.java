package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportSubByESD extends MaintainReportSubByBase {
    public String esdTitle = "";
    public String cpuColumName1 = "";//记录指示灯
    public String cpuColumName2 = "";//F30
    public String cpuColumName3 = "";//IO1
    public String cpuColumName4 = "";//IO2
    public List<MaintainReportByESDItemValue> esdItemValueList = new ArrayList<>();
    public String subDesc = "";//描述

    public MaintainReportSubByESD(int position) {
        super(position);
    }


    public static class MaintainReportByESDItemValue {
        public String rowTitle = "";
        public String rowText1 = "";
        public String rowText2 = "";
        public String rowText3 = "";
    }

}

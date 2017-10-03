package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportSubByCPU extends MaintainReportSubByBase{
    public String cpuTitle = "记录以下指示灯状态";
    public String cpuColumName1 = "";//A机架 PLC-A
    public String cpuColumName2 = "";//B机架 PLC-B
    public String cpuColumName3 = "";//ESD-A
    public String cpuColumName4 = "";//ESD-B
    public List<MaintainReportByCPUItemValue> subItemList = new ArrayList<>();
    public int topRow;
    public int lastRow;

    public MaintainReportSubByCPU(int position) {
        super(position);
    }

    public static class MaintainReportByCPUItemValue {
        public String subItemName = "";//Pew OK

        public String subItemValueName1 = "";//PLC-A    or A机架
        public String subItemValueName2 = "";//PLC-B    or B机架
        public String subItemValueName3 = "";//ESD-A
        public String subItemValueName4 = "";//ESD-B

        public String subItemValueText1 = "";//Pew OK
        public String subItemValueText2 = "";//Pew OK
        public String subItemValueText3 = "";//Pew OK
        public String subItemValueText4 = "";//Pew OK
    }
}

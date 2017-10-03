package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportBySCADA {
    public String scadaTitle = "SCADA系统";
    public String titleDesc = "";
    public String item1Title = "";
    public String item2Title = "";
    public String item3Title = "";
    public String item4Title = "";
    List<MaintainReportBySubSCADA> subSCADAList = new ArrayList<>();
}

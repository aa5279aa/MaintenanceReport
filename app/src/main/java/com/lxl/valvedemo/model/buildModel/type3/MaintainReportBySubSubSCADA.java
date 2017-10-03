package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportBySubSubSCADA {
    public String scadaSubTitle = "";//电源模块
    List<MaintainReportBySubSubItemSCADA> subItemList = new ArrayList<>();
    public int topRow;
    public int lastRow;
}

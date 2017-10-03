package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportBySubSCADA {
    public String scadaSubTitle = "";//CPU机架
    List<MaintainReportBySubSubSCADA> subItemList = new ArrayList<>();
    List<MaintainReportBySubValueSCADA> subValueList = new ArrayList<>();
}

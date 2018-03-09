package com.lxl.valvedemo.model.buildModel.type3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3 0003.
 */

public class MaintainReportSubByNormal extends MaintainReportSubByBase {
    public String subNormalTitle = "";
    public String subDesc = "";//描述
    public List<MaintainReportSubByNormalItemValue> normalItemValueList = new ArrayList<>();

    public MaintainReportSubByNormal(int position) {
        super(position);
    }

    public static class MaintainReportSubByNormalItemValue {
        public String columDesc = "";
        public String columText = "";
    }

}

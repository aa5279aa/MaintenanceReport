package com.lxl.valvedemo.model.buildModel.type8;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by xiangleiliu on 2018/3/9.
 */

public class ReportRecord2Model {
    @JSONField(serialize = false)
    public String desc;
    public String key;
    public String value;
    public String comment;//注释
}

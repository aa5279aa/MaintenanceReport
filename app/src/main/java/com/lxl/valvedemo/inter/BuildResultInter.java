package com.lxl.valvedemo.inter;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public interface BuildResultInter {
    public void buildSucess(String pathStr);

    public void buildFail(String caseStr);
}

package com.lxl.valvedemo.config;

import android.os.Environment;

import com.lxl.valvedemo.util.DateUtil;

import java.io.File;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class ReportBuildConfig {

    public static final String reportBuildPath = Environment.getExternalStorageDirectory() + File.separator + "areport";
    public static final String Execl_Suffix = ".xls";
    public static final String Location_Suffix = ".txt";
    public static final String PNG_Suffix = ".png";

    public static String getBaseBuildPath() {
        String path = ReportBuildConfig.reportBuildPath + File.separator + DateUtil.getCurrentDate();
        return path;
    }

}

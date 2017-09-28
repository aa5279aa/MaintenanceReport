package com.lxl.valvedemo.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class ReportBuildConfig {

    public static final String reportBuildPath = Environment.getExternalStorageDirectory() + File.separator + "areport";
    public static final String Suffix = ".xls";

}

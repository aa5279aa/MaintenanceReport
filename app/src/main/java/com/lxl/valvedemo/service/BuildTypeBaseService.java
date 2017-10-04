package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/4 0004.
 */

public abstract class BuildTypeBaseService {

    abstract public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter)  throws IOException;
}

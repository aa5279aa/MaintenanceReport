package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.MaintainReportModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class BuildService {

    public static BuildService mService;

    private BuildService() {

    }

    public static synchronized BuildService getInstance() {
        mService = new BuildService();
        return mService;
    }


    public void buildReportTypeOne(File outFile, MaintainReportModel maintainReportModel, BuildResultInter inter) throws IOException {
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("设备检查于维护");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        HSSFRow areaRow = sheet.createRow(1);
        HSSFCell areaCell = areaRow.createCell(0);
        HSSFCell stationCell = areaRow.createCell(4);
        stationCell.setCellValue(maintainReportModel.stationName + ":" + maintainReportModel.stationText);
        areaCell.setCellValue(maintainReportModel.workAreaName + ":" + maintainReportModel.workAreaText);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();
    }


}

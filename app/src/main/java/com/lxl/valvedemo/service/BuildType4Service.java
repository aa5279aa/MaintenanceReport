package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type4.AlertReportModel;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class BuildType4Service extends BuildTypeBaseService {

    public BuildType4Service() {

    }

    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        AlertReportModel alertReportModel = reportBuildModel.alertReportModel;
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");
        HSSFCellStyle descStyle = StyleUtil.createFont12BoldLeftStyle(wb);

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(alertReportModel.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleBigFontStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        titleRow.setHeight(StyleUtil.getRowHeight(35));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        HSSFCell areaNameCell = areaRow.createCell(0);
        HSSFCell showValueNameCell = areaRow.createCell(2);
        areaRow.setHeight(StyleUtil.getRowHeight(20));

        areaNameCell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));
        showValueNameCell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));

        areaNameCell.setCellValue(alertReportModel.stationText + alertReportModel.stationName);
        showValueNameCell.setCellValue(alertReportModel.standardName + alertReportModel.standardText);

        if (alertReportModel.reportItemModelList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < alertReportModel.reportItemModelList.size(); i++) {
            String indexCellStr = alertReportModel.positionName;
            String installStr = alertReportModel.installPositionName;
            String showValueStr = alertReportModel.showValueName;
            HSSFRow headerRow = sheet.createRow(i + 3);
            HSSFCell indexCell = headerRow.createCell(0);
            HSSFCell installCell = headerRow.createCell(1);
            HSSFCell showValueCell = headerRow.createCell(2);
            if (i >= 0) {
                AlertReportModel.AlertReportItemModel alertReportItemModel = alertReportModel.reportItemModelList.get(i);
                indexCellStr = String.valueOf(alertReportItemModel.index);
                installStr = alertReportItemModel.installPosition;
                showValueStr = alertReportItemModel.showValue;
                float excelCellAutoHeight = StyleUtil.getExcelCellAutoHeight(installStr, 15, 29);
                headerRow.setHeight(StyleUtil.getRowHeight(excelCellAutoHeight));
                indexCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
                installCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
                showValueCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
            } else {
                headerRow.setHeight(StyleUtil.getRowHeight(20));
                indexCell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));
                installCell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));
                showValueCell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));
            }
            indexCell.setCellValue(indexCellStr);
            installCell.setCellValue(installStr);
            showValueCell.setCellValue(showValueStr);
        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        bottomRow.setHeight(StyleUtil.getRowHeight(15));
        HSSFCell cell = bottomRow.createCell(0);
        cell.setCellValue("注：1、允许示值误差±5%LEL；2、请将存在问题的报警器数量统计后上报安全科。");
        cell.setCellStyle(StyleUtil.createFont12LeftStyle(wb));
        mergedRegion(wb, sheet, nextRow, nextRow, 0, 2);

        nextRow = sheet.getLastRowNum() + 1;
        bottomRow = sheet.createRow(nextRow);
        bottomRow.setHeight(StyleUtil.getRowHeight(20));
        HSSFCell checkCellName = bottomRow.createCell(0);
        HSSFCell checkCellText = bottomRow.createCell(1);
        HSSFCell dateCell = bottomRow.createCell(2);

        checkCellName.setCellStyle(StyleUtil.createFont12BoldCenterNoBorderStyle(wb));
        checkCellText.setCellStyle(StyleUtil.createFont10LeftNoBorderStyle(wb));
        dateCell.setCellStyle(StyleUtil.createFont12BoldLeftNoBorderStyle(wb));

        checkCellName.setCellValue(alertReportModel.checkerName);
        checkCellText.setCellValue(alertReportModel.checkerText);

        dateCell.setCellStyle(descStyle);
        String s = DateUtil.formatDateTime2String(alertReportModel.checkDateText);
        dateCell.setCellValue(alertReportModel.checkDateName + s);

        sheet.addMergedRegion(new CellRangeAddress(bottomRow.getRowNum(), bottomRow.getRowNum(), 0, 1));

        sheet.setColumnWidth(0, StyleUtil.getColumnWidth(25));
        sheet.setColumnWidth(1, StyleUtil.getColumnWidth(52));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(40));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }


    public AlertReportModel readReportTypeFour(InputStream open) {
        AlertReportModel reportModel = new AlertReportModel();
        return reportModel;
    }

    public String checkInfo(ReportBuildModel buildModel) {
        AlertReportModel alertReportModel = buildModel.alertReportModel;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(alertReportModel.stationText)) {
            builder.append("补全输气站，");
        }
        if (StringUtil.emptyOrNull(alertReportModel.checkerText)) {
            builder.append("补全校对人，");
        }
        return builder.toString();
    }
}

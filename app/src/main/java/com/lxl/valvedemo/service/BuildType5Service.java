package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type4.AlertReportModel;
import com.lxl.valvedemo.model.buildModel.type5.ReportModelType5;
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
public class BuildType5Service extends BuildTypeBaseService {

    public BuildType5Service() {

    }

    public ReportModelType5 readReportType(InputStream open) throws IOException {
        ReportModelType5 reportModelType5 = new ReportModelType5();

        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        HSSFRow row = sheet1.getRow(0);
        HSSFCell cell = row.getCell(0);
        //title
        String tableTitle = cell.getStringCellValue();
        reportModelType5.tableName = tableTitle;

        //场站
        HSSFRow stationRow = sheet1.getRow(1);
        HSSFCell stationCell = stationRow.getCell(0);
//        reportModelType5.stationName = stationCell.getStringCellValue();

        //title header
        HSSFRow contentHeader = sheet1.getRow(2);

        //
        HSSFRow lastRow = sheet1.getRow(3);
        while (true) {
            HSSFCell productCell = lastRow.getCell(1);
            if (productCell == null) {
                break;
            }
            int[] rowHeightByIndex = getRowHeightByIndex(sheet1, productCell);
            if (StringUtil.emptyOrNull(productCell.getStringCellValue())) {
                break;
            }
            lastRow = sheet1.getRow(rowHeightByIndex[1] + 1);
            ReportModelType5.ReportModelType5SubModel subModel = new ReportModelType5.ReportModelType5SubModel();
            reportModelType5.reportItemModelList.add(subModel);
            subModel.projectText = productCell.getStringCellValue();
            subModel.indexStart = rowHeightByIndex[0] - 2;
            subModel.indexEnd = rowHeightByIndex[1] - 2;
            for (int i = rowHeightByIndex[0]; i <= rowHeightByIndex[1]; i++) {
                HSSFCell cell1 = sheet1.getRow(i).getCell(2);
                String stringCellValue = cell1.getStringCellValue();
                ReportModelType5.ReportModelType5CheckItem checkItem = new ReportModelType5.ReportModelType5CheckItem();
                checkItem.checkInfo = stringCellValue;
                checkItem.index = i - 2;
                subModel.checkInfoList.add(checkItem);
            }
        }
        return reportModelType5;
    }

    @Override
    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        //拷贝这种类型文件到到指定的目录
        ReportModelType5 reportModelType5 = reportBuildModel.reportModelType5;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportModelType5.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        //desc
        HSSFRow stationRow = sheet.createRow(1);
        HSSFCell stationCell = stationRow.createCell(0);
        stationCell.setCellValue(reportModelType5.stationName + reportModelType5.stationText);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

        if (reportModelType5.reportItemModelList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < reportModelType5.reportItemModelList.size(); i++) {
            String indexCellStr = "序号";
            String projectCellStr = "项目";
            String checkInfoStr = "检查内容";
            String checkResultStr = "检查结果";
            String checkDescStr = "备注";
            HSSFRow headerRow = null;
            if (i < 0) {
                headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                HSSFCell indexCell = headerRow.createCell(0);
                HSSFCell projectCell = headerRow.createCell(1);
                HSSFCell checkInfoCell = headerRow.createCell(2);
                HSSFCell checkResultCell = headerRow.createCell(3);
                HSSFCell checkDescCell = headerRow.createCell(4);

                HSSFCellStyle descStyle = StyleUtil.createDescStyle(wb);
                indexCell.setCellStyle(descStyle);
                projectCell.setCellStyle(descStyle);
                checkInfoCell.setCellStyle(descStyle);
                checkResultCell.setCellStyle(descStyle);
                checkDescCell.setCellStyle(descStyle);

                indexCell.setCellValue(indexCellStr);
                projectCell.setCellValue(projectCellStr);
                checkInfoCell.setCellValue(checkInfoStr);
                checkResultCell.setCellValue(checkResultStr);
                checkDescCell.setCellValue(checkDescStr);
                continue;
            }
            ReportModelType5.ReportModelType5SubModel subModel = reportModelType5.reportItemModelList.get(i);
            projectCellStr = subModel.projectText;
            for (int k = 0; k < subModel.checkInfoList.size(); k++) {
                ReportModelType5.ReportModelType5CheckItem checkItem = subModel.checkInfoList.get(k);
                checkInfoStr = checkItem.checkInfo;
                checkDescStr = checkItem.checkDesc;
                checkResultStr = checkItem.checkResult;
                headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                HSSFCell indexCell = headerRow.createCell(0);
                if (k == 0) {
                    HSSFCell projectCell = headerRow.createCell(1);
                    projectCell.setCellStyle(StyleUtil.createVerticalCenterStyle(wb));//流量计算机检测
                    projectCell.setCellValue(projectCellStr);
                }
                HSSFCell checkInfoCell = headerRow.createCell(2);
                HSSFCell checkResultCell = headerRow.createCell(3);
                HSSFCell checkDescCell = headerRow.createCell(4);
                indexCell.setCellValue(sheet.getLastRowNum() - 2);
                checkInfoCell.setCellValue(checkInfoStr);
                checkResultCell.setCellValue(checkResultStr);
                checkDescCell.setCellValue(checkDescStr);
            }
            //合并单元格
            if (headerRow != null) {
                int rowNum = headerRow.getRowNum();
                int startRowNum = rowNum - subModel.checkInfoList.size() + 1;
                sheet.addMergedRegion(new CellRangeAddress(startRowNum, rowNum, 1, 1));
            }
        }
        //                        场站负责人：                  检查人：                  日期：
        int nextRow = sheet.getLastRowNum() + 3;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        HSSFCell checkerCell = bottomRow.createCell(0);
        checkerCell.setCellValue(reportModelType5.owerName + reportModelType5.owerText + "  " + reportModelType5.checkerName + reportModelType5.checkerText + "  " + reportModelType5.dateName + reportModelType5.dateText);
        checkerCell.setCellStyle(StyleUtil.createHCenterBStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 0, 4));

        //设置列宽
        sheet.setColumnWidth(1, StyleUtil.getColumnWidth(15.8));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(74));
        sheet.setColumnWidth(3, StyleUtil.getColumnWidth(12));
        sheet.setColumnWidth(4, StyleUtil.getColumnWidth(8.3));
        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }

    public String checkInfo(ReportBuildModel buildModel) {
        ReportModelType5 reportModelType5 = buildModel.reportModelType5;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(reportModelType5.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(reportModelType5.checkerText)) {
            builder.append("补全检查人，");
        }
        return builder.toString();
    }
}

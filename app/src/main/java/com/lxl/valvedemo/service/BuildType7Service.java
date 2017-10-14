package com.lxl.valvedemo.service;

import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type7.ReportModelType7;
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
public class BuildType7Service extends BuildTypeBaseService {

    public BuildType7Service() {

    }


    @Override
    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        {
            //拷贝这种类型文件到到指定的目录
            ReportModelType7 reportModelType7 = reportBuildModel.reportModelType7;
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Sheet1");

            //title
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.setHeight(StyleUtil.getRowHeight(37.5));
            HSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(reportModelType7.tableName);
            titleCell.setCellStyle(StyleUtil.createTitleBigFontStyle(wb));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

            //作业区 场站
            HSSFRow stationRow = sheet.createRow(1);
            stationRow.setHeight(StyleUtil.getRowHeight(20));
            HSSFCell workAreaCell = createDescBoldCell(wb, stationRow, 0);
            HSSFCell stationCell = createDescBoldCell(wb, stationRow, 2);
            workAreaCell.setCellValue(reportModelType7.workAreaName + reportModelType7.workAreaText);
            stationCell.setCellValue(reportModelType7.stationName + reportModelType7.stationText);
            mergedRegion(wb, sheet, 1, 1, 0, 1);
            mergedRegion(wb, sheet, 1, 1, 2, 3);

            if (reportModelType7.reportItemModelList.size() == 0) {
                FileOutputStream fileOut = new FileOutputStream(outFile);
                wb.write(fileOut);
                fileOut.close();
                return;
            }
            for (int i = -1; i < reportModelType7.reportItemModelList.size(); i++) {
                String indexCellStr = "序号";
                String checkInfoStr = "电气设备定期维护项目";
                String checkDescStr = "检查情况";
                HSSFRow headerRow = null;
                if (i < 0 || (sheet.getLastRowNum() + 1) == 16) {
                    headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    headerRow.setHeight(StyleUtil.getRowHeight(28));
                    HSSFCell indexCell = headerRow.createCell(0);
                    HSSFCell checkInfoCell = headerRow.createCell(1);
                    HSSFCell checkDescCell = headerRow.createCell(2);

                    HSSFCellStyle descStyle = StyleUtil.createFont12BoldCenterStyle(wb);
                    indexCell.setCellStyle(descStyle);
                    checkInfoCell.setCellStyle(descStyle);
                    checkDescCell.setCellStyle(descStyle);

                    indexCell.setCellValue(indexCellStr);
                    checkInfoCell.setCellValue(checkInfoStr);
                    checkDescCell.setCellValue(checkDescStr);
                    mergedRegion(wb, sheet, headerRow.getRowNum(), headerRow.getRowNum(), 2, 3);
                    continue;
                }
                //每一行的逻辑处理
                ReportModelType7.ReportModelType7SubModel subModel = reportModelType7.reportItemModelList.get(i);
                //添加标题栏
                headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                headerRow.setHeight(StyleUtil.getRowHeight(18));
                HSSFCell indexCell = headerRow.createCell(0);
                HSSFCell projectCell = headerRow.createCell(1);
                HSSFCell borderCell = headerRow.createCell(2);

                indexCell.setCellStyle(StyleUtil.createFont10BoldLeftStyle(wb));
                projectCell.setCellStyle(StyleUtil.createFont10BoldLeftStyle(wb));
                borderCell.setCellStyle(StyleUtil.createBorderStyle(wb));

                indexCell.setCellValue(subModel.indexStr);
                projectCell.setCellValue(subModel.projectText);
                mergedRegion(wb, sheet, headerRow.getRowNum(), headerRow.getRowNum(), 2, 3);

                if (subModel.subItemModelList.size() > 0) {
                    for (ReportModelType7.ReportModelType7SubItemModel reportModelType7SubItemModel : subModel.subItemModelList) {
                        headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        HSSFCell checkCell = headerRow.createCell(1);
                        HSSFCell descCell = headerRow.createCell(2);
                        mergedRegion(wb, sheet, headerRow.getRowNum(), headerRow.getRowNum(), 2, 3);

                        checkCell.setCellStyle(StyleUtil.createFont8LeftStyle(wb));
                        descCell.setCellStyle(StyleUtil.createFont8LeftStyle(wb));

                        checkCell.setCellValue("\r\n" + reportModelType7SubItemModel.checkInfo + "\r\n");
                        descCell.setCellValue("\r\n" + reportModelType7SubItemModel.checkDescText + "\r\n");
                    }
                } else {
                    for (ReportModelType7.ReportModelType7SubSubModel subSubModel : subModel.checkInfoSubList) {
                        headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        headerRow.setHeight(StyleUtil.getRowHeight(18));

                        HSSFCell subIndexCell = headerRow.createCell(0);
                        HSSFCell subProjectCell = headerRow.createCell(1);
                        borderCell = headerRow.createCell(2);

                        subIndexCell.setCellStyle(StyleUtil.createFont10BoldLeftStyle(wb));
                        subProjectCell.setCellStyle(StyleUtil.createFont10BoldLeftStyle(wb));
                        borderCell.setCellStyle(StyleUtil.createBorderStyle(wb));

                        subIndexCell.setCellValue(subSubModel.indexStr);
                        subProjectCell.setCellValue(subSubModel.projectText);
                        mergedRegion(wb, sheet, headerRow.getRowNum(), headerRow.getRowNum(), 2, 3);

                        for (ReportModelType7.ReportModelType7SubItemModel reportModelType7SubItemModel : subSubModel.checkInfoSubList) {
                            headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                            HSSFCell checkCell = headerRow.createCell(1);
                            HSSFCell descCell = headerRow.createCell(2);
                            mergedRegion(wb, sheet, headerRow.getRowNum(), headerRow.getRowNum(), 2, 3);

                            checkCell.setCellStyle(StyleUtil.createFont8LeftStyle(wb));
                            descCell.setCellStyle(StyleUtil.createFont8LeftStyle(wb));

                            checkCell.setCellValue("\r\n" + reportModelType7SubItemModel.checkInfo + "\r\n");
                            descCell.setCellValue("\r\n" + reportModelType7SubItemModel.checkDescText + "\r\n");
                        }
                    }
                }
            }

            //备注
            HSSFRow bottomRow = sheet.createRow(sheet.getLastRowNum() + 1);
            bottomRow.setHeight(StyleUtil.getRowHeight(106));
            HSSFCell descNameCell = bottomRow.createCell(0);
            HSSFCell descTextCell = bottomRow.createCell(1);

            descNameCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
            descTextCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));

            descNameCell.setCellValue(reportModelType7.descName);
            descTextCell.setCellValue(reportModelType7.descText);
            mergedRegion(wb, sheet, bottomRow.getRowNum(), bottomRow.getRowNum(), 1, 3);

            bottomRow = sheet.createRow(sheet.getLastRowNum() + 2);
            bottomRow.setHeight(StyleUtil.getRowHeight(15.6));
            bottomRow.setHeight(StyleUtil.getRowHeight(14.25));
            HSSFCell bottomCell = bottomRow.createCell(0);

            bottomCell.setCellStyle(StyleUtil.createFont12BoldCenterNoBorderStyle(wb));
            bottomCell.setCellValue(reportModelType7.checkName + reportModelType7.checkText + "   " + reportModelType7.confirmName + reportModelType7.confirmText + "   " + reportModelType7.dateName + DateUtil.formatDateTime2String(reportModelType7.dateText));

            mergedRegion(wb, sheet, bottomRow.getRowNum(), bottomRow.getRowNum(), 0, 3);

            sheet.setColumnWidth(0, StyleUtil.getColumnWidth(5));
            sheet.setColumnWidth(1, StyleUtil.getColumnWidth(35));
            sheet.setColumnWidth(2, StyleUtil.getColumnWidth(13.3));
            sheet.setColumnWidth(3, StyleUtil.getColumnWidth(26.9));

            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();

            if (inter != null) {
                inter.buildSucess(outFile.getPath());
            }
        }
    }

    public ReportModelType7 readReportType(InputStream open) throws IOException {
        ReportModelType7 reportModelType7 = new ReportModelType7();

        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        HSSFRow row = sheet1.getRow(0);
        HSSFCell cell = row.getCell(0);
        //title
        String tableTitle = cell.getStringCellValue();
        reportModelType7.tableName = tableTitle;

        //作业区 场站
        HSSFRow stationRow = sheet1.getRow(1);

        //title header
        HSSFRow contentHeader = sheet1.getRow(2);
        //
        HSSFRow nextRow = sheet1.getRow(2);
        while (true) {
            nextRow = sheet1.getRow(nextRow.getRowNum() + 1);
            if (nextRow.getRowNum() == 16) {
                continue;
            }
            HSSFCell indexCell = nextRow.getCell(0);
            if (indexCell == null || indexCell.getCellType() < 0) {
                break;
            }
            String stringCellValue1 = indexCell.getStringCellValue();
            if (stringCellValue1 == null || stringCellValue1.equals("备注")) {
                break;
            }
            if (DataConfig.isType7Start(stringCellValue1)) {
                //节点的头部
                HSSFCell cell0 = nextRow.getCell(0);//序号
                HSSFCell cell1 = nextRow.getCell(1);//济柴燃气发电机组
                HSSFCell cell2 = nextRow.getCell(2);//检查情况
                ReportModelType7.ReportModelType7SubModel subModel = new ReportModelType7.ReportModelType7SubModel();
                subModel.indexStr = cell0.getStringCellValue();
                subModel.projectText = cell1.getStringCellValue();
                reportModelType7.reportItemModelList.add(subModel);
                continue;
            }
            ReportModelType7.ReportModelType7SubModel subModel = reportModelType7.reportItemModelList.get(reportModelType7.reportItemModelList.size() - 1);
            if (DataConfig.isType7SubStart(stringCellValue1)) {
                ReportModelType7.ReportModelType7SubSubModel subSubModel = new ReportModelType7.ReportModelType7SubSubModel();
                subModel.checkInfoSubList.add(subSubModel);
                subSubModel.indexStr = nextRow.getCell(0).getStringCellValue();
                subSubModel.projectText = nextRow.getCell(1).getStringCellValue();
                continue;
            }
            //为空，则获取高度
            int[] rowHeightByIndex = getRowHeightByIndex(sheet1, indexCell);
            for (int i = rowHeightByIndex[0]; i <= rowHeightByIndex[1]; i++) {
                //挂载到最近的一个节点上
                ReportModelType7.ReportModelType7SubItemModel reportModelType7SubItemModel = new ReportModelType7.ReportModelType7SubItemModel();
                reportModelType7SubItemModel.checkInfo = nextRow.getCell(1).getStringCellValue();
                if (subModel.checkInfoSubList.size() == 0) {
                    subModel.subItemModelList.add(reportModelType7SubItemModel);
                } else {
                    subModel.checkInfoSubList.get(subModel.checkInfoSubList.size() - 1).checkInfoSubList.add(reportModelType7SubItemModel);
                }
            }
        }
        return reportModelType7;
    }

    public String checkInfo(ReportBuildModel buildModel) {
        ReportModelType7 reportModelType7 = buildModel.reportModelType7;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(reportModelType7.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(reportModelType7.workAreaText)) {
            builder.append("补全作业区，");
        }
        if (StringUtil.emptyOrNull(reportModelType7.checkText)) {
            builder.append("补全检修人员，");
        }
        return builder.toString();
    }

}

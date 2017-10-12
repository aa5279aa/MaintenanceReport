package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type6.ReportModelType6;
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
public class BuildType6Service extends BuildTypeBaseService {

    public BuildType6Service() {

    }

    @Override
    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        //拷贝这种类型文件到到指定的目录
        ReportModelType6 reportModelType6 = reportBuildModel.reportModelType6;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeight(StyleUtil.getRowHeight(40));
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportModelType6.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleBigFontStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        //作业区 场站
        HSSFRow stationRow = sheet.createRow(1);
        stationRow.setHeight(StyleUtil.getRowHeight(32));
        HSSFCell borderCell = stationRow.createCell(1);
        HSSFCell workAreaCell = stationRow.createCell(1);
        HSSFCell stationCell = stationRow.createCell(2);

        borderCell.setCellStyle(StyleUtil.createBorderStyle(wb));
        workAreaCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        stationCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));

        workAreaCell.setCellValue(reportModelType6.workAreaName + reportModelType6.workAreaText);
        stationCell.setCellValue(reportModelType6.stationName + reportModelType6.stationText);

        if (reportModelType6.reportItemModelList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < reportModelType6.reportItemModelList.size(); i++) {
            String indexCellStr = "序号";
            String checkInfoStr = "维护保养项目和内容";
            String checkDescStr = "设备情况";
            HSSFRow headerRow = null;
            if (i < 0) {
                headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                headerRow.setHeight(StyleUtil.getRowHeight(32));
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
                continue;
            }
            ReportModelType6.ReportModelType6SubModel subModel = reportModelType6.reportItemModelList.get(i);
            //描述行
            HSSFRow itemHeader = sheet.createRow(sheet.getLastRowNum() + 1);
            HSSFCell indexCell = itemHeader.createCell(0);
            HSSFCell checkProjectCell = itemHeader.createCell(1);
            HSSFCell checkDescCell = itemHeader.createCell(2);
            indexCell.setCellValue(String.valueOf(i + 1));
            indexCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
            checkProjectCell.setCellValue(subModel.projectText);
            checkProjectCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
            checkDescCell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
            checkDescCell.setCellValue(subModel.checkDesc);
            int size = subModel.checkInfoList.size();
            for (int k = 0; k < size; k++) {
                String checkInfo = subModel.checkInfoList.get(k);
                headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                HSSFCell checkInfoCell = createBaseCell(wb, headerRow, 1);
                checkInfoCell.setCellValue(checkInfo);
            }
            //合并单元格
            if (headerRow != null) {
                int startRowNum = itemHeader.getRowNum();
                int endRowNum = itemHeader.getRowNum() + size;
                mergedRegion(wb, sheet, startRowNum, endRowNum, 2, 2);
            }
        }

        //确认项
        HSSFRow bottomRow = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell cell0 = bottomRow.createCell(0);
        HSSFCell cell1 = bottomRow.createCell(1);
        HSSFCell cell2 = bottomRow.createCell(2);

        cell0.setCellStyle(StyleUtil.createFont11BoldLeft(wb));
        cell1.setCellStyle(StyleUtil.createFont11BoldLeft(wb));
        cell2.setCellStyle(StyleUtil.createFont11BoldLeft(wb));

        cell0.setCellValue("确认项");
        cell1.setCellValue("是否对阀门执行机构进行排水、通风处理？（方法：打开齿轮箱侧面的内六角螺栓排水，每两月找晴朗无风的天气打开排水螺栓通风透气5～8天。）");
        cell2.setCellValue(" 确认签字：" + reportModelType6.confirmText);

        bottomRow = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell checkerCell = bottomRow.createCell(0);
        HSSFCell dateCell = bottomRow.createCell(2);
        checkerCell.setCellValue(reportModelType6.checkerName + reportModelType6.checkerText);
        dateCell.setCellValue(reportModelType6.dateName + DateUtil.formatDateTime2String(reportModelType6.dateText));
        checkerCell.setCellStyle(StyleUtil.createFont11NoBorderCenter(wb));
        dateCell.setCellStyle(StyleUtil.createFont11NoBorderCenter(wb));
        sheet.addMergedRegion(new CellRangeAddress(bottomRow.getRowNum(), bottomRow.getRowNum(), 0, 1));

        sheet.setColumnWidth(0, StyleUtil.getColumnWidth(6.2));
        sheet.setColumnWidth(1, StyleUtil.getColumnWidth(77));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(44.4));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }

    public ReportModelType6 readReportType(InputStream open) throws IOException {
        ReportModelType6 reportModelType6 = new ReportModelType6();

        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        HSSFRow row = sheet1.getRow(0);
        HSSFCell cell = row.getCell(0);
        //title
        String tableTitle = cell.getStringCellValue();
        reportModelType6.tableName = tableTitle;

        //作业区
        HSSFRow stationRow = sheet1.getRow(1);
        HSSFCell workAreaCell = stationRow.getCell(1);
        reportModelType6.workAreaText = workAreaCell.getStringCellValue();
        //场站
        HSSFCell stationCell = stationRow.getCell(2);
        reportModelType6.stationText = stationCell.getStringCellValue();

        //title header
        HSSFRow contentHeader = sheet1.getRow(2);

        //
        HSSFRow lastRow = sheet1.getRow(3);
        int index = 1;
        while (true) {
            HSSFCell indexCell = lastRow.getCell(0);
            if (indexCell == null || indexCell.getCellType() < 0) {
                break;
            }
            String stringCellValue1 = indexCell.getStringCellValue();
            if (stringCellValue1 == null || stringCellValue1.length() > 2) {
                break;
            }
            HSSFCell cell0 = lastRow.getCell(0);//序号
            HSSFCell cell1 = lastRow.getCell(1);//保养内容
            HSSFCell cell2 = lastRow.getCell(2);//设备情况

            String value0 = "";
            if (cell0 != null) {
                value0 = cell0.getStringCellValue();
            }
            String value1 = cell1.getStringCellValue();
            String value2 = cell2.getStringCellValue();

            if (!StringUtil.emptyOrNull(value0)) {
                ReportModelType6.ReportModelType6SubModel subModel = new ReportModelType6.ReportModelType6SubModel();
                reportModelType6.reportItemModelList.add(subModel);
                subModel.index = index++;
                subModel.projectText = value1;
            } else {
                ReportModelType6.ReportModelType6SubModel lastSubModel = reportModelType6.reportItemModelList.get(reportModelType6.reportItemModelList.size() - 1);
                lastSubModel.checkInfoList.add(value1);
            }
            lastRow = sheet1.getRow(lastRow.getRowNum() + 1);
        }
        return reportModelType6;
    }

    public String checkInfo(ReportBuildModel buildModel) {
        ReportModelType6 reportModelType6 = buildModel.reportModelType6;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(reportModelType6.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(reportModelType6.workAreaText)) {
            builder.append("补全作业区，");
        }
        return builder.toString();
    }

    ;
}

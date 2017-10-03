package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.util.StyleUtil;

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
public class BuildTye4Service {

    public BuildTye4Service() {

    }

    public void buildReportTypeOne(File outFile, MaintainReportModel maintainReportModel, BuildResultInter inter) throws IOException {
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(maintainReportModel.tableTile);
        titleCell.setCellStyle(StyleUtil.createTitleStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        HSSFCell areaCell = areaRow.createCell(0);
        HSSFCell stationCell = areaRow.createCell(4);
        stationCell.setCellValue(maintainReportModel.stationName + ":" + maintainReportModel.stationText);
        areaCell.setCellValue(maintainReportModel.workAreaName + ":" + maintainReportModel.workAreaText);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        if (maintainReportModel.maintainList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < maintainReportModel.maintainList.size(); i++) {
            String positionCellStr = "序号";
            String equipmentNameCellStr = "设备名称";
            String specificationsCellStr = "规格型号";
            String equipmentIdCellStr = "设备编号";
            String maintainInfoCellStr = "检查与维护保养情况";
            String maintainDescCellStr = "备注";
            if (i >= 0) {
                MaintainReportItemModel maintainReportItemModel = maintainReportModel.maintainList.get(i);
                positionCellStr = String.valueOf(i + 1);
                equipmentNameCellStr = maintainReportItemModel.equipmentName;
                specificationsCellStr = maintainReportItemModel.specificationsName;
                equipmentIdCellStr = maintainReportItemModel.equipmentId;
                maintainInfoCellStr = maintainReportItemModel.maintainInfo;
                maintainDescCellStr = maintainReportItemModel.maintainDesc;
            }
            HSSFRow headerRow = sheet.createRow(i + 3);
            HSSFCell positionCell = headerRow.createCell(0);
            HSSFCell equipmentNameCell = headerRow.createCell(1);
            HSSFCell specificationsCell = headerRow.createCell(2);
            HSSFCell equipmentIdCell = headerRow.createCell(3);
            HSSFCell maintainInfoCell = headerRow.createCell(4);
            HSSFCell maintainDescCell = headerRow.createCell(5);
            positionCell.setCellValue(positionCellStr);
            equipmentNameCell.setCellValue(equipmentNameCellStr);
            specificationsCell.setCellValue(specificationsCellStr);
            equipmentIdCell.setCellValue(equipmentIdCellStr);
            maintainInfoCell.setCellValue(maintainInfoCellStr);
            maintainDescCell.setCellValue(maintainDescCellStr);
        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        HSSFCell checkerCell = bottomRow.createCell(0);
        HSSFCell dataCell = bottomRow.createCell(4);
        checkerCell.setCellValue(maintainReportModel.checkerText);
        dataCell.setCellValue(maintainReportModel.dateText);
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 4, 5));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }


}

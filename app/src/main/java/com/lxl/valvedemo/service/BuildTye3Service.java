package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class BuildTye3Service {

    public BuildTye3Service() {

    }

    public void buildReportTypeThree(File outFile, MaintainReportModel maintainReportModel, BuildResultInter inter) throws IOException {
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


    public MaintainReportByAreaModel readReportTypeThree(InputStream open) throws IOException {
        MaintainReportByAreaModel mainAreaModel = new MaintainReportByAreaModel();
        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        setEquipmentValue(mainAreaModel, sheet1);
        setSCADAValue(mainAreaModel, sheet1);
        return mainAreaModel;
    }

    public void setEquipmentValue(MaintainReportByAreaModel mainAreaModel, HSSFSheet sheet1) {
        //仪表设备
        HSSFRow row4 = sheet1.getRow(4);
        mainAreaModel.meterPressureName = row4.getCell(0).getStringCellValue();
        mainAreaModel.meterPressureDesc = row4.getCell(1).getStringCellValue();

        HSSFRow row5 = sheet1.getRow(5);
        mainAreaModel.meterTemperatureName = row5.getCell(0).getStringCellValue();
        mainAreaModel.meterTemperatureDesc = row5.getCell(1).getStringCellValue();

        HSSFRow row6 = sheet1.getRow(6);
        mainAreaModel.meterPressureTransferName = row6.getCell(0).getStringCellValue();
        mainAreaModel.meterPressureTransferDesc = row6.getCell(1).getStringCellValue();

        HSSFRow row7 = sheet1.getRow(7);
        mainAreaModel.meterTemperatureTransferName = row7.getCell(0).getStringCellValue();
        mainAreaModel.meterTemperatureTransferDesc = row7.getCell(1).getStringCellValue();

        HSSFRow row8 = sheet1.getRow(8);
        mainAreaModel.meterIndicatorLightName = row8.getCell(0).getStringCellValue();
        mainAreaModel.meterIndicatorLightDesc = row8.getCell(1).getStringCellValue();
    }

    public void setSCADAValue(MaintainReportByAreaModel mainAreaModel, HSSFSheet sheet1) {
        Row allRow = null;
        int startRow = 0;
        do {
            allRow = findNextAllRow(sheet1, startRow);
            MaintainReportBySCADA scada = new MaintainReportBySCADA();
            setSubSCADAValue(scada, sheet1);
            mainAreaModel.scadaList.add(scada);
            startRow = 5;
        } while (allRow != null);

    }

    public void setSubSCADAValue(MaintainReportBySCADA scada, HSSFSheet sheet1) {

    }

    public Row findNextAllRow(Sheet sheet, int startRow) {
        Row row = sheet.getRow(0);
        //寻找合并横行所有单元格的

        //寻找合并竖行单元格的
        //mainAreaModel.scadaList
        return row;
    }


}

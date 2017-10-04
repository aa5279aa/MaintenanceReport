package com.lxl.valvedemo.service;

import android.util.Log;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByCPU;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByESD;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByNormal;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class BuildTye3Service extends BuildTypeBaseService{

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
//        setEquipmentValue(mainAreaModel, sheet1);
        setSCADAValue(mainAreaModel, sheet1);
        return mainAreaModel;
    }

    public void setSCADAValue(MaintainReportByAreaModel mainAreaModel, HSSFSheet sheet1) {
        List<Integer[]> allCloumRowNumList = findAllNextAllRow(mainAreaModel.tableName, sheet1);
        //仪表设备   SCADA系统   消防系统
        for (Integer[] rowNum : allCloumRowNumList) {
            MaintainReportBySCADA scada = new MaintainReportBySCADA();
            setSubSCADAValue(mainAreaModel, scada, sheet1, rowNum[0], rowNum[1]);
            mainAreaModel.scadaList.add(scada);
        }
        Log.i("lxltest", "size:" + allCloumRowNumList.size());
    }

    public void setSubSCADAValue(MaintainReportByAreaModel mainAreaModel, MaintainReportBySCADA scada, HSSFSheet sheet, int startRowNum, int endRowNum) {
        scada.scadaTitle = sheet.getRow(startRowNum).getCell(0).getStringCellValue();//仪表设备
        startRowNum++;
        while (true) {
            HSSFRow row = sheet.getRow(startRowNum);
            HSSFCell cell = row.getCell(0);//CPU机架 or 远程机架
            String stringCellValue = cell.getStringCellValue();
            int position = 0;
            if (stringCellValue.equals("CPU机架")) {
                MaintainReportSubByCPU reportSubByCPU = new MaintainReportSubByCPU(position++);
                scada.subList.add(reportSubByCPU);
                reportSubByCPU.cpuTitle = "记录以下指示灯状态";
                if (mainAreaModel.tableName.contains("冀宁线")) {
                    reportSubByCPU.cpuColumName1 = "A机架";
                    reportSubByCPU.cpuColumName2 = "B机架";
                } else {
                    reportSubByCPU.cpuColumName1 = "PLC-A";
                    reportSubByCPU.cpuColumName2 = "PLC-B";
                    reportSubByCPU.cpuColumName3 = "ESD-A";
                    reportSubByCPU.cpuColumName4 = "ESD-B";
                }
                startRowNum++;
                HSSFRow cpuRow = sheet.getRow(startRowNum);
                HSSFCell cpuCell = cpuRow.getCell(1);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuCell);
                int cpuFirstRow = rowHeightByIndex[0];
                int cpuLastRow = rowHeightByIndex[1];
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(cpuFirstRow);
                    HSSFCell cell1 = lineRow.getCell(2);
                    MaintainReportSubByCPU.MaintainReportByCPUItemValue cpuItemValue = new MaintainReportSubByCPU.MaintainReportByCPUItemValue();
                    cpuItemValue.subItemName = cell1.getStringCellValue();
                    reportSubByCPU.subItemList.add(cpuItemValue);
                }
            } else if (stringCellValue.equals("ESD系统（Himatrix）")) {
                MaintainReportSubByESD maintainReportByESD = new MaintainReportSubByESD(position++);
                scada.subList.add(maintainReportByESD);
                maintainReportByESD.cpuColumName1 = "记录指示灯";
                maintainReportByESD.cpuColumName2 = "F30";
                maintainReportByESD.cpuColumName3 = "IO1";
                maintainReportByESD.cpuColumName4 = "IO2";
                startRowNum++;
                HSSFRow cpuRow = sheet.getRow(startRowNum);
                HSSFCell cpuCell = cpuRow.getCell(1);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuCell);
                int cpuFirstRow = rowHeightByIndex[0];
                int cpuLastRow = rowHeightByIndex[1];
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(cpuFirstRow);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByESD.MaintainReportByESDItemValue cpuItemValue = new MaintainReportSubByESD.MaintainReportByESDItemValue();
                    cpuItemValue.rowTitle = cell1.getStringCellValue();
                    maintainReportByESD.esdItemValueList.add(cpuItemValue);
                }
            } else if (stringCellValue.equals("远程机架") && mainAreaModel.tableName.contains("平泰线")) {
                //暂不处理

            } else {
                MaintainReportSubByNormal maintainReportSubByNormal = new MaintainReportSubByNormal(position++);
                scada.subList.add(maintainReportSubByNormal);
                HSSFRow cpuRow = sheet.getRow(startRowNum);
                HSSFCell cpuCell = cpuRow.getCell(1);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuCell);
                int cpuFirstRow = rowHeightByIndex[0];
                int cpuLastRow = rowHeightByIndex[1];
                maintainReportSubByNormal.subNormalTitle = cpuCell.getStringCellValue();
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(cpuFirstRow);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue = new MaintainReportSubByNormal.MaintainReportSubByNormalItemValue();
                    normalItemValue.columName = cell1.getStringCellValue();
                    maintainReportSubByNormal.normalItemValueList.add(normalItemValue);
                }
            }

            int[] rowHeightByIndex = getRowHeightByIndex(sheet, cell);
            int lastRow = rowHeightByIndex[1];
            startRowNum = lastRow + 1;
            if (lastRow >= endRowNum) {
                break;
            }
        }
        Log.i("lxltest", "scada.subList:" + scada.subList.size());
    }

    public void setSubSubSCADAValue(MaintainReportBySCADA scada, HSSFSheet sheet, int startRowNum) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (firstRow == startRowNum && lastColumn == 0) {
                scada.scadaTitle = sheet.getRow(firstRow).getCell(0).getStringCellValue();
                scada.firstRow = firstRow;
                scada.lastRow = lastRow;
            }
        }
    }

    public List<Integer[]> findAllNextAllRow(String tableName, Sheet sheet) {
        List<Integer[]> allRowList = new ArrayList<>();
        if (tableName.contains("冀宁线")) {
            Integer[] integers1 = new Integer[]{3, 8};
            Integer[] integers2 = new Integer[]{9, 54};
            Integer[] integers3 = new Integer[]{55, 60};
            allRowList.add(integers1);
            allRowList.add(integers2);
            allRowList.add(integers3);
        } else {
            Integer[] integers3 = new Integer[]{4, 8};
            Integer[] integers1 = new Integer[]{10, 45};
            Integer[] integers2 = new Integer[]{47, 51};
            allRowList.add(integers1);
            allRowList.add(integers2);
            allRowList.add(integers3);
        }

        //寻找合并横行所有单元格的
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();
//            int lastColumn = range.getLastColumn();
//            int lastRow = range.getLastRow();
//            int firstRow = range.getFirstRow();
//            if (firstColumn == 0 && lastColumn == 5 && firstRow != 0) {
//                nextAllRowList.add(firstRow);
//            }
//        }
//        //去掉最大的三个
//        Collections.sort(nextAllRowList, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer lhs, Integer rhs) {
//                return lhs > rhs ? 1 : -1;
//            }
//        });
//        return nextAllRowList.subList(0, nextAllRowList.size() - 3);

        return allRowList;
    }

    @Override
    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {

    }
}

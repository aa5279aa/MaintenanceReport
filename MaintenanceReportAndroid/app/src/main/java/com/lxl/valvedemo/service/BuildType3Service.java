package com.lxl.valvedemo.service;

import android.util.Log;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByBase;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByCPU;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByESD;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByNormal;
import com.lxl.valvedemo.util.StringUtil;
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
import java.util.List;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class

BuildType3Service extends BuildTypeBaseService {

    public BuildType3Service() {

    }

    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        MaintainReportByAreaModel maintainReportByArea = reportBuildModel.maintainReportByArea;
        String tableName = maintainReportByArea.tableName;
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        boolean isPingtai = !maintainReportByArea.tableName.contains("冀宁");
        int lastCol = isPingtai ? 7 : 5;

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(maintainReportByArea.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleSmallFontStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, lastCol));
        titleRow.setHeight(StyleUtil.getRowHeight(28.5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        areaRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell stationNameCell;
        HSSFCell stationTextCell;
        HSSFCell dateNameCell;
        HSSFCell dateTextCell;
        if (isPingtai) {
            stationNameCell = createDescCell(wb, areaRow, 0);
            stationTextCell = createDescCell(wb, areaRow, 1);
            dateNameCell = createDescCell(wb, areaRow, 3);
            dateTextCell = createBaseCell(wb, areaRow, 7);
            mergedRegion(wb, sheet, 1, 1, 1, 2);
            mergedRegion(wb, sheet, 1, 1, 3, 6);
        } else {
            stationNameCell = createDescCell(wb, areaRow, 0);
            stationTextCell = createDescCell(wb, areaRow, 1);
            dateNameCell = createDescCell(wb, areaRow, 3);
            dateTextCell = createBaseCell(wb, areaRow, 4);
            mergedRegion(wb, sheet, 1, 1, 1, 2);
            mergedRegion(wb, sheet, 1, 1, lastCol - 1, lastCol);
        }
        stationNameCell.setCellValue(maintainReportByArea.stationName);
        stationTextCell.setCellValue(maintainReportByArea.stationText);
        dateNameCell.setCellValue(maintainReportByArea.dateName);
        dateTextCell.setCellValue(maintainReportByArea.dateText);

        //desc
        HSSFRow headerRow = sheet.createRow(2);
        headerRow.setHeight(StyleUtil.getRowHeight(16));
        HSSFCell cell0;
        HSSFCell cell1;
        HSSFCell cell5;
        if (isPingtai) {
            cell0 = headerRow.createCell(0);
            cell1 = headerRow.createCell(1);
            cell5 = headerRow.createCell(7);
            mergedRegion(wb, sheet, 2, 2, 1, 6);
        } else {
            cell0 = headerRow.createCell(0);
            cell1 = headerRow.createCell(1);
            cell5 = headerRow.createCell(5);
            mergedRegion(wb, sheet, 2, 2, 1, 4);
        }
        cell0.setCellValue("检查项目");
        cell1.setCellValue("检查内容");
        cell5.setCellValue("检查结论（如有故障详细记录故障状态）");
        cell0.setCellStyle(StyleUtil.createFont10CenterStyle(wb));
        cell1.setCellStyle(StyleUtil.createFont10CenterStyle(wb));
        cell5.setCellStyle(StyleUtil.createFont10CenterStyle(wb));
        if (maintainReportByArea.scadaList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        HSSFRow row;
        for (int i = 0; i < maintainReportByArea.scadaList.size(); i++) {
            MaintainReportBySCADA reportBySCADA = maintainReportByArea.scadaList.get(i);
            int rowNum = sheet.getLastRowNum() + 1;
            row = sheet.createRow(rowNum);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(reportBySCADA.scadaTitle);
            cell.setCellStyle(StyleUtil.createFont10CenterStyle(wb));
            row.setHeight(StyleUtil.getRowHeight(15));
            mergedRegion(wb, sheet, rowNum, rowNum, 0, lastCol);
            for (int j = 0; j < reportBySCADA.subList.size(); j++) {
                MaintainReportSubByBase subByBase = reportBySCADA.subList.get(j);
                if (subByBase instanceof MaintainReportSubByCPU) {
                    MaintainReportSubByCPU subByCPU = (MaintainReportSubByCPU) subByBase;
                    int cpuStartRowNum = sheet.getLastRowNum() + 1;
                    //创建title
                    row = sheet.createRow(cpuStartRowNum);
                    HSSFCell descCell1 = createBaseCell(wb, row, 1);
                    HSSFCell descCell3 = createBaseCell(wb, row, 3);
                    HSSFCell descCell4 = createBaseCell(wb, row, 4);
                    descCell1.setCellValue(subByCPU.cpuColumName0);
                    descCell3.setCellValue(subByCPU.cpuColumName1);
                    descCell4.setCellValue(subByCPU.cpuColumName2);
                    if (!tableName.contains("冀宁")) {
                        HSSFCell descCell5 = createBaseCell(wb, row, 5);
                        HSSFCell descCell6 = createBaseCell(wb, row, 6);
                        descCell5.setCellValue(subByCPU.cpuColumName3);
                        descCell6.setCellValue(subByCPU.cpuColumName4);
                    }
                    mergedRegion(wb, sheet, sheet.getLastRowNum(), sheet.getLastRowNum(), 1, 2);
                    for (int k = 0; k < subByCPU.cpuSubList.size(); k++) {
                        MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue = subByCPU.cpuSubList.get(k);
                        int subStartNum = sheet.getLastRowNum() + 1;
                        for (int m = 0; m < cpuSubValue.cpuItemValueList.size(); m++) {
                            MaintainReportSubByCPU.MaintainReportByCPUItemValue maintainReportByCPUItemValue = cpuSubValue.cpuItemValueList.get(m);
                            row = sheet.createRow(sheet.getLastRowNum() + 1);
                            row.setHeight(StyleUtil.getRowHeight(15.6));
                            //title
                            HSSFCell subCell1 = createBaseCell(wb, row, 1);
                            HSSFCell subCell2 = createBaseCell(wb, row, 2);
                            HSSFCell subCell3 = createBaseCell(wb, row, 3);
                            HSSFCell subCell4 = createBaseCell(wb, row, 4);
                            if (!tableName.contains("冀宁")) {
                                HSSFCell subCell5 = createBaseCell(wb, row, 5);
                                HSSFCell subCell6 = createBaseCell(wb, row, 6);
                                subCell5.setCellValue(maintainReportByCPUItemValue.subItemValueText3);
                                subCell6.setCellValue(maintainReportByCPUItemValue.subItemValueText4);
                            }
                            subCell1.setCellValue(cpuSubValue.cpuSubName);
                            subCell2.setCellValue(maintainReportByCPUItemValue.subItemName);
                            subCell3.setCellValue(maintainReportByCPUItemValue.subItemValueText1);
                            subCell4.setCellValue(maintainReportByCPUItemValue.subItemValueText2);
                        }
                        if (sheet.getLastRowNum() > subStartNum) {
                            mergedRegion(wb, sheet, subStartNum, sheet.getLastRowNum(), 1, 1);
                        }
                    }
                    //添加io模块
                    if (!StringUtil.emptyOrNull(subByCPU.ioDescName)) {
                        HSSFRow ioRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        HSSFCell ioNameCell = createBaseCell(wb, ioRow, 1);
                        HSSFCell ioTextCell = createBaseCell(wb, ioRow, 7);
                        ioNameCell.setCellValue(subByCPU.ioDescName);
                        ioTextCell.setCellValue(subByCPU.ioDescText);
                        mergedRegion(wb, sheet, sheet.getLastRowNum(), sheet.getLastRowNum(), 1, 6);
                    }
                    //设置名称并合并单元格
                    HSSFCell subTitleCell = sheet.getRow(cpuStartRowNum).createCell(0);
                    subTitleCell.setCellValue(subByCPU.cpuTitle);
                    subTitleCell.setCellStyle(StyleUtil.createVerticalCenterStyle(wb));

                    HSSFCell subDescCell = sheet.getRow(cpuStartRowNum).createCell(lastCol);
                    StringBuilder subDescBuilder = new StringBuilder();
                    for (String key : subByCPU.cpuCheckInfoMap.keySet()) {
                        subDescBuilder.append(key + subByCPU.cpuCheckInfoMap.get(key) + "\n");
                    }
                    subDescCell.setCellValue(subDescBuilder.toString());
                    subDescCell.setCellStyle(StyleUtil.createFont10VerticalTopStyle(wb));

                    mergedRegion(wb, sheet, cpuStartRowNum, sheet.getLastRowNum(), 0, 0);
                    mergedRegion(wb, sheet, cpuStartRowNum, !StringUtil.emptyOrNull(subByCPU.ioDescName) ? sheet.getLastRowNum() - 1 : sheet.getLastRowNum(), lastCol, lastCol);
                } else if (subByBase instanceof MaintainReportSubByESD) {
                    MaintainReportSubByESD subByESD = (MaintainReportSubByESD) subByBase;
                    int esdStartRowNum = sheet.getLastRowNum() + 1;
                    for (int n = -1; n < subByESD.esdItemValueList.size(); n++) {
                        HSSFRow esdHeaderRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        HSSFCell esd1Celll = createBaseCell(wb, esdHeaderRow, 1);
                        HSSFCell esd1Cell2 = createBaseCell(wb, esdHeaderRow, 2);
                        HSSFCell esd1Cell3 = createBaseCell(wb, esdHeaderRow, 3);
                        HSSFCell esd1Cell4 = createBaseCell(wb, esdHeaderRow, 4);
                        if (n < 0) {
                            esd1Celll.setCellValue(subByESD.cpuColumName1);
                            esd1Cell2.setCellValue(subByESD.cpuColumName2);
                            esd1Cell3.setCellValue(subByESD.cpuColumName3);
                            esd1Cell4.setCellValue(subByESD.cpuColumName4);
                        } else {
                            MaintainReportSubByESD.MaintainReportByESDItemValue esdItemValue = subByESD.esdItemValueList.get(n);
                            esd1Celll.setCellValue(esdItemValue.rowTitle);
                            esd1Cell2.setCellValue(esdItemValue.rowText1);
                            esd1Cell3.setCellValue(esdItemValue.rowText2);
                            esd1Cell4.setCellValue(esdItemValue.rowText3);
                        }
                    }
                    //设置名称并合并单元格
                    HSSFCell subTitleCell = createBaseCell(wb, sheet.getRow(esdStartRowNum), 0);
                    HSSFCell subDescCell = createBaseCell(wb, sheet.getRow(esdStartRowNum), 5);
                    subTitleCell.setCellValue(subByESD.esdTitle);
                    subTitleCell.setCellStyle(StyleUtil.createVerticalCenterStyle(wb));
                    subDescCell.setCellValue(subByESD.subDesc);
                    subTitleCell.setCellStyle(StyleUtil.createVerticalCenterStyle(wb));
                    mergedRegion(wb, sheet, esdStartRowNum, sheet.getLastRowNum(), 0, 0);
                    mergedRegion(wb, sheet, esdStartRowNum, sheet.getLastRowNum(), 5, 5);
                } else {
                    MaintainReportSubByNormal subByNormal = (MaintainReportSubByNormal) subByBase;
                    int normalStartRowNum = sheet.getLastRowNum() + 1;
                    for (int k = 0; k < subByNormal.normalItemValueList.size(); k++) {
                        MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue = subByNormal.normalItemValueList.get(k);
                        rowNum = sheet.getLastRowNum() + 1;
                        row = sheet.createRow(rowNum);
                        HSSFCell subCell0 = createBaseCell(wb, row, 0);
                        HSSFCell subCell1 = createBaseCell(wb, row, 1);
                        HSSFCell subCell5 = createBaseCell(wb, row, lastCol);//todo
                        short excelCellAutoHeight = StyleUtil.getExcelCellAutoHeight(normalItemValue.columDesc, 16, 23);
                        row.setHeight(excelCellAutoHeight);
                        subCell0.setCellValue(subByNormal.subNormalTitle);
                        subCell1.setCellValue(normalItemValue.columDesc);
                        subCell5.setCellValue(normalItemValue.columText);
                        mergedRegion(wb, sheet, rowNum, rowNum, 1, lastCol - 1);
                    }
                    //合并单元格
                    mergedRegion(wb, sheet, normalStartRowNum, sheet.getLastRowNum(), 0, 0);
                    mergedRegion(wb, sheet, normalStartRowNum, sheet.getLastRowNum(), lastCol, lastCol);
                }
            }

        }

        //维护结论
        int nextRowNum = sheet.getLastRowNum() + 1;
        HSSFRow nextRow = sheet.createRow(nextRowNum);
        HSSFCell nextCell = nextRow.createCell(0);
        mergedRegion(wb, sheet, nextRowNum, nextRowNum, 0, lastCol);
        nextRow.setHeight(StyleUtil.getRowHeight(160));
        nextCell.setCellValue(maintainReportByArea.maintainDescName + maintainReportByArea.maintainDescText);
        nextCell.setCellStyle(StyleUtil.createFont10VerticalTopStyle(wb));

        nextRowNum = sheet.getLastRowNum() + 1;
        nextRow = sheet.createRow(nextRowNum);
        nextCell = nextRow.createCell(0);
        nextRow.setHeight(StyleUtil.getRowHeight(160));
        mergedRegion(wb, sheet, nextRowNum, nextRowNum, 0, lastCol);
        nextCell.setCellValue(maintainReportByArea.maintainOtherName + maintainReportByArea.maintainOtherText);
        nextCell.setCellStyle(StyleUtil.createFont10VerticalTopStyle(wb));

        nextRowNum = sheet.getLastRowNum() + 1;
        nextRow = sheet.createRow(nextRowNum);
        nextCell = nextRow.createCell(0);
        nextRow.setHeight(StyleUtil.getRowHeight(160));
        mergedRegion(wb, sheet, nextRowNum, nextRowNum, 0, lastCol);
        nextCell.setCellValue(maintainReportByArea.stationConfirmName + maintainReportByArea.stationConfirmText);
        nextCell.setCellStyle(StyleUtil.createFont10VerticalTopStyle(wb));

        nextRowNum = sheet.getLastRowNum() + 1;
        HSSFRow nextRow11 = sheet.createRow(nextRowNum);
        HSSFCell nextCell00;
        HSSFCell nextCell11;
        HSSFCell nextCell12;
        HSSFCell nextCell13;
        if (isPingtai) {
            nextCell00 = createBaseCell(wb, nextRow11, 0);
            nextCell11 = createBaseCell(wb, nextRow11, 3);
            nextCell12 = createBaseCell(wb, nextRow11, 5);
            nextCell13 = createBaseCell(wb, nextRow11, lastCol);
            mergedRegion(wb, sheet, nextRowNum, nextRowNum, 0, 2);
            mergedRegion(wb, sheet, nextRowNum, nextRowNum, 3, 4);
            mergedRegion(wb, sheet, nextRowNum, nextRowNum, 5, 6);
        } else {
            nextCell00 = createBaseCell(wb, nextRow11, 0);
            nextCell11 = createBaseCell(wb, nextRow11, 3);
            nextCell12 = createBaseCell(wb, nextRow11, 4);
            nextCell13 = createBaseCell(wb, nextRow11, lastCol);
            mergedRegion(wb, sheet, nextRowNum, nextRowNum, 0, 2);
        }
        nextCell11.setCellValue(maintainReportByArea.stationConfirmPersonName);
        nextCell12.setCellValue(maintainReportByArea.stationConfirmPersonText);
        nextCell13.setCellValue(maintainReportByArea.stationConfirmDateName + maintainReportByArea.stationConfirmDateText);

        nextRowNum = sheet.getLastRowNum() + 2;
        HSSFRow bottomRow = sheet.createRow(nextRowNum);
        HSSFCell bottomCell0;
        HSSFCell bottomCell1;
        HSSFCell bottomCell4;
        HSSFCell bottomCell5;
        if (isPingtai) {
            bottomCell0 = createDescBoldNoBorderCell(wb, bottomRow, 0);
            bottomCell1 = createBaseNoBorderCell(wb, bottomRow, 1);
            bottomCell4 = createDescBoldNoBorderCell(wb, bottomRow, 4);
            bottomCell5 = createBaseNoBorderCell(wb, bottomRow, 6);
            mergedRegionNoBorder(wb, sheet, nextRowNum, nextRowNum, 4, 5);
            mergedRegionNoBorder(wb, sheet, nextRowNum, nextRowNum, 6, 7);
        } else {
            bottomCell0 = createDescBoldNoBorderCell(wb, bottomRow, 0);
            bottomCell1 = createBaseNoBorderCell(wb, bottomRow, 1);
            bottomCell4 = createDescBoldNoBorderCell(wb, bottomRow, 4);
            bottomCell5 = createBaseNoBorderCell(wb, bottomRow, 5);
        }
        bottomCell0.setCellValue(maintainReportByArea.productName);
        bottomCell1.setCellValue(maintainReportByArea.productText);
        bottomCell4.setCellValue(maintainReportByArea.checkerName);
        bottomCell5.setCellValue(maintainReportByArea.checkerText);

        if (isPingtai) {
            sheet.setColumnWidth(0, StyleUtil.getColumnWidth(11.3));
            sheet.setColumnWidth(1, StyleUtil.getColumnWidth(11));
            sheet.setColumnWidth(2, StyleUtil.getColumnWidth(10));
            sheet.setColumnWidth(3, StyleUtil.getColumnWidth(5.2));
            sheet.setColumnWidth(4, StyleUtil.getColumnWidth(5.2));
            sheet.setColumnWidth(5, StyleUtil.getColumnWidth(5.2));
            sheet.setColumnWidth(6, StyleUtil.getColumnWidth(5.2));
            sheet.setColumnWidth(7, StyleUtil.getColumnWidth(34.5));
        } else {
            sheet.setColumnWidth(0, StyleUtil.getColumnWidth(11.3));
            sheet.setColumnWidth(1, StyleUtil.getColumnWidth(11));
            sheet.setColumnWidth(2, StyleUtil.getColumnWidth(11));
            sheet.setColumnWidth(3, StyleUtil.getColumnWidth(10));
            sheet.setColumnWidth(4, StyleUtil.getColumnWidth(10));
            sheet.setColumnWidth(5, StyleUtil.getColumnWidth(34.5));
        }

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }


    public MaintainReportByAreaModel readReportType(InputStream open) throws IOException {
        MaintainReportByAreaModel mainAreaModel = new MaintainReportByAreaModel();
        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        HSSFCell tableCell = sheet1.getRow(0).getCell(0);
        mainAreaModel.tableName = tableCell.getStringCellValue();
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
                reportSubByCPU.cpuTitle = stringCellValue;
                reportSubByCPU.cpuCheckInfoMap.put("CPU机架运行（正常/故障）:", "");
                reportSubByCPU.cpuCheckInfoMap.put("冗余状态（正常/故障）:", "");
                reportSubByCPU.cpuCheckInfoMap.put("远程机架通讯（正常/故障）:", "");
                reportSubByCPU.cpuCheckInfoMap.put("以太网通讯（正常/故障）:", "");
                reportSubByCPU.cpuCheckInfoMap.put("机架背板供电（正常/故障）:", "");
                reportSubByCPU.cpuCheckInfoMap.put("其他事项:", "");

                if (mainAreaModel.tableName.contains("冀宁线")) {
                    reportSubByCPU.cpuColumName1 = "A机架";
                    reportSubByCPU.cpuColumName2 = "B机架";
                    reportSubByCPU.cpuColumName0 = "记录以下指示灯状态";
                } else {
                    reportSubByCPU.cpuColumName1 = "PLC-A";
                    reportSubByCPU.cpuColumName2 = "PLC-B";
                    reportSubByCPU.cpuColumName3 = "ESD-A";
                    reportSubByCPU.cpuColumName4 = "ESD-B";
                    reportSubByCPU.cpuColumName0 = "记录以下指示灯状态";
                }
                int[] rowHeightByIndex1 = getRowHeightByIndex(sheet, cell);//cpu机架的高度
                int k = rowHeightByIndex1[0];
                k++;//忽略第一行
                while (k <= rowHeightByIndex1[1]) {
                    HSSFRow cpuSubRow = sheet.getRow(k);
                    HSSFCell cpuSubCell = cpuSubRow.getCell(1);
                    int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuSubCell);
                    int cpuFirstRow = rowHeightByIndex[0];
                    int cpuLastRow = rowHeightByIndex[1];//CPU模块的高度
                    MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue = new MaintainReportSubByCPU.MaintainReportByCPUSubValue();
                    cpuSubValue.cpuSubName = cpuSubCell.getStringCellValue();
                    reportSubByCPU.cpuSubList.add(cpuSubValue);
                    for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                        HSSFRow lineRow = sheet.getRow(i);
                        HSSFCell cell1 = lineRow.getCell(2);
                        MaintainReportSubByCPU.MaintainReportByCPUItemValue cpuItemValue = new MaintainReportSubByCPU.MaintainReportByCPUItemValue();
                        cpuItemValue.subItemName = cell1.getStringCellValue();
                        cpuSubValue.cpuItemValueList.add(cpuItemValue);
                    }
                    k = rowHeightByIndex[1] + 1;
                }
            } else if (stringCellValue.equals("ESD系统（Himatrix）")) {
                MaintainReportSubByESD maintainReportByESD = new MaintainReportSubByESD(position++);
                scada.subList.add(maintainReportByESD);
                maintainReportByESD.esdTitle = stringCellValue;
                maintainReportByESD.cpuColumName1 = "记录指示灯";
                maintainReportByESD.cpuColumName2 = "F30";
                maintainReportByESD.cpuColumName3 = "IO1";
                maintainReportByESD.cpuColumName4 = "IO2";
                HSSFRow cpuRow = sheet.getRow(startRowNum);
                HSSFCell cpuCell = cpuRow.getCell(0);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuCell);
                int cpuFirstRow = rowHeightByIndex[0] + 1;
                int cpuLastRow = rowHeightByIndex[1];
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(i);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByESD.MaintainReportByESDItemValue cpuItemValue = new MaintainReportSubByESD.MaintainReportByESDItemValue();
                    cpuItemValue.rowTitle = cell1.getStringCellValue();
                    maintainReportByESD.esdItemValueList.add(cpuItemValue);
                }
            } else if (stringCellValue.equals("远程机架") && mainAreaModel.tableName.contains("平泰线")) {
                MaintainReportSubByCPU reportSubByCPU = new MaintainReportSubByCPU(position++);
                scada.subList.add(reportSubByCPU);
                reportSubByCPU.cpuTitle = stringCellValue;
                reportSubByCPU.cpuColumName1 = "PLC-1";
                reportSubByCPU.cpuColumName2 = "PLC-2";
                reportSubByCPU.cpuColumName3 = "ESD-1";
                reportSubByCPU.cpuColumName4 = "ESD-2";
                reportSubByCPU.cpuColumName0 = "记录以下指示灯状态";
                int[] rowHeightByIndex1 = getRowHeightByIndex(sheet, cell);//cpu机架的高度
                int k = rowHeightByIndex1[0];
                k++;//忽略第一行
                while (k <= rowHeightByIndex1[1]) {
                    HSSFRow cpuSubRow = sheet.getRow(k);
                    HSSFCell cpuSubCell = cpuSubRow.getCell(1);
                    int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuSubCell);
                    int cpuFirstRow = rowHeightByIndex[0];
                    int cpuLastRow = rowHeightByIndex[1];//CPU模块的高度
                    MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue = new MaintainReportSubByCPU.MaintainReportByCPUSubValue();
                    cpuSubValue.cpuSubName = cpuSubCell.getStringCellValue();
                    if (cpuSubValue.cpuSubName.equals("各IO模块运行情况")) {
                        reportSubByCPU.ioDescName = "各IO模块运行情况";
                        break;
                    }
                    reportSubByCPU.cpuSubList.add(cpuSubValue);
                    for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                        HSSFRow lineRow = sheet.getRow(i);
                        HSSFCell cell1 = lineRow.getCell(2);
                        MaintainReportSubByCPU.MaintainReportByCPUItemValue cpuItemValue = new MaintainReportSubByCPU.MaintainReportByCPUItemValue();
                        cpuItemValue.subItemName = cell1.getStringCellValue();
                        cpuSubValue.cpuItemValueList.add(cpuItemValue);
                    }
                    k = rowHeightByIndex[1] + 1;
                }
            } else {
                MaintainReportSubByNormal maintainReportSubByNormal = new MaintainReportSubByNormal(position++);
                scada.subList.add(maintainReportSubByNormal);
                HSSFRow normalRow = sheet.getRow(startRowNum);
                HSSFCell normalCell = normalRow.getCell(0);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, normalCell);
                int cpuFirstRow = rowHeightByIndex[0];
                int cpuLastRow = rowHeightByIndex[1];
                maintainReportSubByNormal.subNormalTitle = normalCell.getStringCellValue();
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(i);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue = new MaintainReportSubByNormal.MaintainReportSubByNormalItemValue();
                    normalItemValue.columDesc = cell1.getStringCellValue();
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

    public String checkInfo(ReportBuildModel buildModel) {
        MaintainReportByAreaModel maintainReportByArea = buildModel.maintainReportByArea;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(maintainReportByArea.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(maintainReportByArea.checkerText)) {
            builder.append("补全测试人，");
        }
        return builder.toString();
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
            Integer[] integers1 = new Integer[]{3, 8};
            Integer[] integers2 = new Integer[]{9, 45};
            Integer[] integers3 = new Integer[]{46, 51};
            allRowList.add(integers1);
            allRowList.add(integers2);
            allRowList.add(integers3);
        }
        return allRowList;
    }

}

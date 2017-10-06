package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportSubTypeModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportTypeModel;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
public class BuildType2Service extends BuildTypeBaseService {

    public BuildType2Service() {

    }

    public InspectionReportModel readReportTypeTwo(InputStream in) throws IOException {
        InspectionReportModel inspectionReportModel = new InspectionReportModel();
        HSSFWorkbook wb = new HSSFWorkbook(in);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        HSSFRow row = sheet1.getRow(0);
        HSSFCell cell = row.getCell(0);
        //title
        String tableTitle = cell.getStringCellValue();
        inspectionReportModel.tableName = tableTitle;

        List<InspectionReportTypeModel> firstColumList = getTypeModelList(sheet1);
        for (InspectionReportTypeModel typeModel : firstColumList) {
            List<InspectionReportSubTypeModel> subTypeModelList = getSubTypeModelList(sheet1, typeModel);
            typeModel.subTypeModelList.addAll(subTypeModelList);
            for (InspectionReportSubTypeModel subTypeModel : subTypeModelList) {
                getSubTypeModelValue(sheet1, subTypeModel);
            }
        }
        inspectionReportModel.typeModelList.addAll(firstColumList);
        return inspectionReportModel;
    }


    private List<InspectionReportTypeModel> getTypeModelList(Sheet sheet) {
        List<InspectionReportTypeModel> reportTypeModelList = new ArrayList<>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            if (firstColumn != 0 || lastColumn != 0) {
                continue;
            }
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            Cell cell = sheet.getRow(firstRow).getCell(0);
            String typeName = cell.getStringCellValue();
            InspectionReportTypeModel typeModel = new InspectionReportTypeModel();
            typeModel.firstRow = firstRow;
            typeModel.lastRow = lastRow;
            typeModel.typeName = typeName;
            reportTypeModelList.add(typeModel);
        }
        return reportTypeModelList;
    }

    private List<InspectionReportSubTypeModel> getSubTypeModelList(Sheet sheet, InspectionReportTypeModel typeModel) {
        List<InspectionReportSubTypeModel> reportSubTypeModelList = new ArrayList<>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            if (firstColumn != 2 || lastColumn != 2) {
                continue;
            }
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (!(firstRow >= typeModel.firstRow && lastRow <= typeModel.lastRow)) {
                continue;
            }

            Cell cell = sheet.getRow(firstRow).getCell(2);
            String subTypeName = cell.getStringCellValue();
            InspectionReportSubTypeModel reportSubTypeModel = new InspectionReportSubTypeModel();
            reportSubTypeModel.firstRow = firstRow;
            reportSubTypeModel.lastRow = lastRow;
            reportSubTypeModel.subTypeName = subTypeName;
            reportSubTypeModelList.add(reportSubTypeModel);
        }
        return reportSubTypeModelList;
    }

    private void getSubTypeModelValue(Sheet sheet1, InspectionReportSubTypeModel subTypeModel) {
        int firstRow = subTypeModel.firstRow;
        int lastRow = subTypeModel.lastRow;
        for (int i = firstRow; i <= lastRow; i++) {
            Row row = sheet1.getRow(i);
            Cell cell = row.getCell(3);
            InspectionReportSubTypeModel.InspectionReportCellModel cellModel = new InspectionReportSubTypeModel.InspectionReportCellModel();
            cellModel.requireDesc = cell.getStringCellValue();
            subTypeModel.cellModelList.add(cellModel);
        }
    }

    public void writeReport(File outFile, ReportBuildModel buildModel, BuildResultInter inter) throws IOException {
        InspectionReportModel inspectionReportModel = buildModel.inspectionReportModel;
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(inspectionReportModel.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        HSSFCell areaCell = areaRow.createCell(0);
        HSSFCell stationCell = areaRow.createCell(4);
        areaCell.setCellValue(inspectionReportModel.workAreaName + ":" + inspectionReportModel.workAreaText);
        stationCell.setCellValue(inspectionReportModel.stationName + ":" + inspectionReportModel.stationText);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        if (inspectionReportModel.typeModelList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < inspectionReportModel.typeModelList.size(); i++) {
            String typeCellStr = "类别/专业";
            String positionCellStr = "序号";
            String equipmentTypeCellStr = "设备类别";
            String requireCellStr = "现场标准/要求";
            String checkrecord = "设备编号";
            String checkdesc = "检查与维护保养情况";
            if (i == -1) {
                HSSFRow headerRow = sheet.createRow(i + 3);
                HSSFCell typeCellCell = headerRow.createCell(0);
                HSSFCell positionCellCell = headerRow.createCell(1);
                HSSFCell equipmentTypeCell = headerRow.createCell(2);
                HSSFCell requireCell = headerRow.createCell(3);
                HSSFCell checkrecordCell = headerRow.createCell(4);
                HSSFCell checkdescCell = headerRow.createCell(5);
                typeCellCell.setCellValue(typeCellStr);
                positionCellCell.setCellValue(positionCellStr);
                equipmentTypeCell.setCellValue(equipmentTypeCellStr);
                requireCell.setCellValue(requireCellStr);
                checkrecordCell.setCellValue(checkrecord);
                checkdescCell.setCellValue(checkdesc);
                continue;
            }
            InspectionReportTypeModel typeModel = inspectionReportModel.typeModelList.get(i);
            typeCellStr = typeModel.typeName;
            int startRomNum = sheet.getLastRowNum() + 1;
            for (int j = 0; j < typeModel.subTypeModelList.size(); j++) {
                InspectionReportSubTypeModel subTypeModel = typeModel.subTypeModelList.get(j);
                equipmentTypeCellStr = subTypeModel.subTypeName;
                if (subTypeModel.isNotCreate) {
                    continue;
                }
                int startSubRomNum = sheet.getLastRowNum() + 1;
                for (int k = 0; k < typeModel.subTypeModelList.size(); k++) {
                    InspectionReportSubTypeModel.InspectionReportCellModel cellModel = subTypeModel.cellModelList.get(k);
                    HSSFRow headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    HSSFCell typeCell = headerRow.createCell(0);
                    HSSFCell positionCellCell = headerRow.createCell(1);
                    HSSFCell equipmentTypeCell = headerRow.createCell(2);
                    HSSFCell requireCell = headerRow.createCell(3);
                    HSSFCell checkrecordCell = headerRow.createCell(4);
                    HSSFCell checkdescCell = headerRow.createCell(5);

                    typeCell.setCellValue(typeCellStr);
                    positionCellCell.setCellValue(sheet.getLastRowNum() - 2);
                    equipmentTypeCell.setCellValue(equipmentTypeCellStr);
                    requireCell.setCellValue(cellModel.requireDesc);
                    checkrecordCell.setCellValue(cellModel.checkRecord);
                    checkdescCell.setCellValue(cellModel.checkDesc);
                }
                sheet.addMergedRegion(new CellRangeAddress(startSubRomNum, sheet.getLastRowNum(), 2, 2));
            }
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(startRomNum, sheet.getLastRowNum(), 0, 0));
        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        HSSFCell checkerCell = bottomRow.createCell(1);
        HSSFCell dataCell = bottomRow.createCell(4);
        checkerCell.setCellValue(inspectionReportModel.checkerText);
        dataCell.setCellValue(inspectionReportModel.dateText);
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 4, 5));

        sheet.setColumnWidth(0, StyleUtil.getColumnWidth(5));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(8));
        sheet.setColumnWidth(3, StyleUtil.getColumnWidth(52));
        sheet.setColumnWidth(4, StyleUtil.getColumnWidth(8));
        sheet.setColumnWidth(5, StyleUtil.getColumnWidth(25));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }

    public String checkInfo(ReportBuildModel buildModel) {
        InspectionReportModel inspectionReportModel = buildModel.inspectionReportModel;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(inspectionReportModel.workAreaText)) {
            builder.append("作业区，");
        }
        if (StringUtil.emptyOrNull(inspectionReportModel.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(inspectionReportModel.checkerText)) {
            builder.append("补全检查人，");
        }
        return builder.toString();
    }

}

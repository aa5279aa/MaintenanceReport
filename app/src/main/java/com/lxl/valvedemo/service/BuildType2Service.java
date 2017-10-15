package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
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
        titleCell.setCellStyle(StyleUtil.createTitleSmallFontStyle(wb));
        titleRow.setHeight(StyleUtil.getRowHeight(28.5));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        areaRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell areaCell = areaRow.createCell(0);
        HSSFCell bolderCell = areaRow.createCell(3);
        HSSFCell stationCell = areaRow.createCell(4);
        areaCell.setCellValue(inspectionReportModel.workAreaName + ":" + inspectionReportModel.workAreaText);
        stationCell.setCellValue(inspectionReportModel.stationName + ":" + inspectionReportModel.stationText);
        areaCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        bolderCell.setCellStyle(StyleUtil.createBorderStyle(wb));
        stationCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        mergedRegion(wb, sheet, 1, 1, 0, 2);
        mergedRegion(wb, sheet, 1, 1, 4, 5);

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
            String checkrecord = "检查记录";
            String checkdesc = "备注";
            if (i == -1) {
                HSSFRow headerRow = sheet.createRow(i + 3);
                headerRow.setHeight(StyleUtil.getRowHeight(28.5));
                HSSFCell typeCellCell = createDescCenterFontCell(wb, headerRow, 0);
                HSSFCell positionCellCell = createDescCenterFontCell(wb, headerRow, 1);
                HSSFCell equipmentTypeCell = createDescCenterFontCell(wb, headerRow, 2);
                HSSFCell requireCell = createDescCenterFontCell(wb, headerRow, 3);
                HSSFCell checkrecordCell = createDescCenterFontCell(wb, headerRow, 4);
                HSSFCell checkdescCell = createDescCenterFontCell(wb, headerRow, 5);
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
                for (int k = 0; k < subTypeModel.cellModelList.size(); k++) {
                    InspectionReportSubTypeModel.InspectionReportCellModel cellModel = subTypeModel.cellModelList.get(k);
                    HSSFRow headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    HSSFCell typeCell = createDescBoldCell(wb, headerRow, 0);
                    HSSFCell positionCellCell = createBaseCell(wb, headerRow, 1);
                    HSSFCell equipmentTypeCell = createBaseCell(wb, headerRow, 2);
                    HSSFCell requireCell = createBaseCell(wb, headerRow, 3);//
                    HSSFCell checkrecordCell = createBaseCell(wb, headerRow, 4);//
                    HSSFCell checkdescCell = createBaseCell(wb, headerRow, 5);

                    short excelCellAutoHeight = StyleUtil.getExcelCellAutoHeight(cellModel.requireDesc, 14, 25);
                    short excelCellAutoHeight1 = StyleUtil.getExcelCellAutoHeight(cellModel.checkRecord, 14, 25);
                    excelCellAutoHeight = excelCellAutoHeight > excelCellAutoHeight1 ? excelCellAutoHeight : excelCellAutoHeight1;
                    headerRow.setHeight(excelCellAutoHeight);

                    typeCell.setCellValue(typeCellStr);
                    positionCellCell.setCellValue(sheet.getLastRowNum() - 2);
                    equipmentTypeCell.setCellValue(equipmentTypeCellStr);
                    requireCell.setCellValue(cellModel.requireDesc);
                    checkrecordCell.setCellValue(cellModel.checkRecord);
                    checkdescCell.setCellValue(cellModel.checkDesc);
                }
                mergedRegion(wb, sheet, startSubRomNum, sheet.getLastRowNum(), 2, 2);
            }
            if (startRomNum >= sheet.getLastRowNum()) {
                continue;
            }
            //合并单元格
            mergedRegion(wb, sheet, startRomNum, sheet.getLastRowNum(), 0, 0);
        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        bottomRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell checkCell = bottomRow.createCell(0);
        checkCell.setCellStyle(StyleUtil.createFont10BoldCenterNoBorderStyle(wb));
        checkCell.setCellValue("检查人：" + inspectionReportModel.checkerText + "      " + "确认人：" + inspectionReportModel.checkerText + "      " + "日期：" + inspectionReportModel.dateText);
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 0, 5));

        sheet.setColumnWidth(0, StyleUtil.getColumnWidth(5));
        sheet.setColumnWidth(1, StyleUtil.getColumnWidth(3));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(10));
        sheet.setColumnWidth(3, StyleUtil.getColumnWidth(52));
        sheet.setColumnWidth(4, StyleUtil.getColumnWidth(10));
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

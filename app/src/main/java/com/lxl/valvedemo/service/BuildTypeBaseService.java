package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type6.ReportModelType6;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/10/4 0004.
 */

public abstract class BuildTypeBaseService {

    abstract public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException;


    public int[] getRowHeightByIndex(Sheet sheet, HSSFCell cell) {
        int[] merge = new int[2];
        merge[0] = cell.getRowIndex();
        merge[1] = cell.getRowIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            int columnIndex = cell.getColumnIndex();
            int rowIndex = cell.getRowIndex();
            if (firstColumn == columnIndex && firstRow == rowIndex) {
                merge[0] = firstRow;
                merge[1] = lastRow;
                return merge;
            }
        }
        return merge;
    }

    public CellRangeAddress getMergeByIndex(Sheet sheet, HSSFCell cell) {
        int[] merge = new int[2];
//        merge[0] = cell.getRowIndex();
//        merge[1] = cell.getRowIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            int columnIndex = cell.getColumnIndex();
            int rowIndex = cell.getRowIndex();
            if (firstColumn == columnIndex && firstRow == rowIndex) {
                return range;
            }
        }
        return null;
    }

    public abstract String checkInfo(ReportBuildModel buildModel);
}

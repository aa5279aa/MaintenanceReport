package com.lxl.valvedemo.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class StyleUtil {

    //创造头部样式
    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 16);
        setBorder.setFont(font);
        return setBorder;
    }

    //创造描述样式
    public static HSSFCellStyle createDescStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);
        setBorder.setFont(font);
        return setBorder;
    }

    //创造自动换行样式
    public static HSSFCellStyle createWrapTextStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setWrapText(true);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        setBorder.setFont(font);
        return setBorder;
    }

    //创造基础cell样式
    public static HSSFCellStyle createNormalStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        setBorder.setFont(font);

        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        return setBorder;
    }

    //创造基础cell样式
    public static HSSFCellStyle createVerticalCenterBStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.VERTICAL_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);
        setBorder.setFont(font);
        return setBorder;
    }

    /**
     * 竖向居中
     *
     * @param wb
     * @return
     */
    public static HSSFCellStyle createVerticalCenterStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        return setBorder;
    }

    /**
     * @param wb
     * @return
     */
    public static HSSFCellStyle createVerticalTopStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_TOP);//垂直居中
        return setBorder;
    }

    //创造基础cell样式
    public static HSSFCellStyle createHCenterBStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);
        setBorder.setFont(font);
        return setBorder;
    }

    //创造基础cell样式
    public static HSSFCellStyle createHCenterStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return setBorder;
    }

    //创造基础cell样式
    public static HSSFCellStyle createRightCenterStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);
        setBorder.setFont(font);
        return setBorder;
    }

    //竖向展示
    public static HSSFCellStyle createVerticalShowStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setRotation((short) 255);
        return setBorder;
    }

    public static int getColumnWidth(double width) {
        return (int) (256 * width + 184);
    }

    public static short getRowHeight(short height) {
        return (short) (20 * height);
    }

}

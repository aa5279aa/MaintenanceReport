package com.lxl.valvedemo.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.regex.Pattern;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class StyleUtil {

    //创造基础描述的样式
    public static HSSFCellStyle createFont8LeftStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 8);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }


    //创造基础描述的样式
    public static HSSFCellStyle createFont10LeftStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    //创造基础描述的样式
    public static HSSFCellStyle createFont10BoldLeftStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 10);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }


    //创造基础描述的样式
    public static HSSFCellStyle createFont10CenterStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        setBorder.setFont(font);
        setBorder.setAlignment(CellStyle.ALIGN_CENTER);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    //创造描述样式
    public static HSSFCellStyle createFont12BoldLeftStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 12);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    //创造描述样式不加粗
    public static HSSFCellStyle createFont12LeftStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    //创造描述样式
    public static HSSFCellStyle createFont12BoldCenterStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 12);
        setBorder.setFont(font);
        setBorder.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    public static HSSFCellStyle createFont11BoldLeft(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 11);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }

    public static HSSFCellStyle createFont11Center(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        setBorder.setFont(font);
        setBorder.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        setBorder.setWrapText(true);
        return setBorder;
    }


    //创造自动换行样式
    public static HSSFCellStyle createWrapTextStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setWrapText(true);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)
                12);
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

    /**
     * 头部样式
     */
    public static HSSFCellStyle createTitleBigFontStyle(HSSFWorkbook wb) {
        return createTitleStyle(wb, 20);
    }

    //创造头部小字体样式
    public static HSSFCellStyle createTitleSmallFontStyle(HSSFWorkbook wb) {
        return createTitleStyle(wb, 16);
    }


    //创造头部体样式
    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb, int fontSize) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) fontSize);
        setBorder.setFont(font);
        setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        return setBorder;
    }

    public static float getExcelCellAutoHeight(String str, float defaultRowHeight, float fontCountInline) {
        float defaultCount = 0.00f;
        for (int i = 0; i < str.length(); i++) {
            float ff = getregex(str.substring(i, i + 1));
            defaultCount = defaultCount + ff;
        }
        return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;//计算
    }

    public static float getregex(String charStr) {

        if (charStr == " ") {
            return 0.5f;
        }
        // 判断是否为字母或字符
        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
            return 0.5f;
        }
        // 判断是否为全角

        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
            return 1.00f;
        }
        //全角符号 及中文
        if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
            return 1.00f;
        }
        return 0.5f;

    }


    public static int getColumnWidth(double width) {
        return (int) (256 * width + 184);
    }

    public static short getRowHeight(double height) {
        return (short) (20 * height);
    }

}

package com.lxl.valvedemo.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class StyleUtil {

    //创造各种样式
    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 24);
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return setBorder;
    }

    //创造各种样式
    public  static HSSFCellStyle createDescStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);
        return setBorder;
    }

}

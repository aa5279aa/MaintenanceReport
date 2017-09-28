package com.lxl.valvedemo.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class StyleUtil {

    //创造各种样式
    public HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle setBorder = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        return setBorder;
    }

}

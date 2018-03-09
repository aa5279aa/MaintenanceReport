package com.lxl.valvedemo.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PoiHelper {
    public static String poiWrite(String[][] str, String filepath) {

        // 判断后缀名，后缀名不同，生成的流不同

        if (filepath.endsWith("xlsx")) {
//			poiwriteXlsx(str, filepath);
            return "不支持的文件格式";
        } else {
            return poiWriteXls(str, filepath);
        }
    }

    //	// HSSF对应的是xls格式
    private static String poiWriteXls(String[][] str, String filepath) {
        InputStream inp;
        ifexist(filepath);
        try {
            inp = new FileInputStream(filepath);
            int rownum = str.length;
            int columnum = str[0].length;

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("sheet1");
            for (int i = 0; i < rownum; i++) {
                // System.out.println("i:"+i);
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
                for (int j = 0; j < columnum; j++) {
                    /* System.out.println("j:"+j); */
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(j);
                    // 设置格式
                    cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                    // 设置值
                    cell.setCellValue(str[i][j]);
                }
            }
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(filepath);
            wb.write(fileOut);
            fileOut.close();
            inp.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            StringBuilder builder = new StringBuilder();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                builder.append(element.toString() + "\n");
            }
            return builder.toString();
        }
        return "success";
    }

    // XSSF对应的是xlsx格式
    private static void poiwriteXlsx(String[][] str, String filepath) {
//		InputStream inp;
//		ifexist(filepath);
//		try {
//			inp = new FileInputStream(filepath);
//			int rownum = str.length;
//			int columnum = str[0].length;
//
//			XSSFWorkbook wb = new XSSFWorkbook();
//			XSSFSheet sheet = wb.createSheet("sheet1");
//			for (int i = 0; i < rownum; i++) {
//				// System.out.println("i:"+i);
//				Row row = sheet.createRow(i);
//				for (int j = 0; j < columnum; j++) {
//					/* System.out.println("j:"+j); */
//					Cell cell = row.createCell(j);
//					// 设置格式
//					cell.setCellType(Cell.CELL_TYPE_STRING);
//					// 设置值
//					cell.setCellValue(str[i][j]);
//				}
//			}
//			// Write the output to a file
//			FileOutputStream fileOut = new FileOutputStream(filepath);
//			wb.write(fileOut);
//			fileOut.close();
//			inp.close();
//			System.out.println("写入完成");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    // 传入文件的地址，判断文件是否存在，如果不存在的话创建该文件
    // 这个功能好像还存在一个小BUG，直接createNewFile();的文件不能用，以后找方法解决。
    public static void ifexist(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("文件不存在，创建该文件，文件地址为：" + path);
                file.createNewFile();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

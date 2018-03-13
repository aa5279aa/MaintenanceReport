package com.lxl.maintenance.util;

/**
 * Created by xiangleiliu on 2017/5/4.
 */
public class NumberUtil {

    public static double[] getLongAndLat(String input_coordinate) {
        double[] coordinate = new double[2];
        try {
            String[] split = input_coordinate.split("_");
            if (split != null && split.length >= 2) {
                coordinate[0] = Double.parseDouble(split[0]);
                coordinate[1] = Double.parseDouble(split[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinate;
    }

    public static int string2Int(String str) {
        try {
            int i = Integer.parseInt(str.trim());
            return i;
        } catch (Exception e) {

        }
        return 0;
    }
}

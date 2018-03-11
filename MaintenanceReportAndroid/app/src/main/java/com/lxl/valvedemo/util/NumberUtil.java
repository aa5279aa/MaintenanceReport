package com.lxl.valvedemo.util;

/**
 * Created by Administrator on 2017/10/6 0006.
 */

public class NumberUtil {

    public static int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }

    }

    public static String formatDouble(double d) {
        return formatDouble(d, 5);
    }

    public static String formatDouble(double d, int digit) {
        String result = String.format("%." + digit + "f");
        return result;
    }

}

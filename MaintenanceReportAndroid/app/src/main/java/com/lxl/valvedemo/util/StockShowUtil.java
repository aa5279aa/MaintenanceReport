package com.lxl.valvedemo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by xiangleiliu on 2017/8/5.
 */
public class StockShowUtil {

    public static void showToastOnMainThread(final Context context, final CharSequence msg) {
        if (TextUtils.isEmpty(msg) || context == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }



}

package com.lxl.valvedemo.application;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by xiangleiliu on 2017/4/24.
 */
public class MyApplication extends Application {
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
//        BlockCanary.init(this, 30);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        SDKInitializer.initialize(getApplicationContext());
    }
}

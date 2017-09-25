package com.lxl.valvedemo.application;

import android.app.Application;

/**
 * Created by xiangleiliu on 2017/4/24.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        BlockCanary.init(this, 30);
    }
}

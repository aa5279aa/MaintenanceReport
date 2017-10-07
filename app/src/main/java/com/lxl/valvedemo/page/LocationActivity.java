package com.lxl.valvedemo.page;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;
import com.lxl.valvedemo.util.DateUtil;

/**
 * Created by Administrator on 2017/10/7 0007.
 */

public class LocationActivity extends Activity {
    LocationClient mClient;

    Handler mHander = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        final TextView locationText = (TextView) findViewById(R.id.location);

        final StringBuilder builder = new StringBuilder();
        mClient = new LocationClient(getApplicationContext());
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.i("lxltest", "location,lat:" + bdLocation.getLatitude() + ",long:" + bdLocation.getLongitude() + ",address:" + bdLocation.getAddrStr());
                LocationRecordModel recordModel = new LocationRecordModel();
                recordModel.latitude = bdLocation.getLatitude();
                recordModel.longitude = bdLocation.getLongitude();
                recordModel.addressText = bdLocation.getAddrStr();
                recordModel.cityText = bdLocation.getCity();
                recordModel.dataText = DateUtil.getCurrentTime();
                builder.append("时间：" + recordModel.dataText);
                builder.append("，地点：" + recordModel.addressText);
                builder.append("，经度：" + recordModel.latitude);
                builder.append("，纬度：" + recordModel.longitude);
                builder.append("\n");
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        locationText.setText(builder.toString());
                    }
                });
            }
        };
        mClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(10 * 1000);
        option.setIsNeedAddress(true);
        mClient.setLocOption(option);
        mClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.stop();
    }
}

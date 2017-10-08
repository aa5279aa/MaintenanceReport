package com.lxl.valvedemo.page;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.TableConfig;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.util.DeviceUtil;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.StockTitleView;

public class SelectActivity extends Activity {
    Context context;
    public final static String SELECT_MODEL = "SELECT_MODEL";
    private static final int BAIDU_READ_PHONE_STATE = 100;

    SingleSelectionModel singleSelctionEntity;
    SingleSelectionModel selectSingleSelctionEntity;

    private LinearLayout selectGroup;
    private StockTitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("lxltest", "end time:" + System.currentTimeMillis());
        this.context = this;
        setContentView(R.layout.activity_select);
        getElement();
        initData();
        initView();
    }

    private void getElement() {
        selectGroup = (LinearLayout) findViewById(R.id.radio_group);
        titleView = (StockTitleView) findViewById(R.id.stock_title_view);
    }

    private void initView() {
        if (StringUtil.emptyOrNull(singleSelctionEntity.itemStr)) {
            titleView.setTitle("中原输油气分公司");
        } else {
            titleView.setTitle(singleSelctionEntity.itemStr);
        }

        int btnwidth = DeviceUtil.getScreenWidth(this) * 4 / 5;
        for (int i = 0; i < singleSelctionEntity.selectList.size(); i++) {
            SingleSelectionModel sonSingleSelctionEntity = singleSelctionEntity.selectList.get(i);
            Button button = new Button(context);
            button.setBackgroundResource(R.drawable.select_button_bg);
            int pixelFromDip = DeviceUtil.getPixelFromDip(this, 30);
            button.setPadding(pixelFromDip, pixelFromDip, pixelFromDip, pixelFromDip);
            button.setId(i);
            button.setText(sonSingleSelctionEntity.itemStr);
            button.setTag(sonSingleSelctionEntity);
            button.setTextAppearance(this, R.style.text_18_ffffff);
            button.setSingleLine(false);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(btnwidth, -2);
            selectGroup.addView(button, lp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectSingleSelctionEntity = (SingleSelectionModel) v.getTag();
                    if (selectSingleSelctionEntity.isCanJump) {
                        gotoReportActivityForCheck(selectSingleSelctionEntity);
                    } else if (selectSingleSelctionEntity.isCanSelect) {
                        gotoSelectActivity(selectSingleSelctionEntity);
                    } else {
                        Toast.makeText(context, "没有对应的选项", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initData() {
        Intent intent = getIntent();
        singleSelctionEntity = (SingleSelectionModel) intent.getSerializableExtra(SELECT_MODEL);
        if (singleSelctionEntity == null) {
            TableConfig instance = TableConfig.getInstance(this);
            singleSelctionEntity = instance.mSelectionModel;
        }
        Log.i("lxltest", "singleSelctionEntity:" + singleSelctionEntity);

    }

    private void gotoSelectActivity(SingleSelectionModel sonSingleSelctionEntity) {
        Intent intent = new Intent();
        intent.setClass(context, SelectActivity.class);
        intent.putExtra(SELECT_MODEL, sonSingleSelctionEntity);
        startActivity(intent);
    }

    private void gotoReportActivityForCheck(SingleSelectionModel sonSingleSelctionEntity) {
        if (Build.VERSION.SDK_INT < 23) {
            gotoReportActivity(sonSingleSelctionEntity);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        } else {
            gotoReportActivity(sonSingleSelctionEntity);
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    gotoReportActivity(selectSingleSelctionEntity);
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //先权限检查，然后进界面
    private void gotoReportActivity(SingleSelectionModel sonSingleSelctionEntity) {
        Intent intent = new Intent();
        intent.setClass(context, ReportRecordActivity.class);
        intent.putExtra(ReportRecordActivity.SelectModel, sonSingleSelctionEntity);
        startActivity(intent);
    }


}

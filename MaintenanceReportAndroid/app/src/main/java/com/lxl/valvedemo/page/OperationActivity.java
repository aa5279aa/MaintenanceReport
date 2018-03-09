package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.util.DeviceUtil;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class OperationActivity extends Activity {

    LinearLayout btnContainer;
    Button selectBtn;
    Button makeCamera;
    Button locationBtn;
    int TAKE_PICTURE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        initView();
        initListener();
    }

    private void initListener() {
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OperationActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });
        makeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
            }
        });
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OperationActivity.this, TrajectoryShowActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OperationActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView() {
        selectBtn = (Button) findViewById(R.id.select_btn);
        makeCamera = (Button) findViewById(R.id.make_camera);
        locationBtn = (Button) findViewById(R.id.location);
        btnContainer = (LinearLayout) findViewById(R.id.btn_container);
        //统一button样式
        int width = DeviceUtil.getScreenWidth(this) * 4 / 5;
        for (int i = 0; i < btnContainer.getChildCount(); i++) {
            View childAt = btnContainer.getChildAt(i);
            if (childAt instanceof Button) {
                Button button = (Button) childAt;
                button.getLayoutParams().width = width;
                button.setBackgroundResource(R.drawable.select_button_bg);
                button.setTextAppearance(this, R.style.text_18_ffffff);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

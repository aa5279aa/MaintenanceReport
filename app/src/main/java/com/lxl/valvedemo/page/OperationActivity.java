package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.lxl.valvedemo.R;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class OperationActivity extends Activity {

    Button selectBtn;

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
    }

    private void initView() {
        selectBtn = (Button) findViewById(R.id.select_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

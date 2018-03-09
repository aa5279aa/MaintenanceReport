package com.lxl.valvedemo.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lxl.valvedemo.R;

/**
 * Created by Administrator on 2017/10/8 0008.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

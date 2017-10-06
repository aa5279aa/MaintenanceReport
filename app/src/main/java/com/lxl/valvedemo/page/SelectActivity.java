package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.TableConfig;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;

public class SelectActivity extends Activity {
    Context context;
    public final static String SELECT_MODEL = "SELECT_MODEL";
    SingleSelectionModel singleSelctionEntity;

    private LinearLayout selectGroup;
    private TextView keyText;

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
        keyText = (TextView) findViewById(R.id.key_text);
    }

    private void initView() {
        keyText.setText("xxxxxx");
        for (int i = 0; i < singleSelctionEntity.selectList.size(); i++) {
            SingleSelectionModel sonSingleSelctionEntity = singleSelctionEntity.selectList.get(i);
            Button button = new Button(context);
            button.setId(i);
            button.setText(sonSingleSelctionEntity.itemStr);
            button.setTextColor(Color.BLACK);
            button.setTag(sonSingleSelctionEntity);
            selectGroup.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingleSelectionModel sonSingleSelctionEntity = (SingleSelectionModel) v.getTag();
                    if (sonSingleSelctionEntity.isCanJump) {
                        gotoReportActivity(sonSingleSelctionEntity);
                    } else if (sonSingleSelctionEntity.isCanSelect) {
                        gotoSelectActivity(sonSingleSelctionEntity);
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

    private void gotoReportActivity(SingleSelectionModel sonSingleSelctionEntity) {
        Intent intent = new Intent();
        intent.setClass(context, ReportRecordActivity.class);
        intent.putExtra(ReportRecordActivity.SelectModel, sonSingleSelctionEntity);
        startActivity(intent);
    }

}

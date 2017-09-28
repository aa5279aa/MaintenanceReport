package com.lxl.valvedemo.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.valvedemo.BaseActivity;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.TableConfig;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;

public class SelectActivity extends BaseActivity {
    Context context;
    public final static String SELECT_MODEL = "SELECT_MODEL";
    SingleSelectionModel singleSelctionEntity;

    private RadioGroup radioGroup;
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
        setParentContentView();
    }

    private void getElement() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        keyText = (TextView) findViewById(R.id.key_text);
    }

    private void initView() {
        keyText.setText("xxxxxx");
        for (int i = 0; i < singleSelctionEntity.selectList.size(); i++) {
            SingleSelectionModel sonSingleSelctionEntity = singleSelctionEntity.selectList.get(i);
            RadioButton button = new RadioButton(context);
            button.setId(i);
            button.setText(sonSingleSelctionEntity.itemStr);
            button.setTextColor(Color.BLACK);
            button.setTag(sonSingleSelctionEntity);
            radioGroup.addView(button);
        }

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton button = (RadioButton) group.getChildAt(checkedRadioButtonId);
                SingleSelectionModel sonSingleSelctionEntity = (SingleSelectionModel) button.getTag();
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

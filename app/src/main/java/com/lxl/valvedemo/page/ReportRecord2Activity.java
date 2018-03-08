package com.lxl.valvedemo.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.IOHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.lxl.valvedemo.page.SelectActivity.SELECT_MODEL;

/**
 * Created by xiangleiliu on 2018/3/8.
 */

public class ReportRecord2Activity extends FragmentActivity {
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    EditText mReportHeaderArea, mReportHeaderStation, mReportHeaderChecker, mReportHeaderDate;
    LinearLayout mReportRecordCcontainer;

    SingleSelectionModel mSelectModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);
        initView();
        initData();
        bindData();
    }

    private void initView() {
        mGoTop = (Button) findViewById(R.id.go_top_btn);
        mSubmit = (Button) findViewById(R.id.submit);

        scrollView = (ScrollView) findViewById(R.id.report_record2_fill);
        mReportHeaderArea = (EditText) findViewById(R.id.report_header_area);
        mReportHeaderStation = (EditText) findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) findViewById(R.id.report_header_checker);
        mReportHeaderDate = (EditText) findViewById(R.id.report_header_date);
        mReportRecordCcontainer = (LinearLayout) findViewById(R.id.report_record_container);
    }


    private void bindData() {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        try {
            InputStream open = getAssets().open(mSelectModel.path + ReportBuildConfig.Location_Suffix);
            List<String> strings = IOHelper.readListStrByCode(open, "utf-8");
            for (int i = 0; i < strings.size(); i++) {
                TextView text = new TextView(this);
                text.setText(strings.get(i));
                mReportRecordCcontainer.addView(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        Intent intent = getIntent();
        mSelectModel = (SingleSelectionModel) intent.getSerializableExtra(SELECT_MODEL);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

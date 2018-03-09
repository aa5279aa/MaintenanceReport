package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.buildModel.type8.ReportRecord2Model;
import com.lxl.valvedemo.model.viewmodel.ReportInquireModel;
import com.lxl.valvedemo.sender.StockSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lxl.valvedemo.config.Definition.Serializable_Model.INQUIRE_MODEL;

/**
 * Created by yanglei on 2018/3/9.
 */

public class ReportInquireActivity extends Activity implements View.OnClickListener {
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    EditText mReportHeaderArea, mReportHeaderStation, mReportHeaderChecker, mReportHeaderDate;
    LinearLayout mReportRecordCcontainer;

    ReportInquireModel mInquireModel;
    Context mContext;
    Handler mHander = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        initView();
        initData();
        bindData();
    }

    private void initView() {
        mGoTop = (Button) findViewById(R.id.go_top_btn);
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setVisibility(View.GONE);

        scrollView = (ScrollView) findViewById(R.id.report_record2_fill);
        mReportHeaderArea = (EditText) findViewById(R.id.report_header_area);
        mReportHeaderStation = (EditText) findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) findViewById(R.id.report_header_checker);
        mReportHeaderDate = (EditText) findViewById(R.id.report_header_date);
        mReportRecordCcontainer = (LinearLayout) findViewById(R.id.report_record_container);

        mGoTop.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        mInquireModel = (ReportInquireModel) intent.getSerializableExtra(INQUIRE_MODEL);
    }

    private void bindData() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("type", mInquireModel.selectType);
        paramsMap.put("table", mInquireModel.tableName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = StockSender.requestGet(StockSender.SelectUrl, paramsMap, "utf-8");
                final List<ReportRecord2Model> reportRecord2Models = JSON.parseArray(s, ReportRecord2Model.class);
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshRecordModel(reportRecord2Models);
                    }
                });
            }

        }).start();

    }

    private void refreshRecordModel(List<ReportRecord2Model> reportRecord2Models) {
        mReportRecordCcontainer.removeAllViews();
        for (int i = 0; i < reportRecord2Models.size(); i++) {
            View inflate = View.inflate(this, R.layout.report_record2_show_item, null);
            TextView showTitle = (TextView) inflate.findViewById(R.id.report_record2_show_title);
            TextView showValue = (TextView) inflate.findViewById(R.id.report_record2_show_value);
            ReportRecord2Model reportRecord2Model = reportRecord2Models.get(i);
            showTitle.setText(reportRecord2Model.name);
            showValue.setText(reportRecord2Model.value);
            mReportRecordCcontainer.addView(inflate);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_top_btn) {
            gotoTop();
        }
    }

    public void gotoTop() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
//                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
//                        scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }


}

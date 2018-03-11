package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.Text;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.buildModel.type8.ReportRecord2Model;
import com.lxl.valvedemo.model.viewmodel.ReportInquireModel;
import com.lxl.valvedemo.sender.StockSender;
import com.lxl.valvedemo.util.NumberUtil;
import com.lxl.valvedemo.util.StockShowUtil;
import com.lxl.valvedemo.view.StockRankFilterBaseFragment;
import com.lxl.valvedemo.view.StockRankFilterGroupFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lxl.valvedemo.config.Definition.Serializable_Model.INQUIRE_MODEL;

/**
 * Created by yanglei on 2018/3/9.
 */

public class ReportInquireActivity extends FragmentActivity implements View.OnClickListener {
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    TextView mInquireType, mInquireArea, mInquireStation;
    EditText mChecker;
    FrameLayout fragmentContainer;
    LinearLayout dataContainer;


    ReportInquireModel mInquireModel = new ReportInquireModel();
    Context mContext;
    Handler mHander = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        initView();
    }

    private void initView() {
        mGoTop = (Button) findViewById(R.id.go_top_btn);
        mSubmit = (Button) findViewById(R.id.submit);

        scrollView = (ScrollView) findViewById(R.id.report_record2_fill);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        dataContainer = (LinearLayout) findViewById(R.id.data_container);

        mInquireType = (TextView) findViewById(R.id.inquire_type);
        mInquireArea = (TextView) findViewById(R.id.inquire_area);
        mInquireStation = (TextView) findViewById(R.id.inquire_station);
        mChecker = (EditText) findViewById(R.id.inquire_checker);


        mInquireType.setOnClickListener(this);
        mInquireArea.setOnClickListener(this);
        mInquireStation.setOnClickListener(this);
        mGoTop.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_top_btn) {
            gotoTop();
            return;
        }
        if (id == R.id.submit) {
            mInquireModel.type = NumberUtil.parseInteger(mInquireType.getText().toString());
            mInquireModel.area = mInquireArea.getText().toString();
            mInquireModel.station = mInquireStation.getText().toString();
            inquireValue();
            return;
        }
        StockRankFilterGroupFragment filterGroupFragmen = new StockRankFilterGroupFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragmentById instanceof StockRankFilterBaseFragment) {
            fragmentTransaction.remove(fragmentById);
            fragmentTransaction.commitAllowingStateLoss();
            return;
        }
        Bundle bundle = new Bundle();
        if (id == R.id.inquire_type) {
            bundle.putInt(StockRankFilterBaseFragment.SelectItemIndexTag, 1);
        } else if (id == R.id.inquire_area) {
            bundle.putInt(StockRankFilterBaseFragment.SelectItemIndexTag, 2);
        } else if (id == R.id.inquire_station) {
            bundle.putInt(StockRankFilterBaseFragment.SelectItemIndexTag, 3);
        }
//        bundle.putSerializable(StockRankFilterBaseFragment.StockRankFilterGroupModelTag, subGroupModel);
        filterGroupFragmen.setArguments(bundle);
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.fragment_container, filterGroupFragmen, "filter");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void inquireValue() {
        if (mInquireModel.type == 0) {
            StockShowUtil.showToastOnMainThread(this, "请先选择类型");
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("type", mInquireModel.type);
        paramsMap.put("station", mInquireModel.station);
        paramsMap.put("area", mInquireModel.area);
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
        dataContainer.removeAllViews();
        for (int i = 0; i < reportRecord2Models.size(); i++) {
            View inflate = View.inflate(this, R.layout.report_record2_show_item, null);
            TextView showTitle = (TextView) inflate.findViewById(R.id.report_record2_show_title);
            TextView showValue = (TextView) inflate.findViewById(R.id.report_record2_show_value);
            ReportRecord2Model reportRecord2Model = reportRecord2Models.get(i);
            showTitle.setText(reportRecord2Model.name);
            showValue.setText(reportRecord2Model.value);
            dataContainer.addView(inflate);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void onReceiveResult(int requestCode, int index, String str) {
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        if (StockRankFilterBaseFragment.FilterFragmentCode == requestCode) {
            if (index > 4) {
                return;
            }
            if (index == 1) {
                mInquireType.setText(str);
                return;
            }
            if (index == 2) {
                mInquireArea.setText(str);
                return;
            }
            if (index == 3) {
                mInquireStation.setText(str);
                return;
            }

        }
    }


}

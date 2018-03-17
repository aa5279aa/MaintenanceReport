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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.mapapi.map.Text;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.model.buildModel.type8.ReportRecord2Model;
import com.lxl.valvedemo.model.viewmodel.ReportInquireModel;
import com.lxl.valvedemo.sender.StockSender;
import com.lxl.valvedemo.util.NumberUtil;
import com.lxl.valvedemo.util.StockShowUtil;
import com.lxl.valvedemo.view.DropListDialog;
import com.lxl.valvedemo.view.StockRankFilterBaseFragment;
import com.lxl.valvedemo.view.StockRankFilterGroupFragment;
import com.lxl.valvedemo.view.StockTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.lxl.valvedemo.config.Definition.Serializable_Model.INQUIRE_MODEL;

/**
 * Created by yanglei on 2018/3/9.
 */

public class ReportInquireActivity extends FragmentActivity implements View.OnClickListener {
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    StockTextView mInquireType, mInquireArea, mInquireStation;
    EditText mChecker, mDateEdit;
    FrameLayout fragmentContainer;
    LinearLayout dataContainer;
    HashMap<String, List<String>> areaStationMap = DataConfig.getAreaStationMap();

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

        mInquireType = (StockTextView) findViewById(R.id.inquire_type);
        mInquireArea = (StockTextView) findViewById(R.id.inquire_area);
        mInquireStation = (StockTextView) findViewById(R.id.inquire_station);
        mChecker = (EditText) findViewById(R.id.inquire_checker);
        mDateEdit = (EditText) findViewById(R.id.inquire_date);

        mInquireType.setOnClickListener(this);
        mInquireArea.setOnClickListener(this);
        mInquireStation.setOnClickListener(this);
        mGoTop.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mDateEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
        mDateEdit.addTextChangedListener(new TextWatcher() {
            int after = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1) {
                    return;
                }
                // 输入后的监听
                if (s.length() == 4 && !s.toString().endsWith("_")) {
                    mDateEdit.setText(s + "-");
                    mDateEdit.setSelection(s.length() + 1);
                } else if (s.length() == 7 && !s.toString().endsWith("_")) {
                    mDateEdit.setText(s + "-");
                    mDateEdit.setSelection(s.length() + 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_top_btn) {
            gotoTop();
            return;
        }
        if (id == R.id.submit) {
            mInquireModel.type = DataConfig.getMapping().get(mInquireType.getText().toString());
            mInquireModel.area = mInquireArea.getText().toString();
            mInquireModel.station = mInquireStation.getText().toString();
            mInquireModel.date = mDateEdit.getText().toString();
            mInquireModel.checker = mChecker.getText().toString();
            inquireValue();
            return;
        }
        if (id == R.id.inquire_type) {
            DropListDialog dropListDialog = new DropListDialog(mContext);
            dropListDialog.isLarge = true;
            String[] strings = DataConfig.getMapping().keySet().toArray(new String[]{});
            final List<String> allType = Arrays.asList(strings);
            dropListDialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                @Override
                public void onItemSelected(View view1, int i, long l) {
                    mInquireType.setText(allType.get(i));
                }
            });
            dropListDialog.show(mInquireType, allType);
        } else if (id == R.id.inquire_area) {
            DropListDialog dropListDialog = new DropListDialog(mContext);
            String[] strings = areaStationMap.keySet().toArray(new String[]{});
            final List<String> list = Arrays.asList(strings);
            dropListDialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                @Override
                public void onItemSelected(View view1, int i, long l) {
                    mInquireArea.setText(list.get(i));
                }
            });
            dropListDialog.show(mInquireArea, list);
        } else if (id == R.id.inquire_station) {
            final List<String> strings = areaStationMap.get(mInquireArea.getText().toString());
            if (strings == null || strings.size() == 0) {
                StockShowUtil.showToastOnMainThread(mContext, "该节点下没有选项");
                return;
            }
            DropListDialog dropListDialog = new DropListDialog(mContext);
            dropListDialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                @Override
                public void onItemSelected(View view1, int i, long l) {
                    mInquireStation.setText(strings.get(i));
                }
            });
            dropListDialog.show(mInquireStation, strings);
        }
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
        paramsMap.put("date", mInquireModel.date);
        paramsMap.put("checker", mInquireModel.checker);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = StockSender.requestGet(StockSender.SelectUrl, paramsMap, "utf-8");
                JSONArray array = JSON.parseArray(s);
                if (array == null || array.size() == 0) {
                    return;
                }
                final List<ReportRecord2Model> reportRecord2Models = JSON.parseArray(array.getJSONArray(0).toString(), ReportRecord2Model.class);
                if (reportRecord2Models == null || reportRecord2Models.size() == 0) {
                    return;
                }
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
            showTitle.setText(reportRecord2Model.key);
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
}

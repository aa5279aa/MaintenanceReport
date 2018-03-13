package com.lxl.valvedemo.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.buildModel.type8.ReportRecord2Model;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.sender.StockSender;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.StockShowUtil;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.StockTitleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lxl.valvedemo.config.Definition.Serializable_Model.SELECT_MODEL;


/**
 * Created by xiangleiliu on 2018/3/8.
 */

public class ReportRecord2Activity extends FragmentActivity implements View.OnClickListener {
    StockTitleView titleView;
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    EditText mReportHeaderArea, mReportHeaderStation, mReportHeaderChecker, mReportHeaderDate;
    LinearLayout mReportRecordCcontainer;

    SingleSelectionModel mSelectModel;

    Context mContext;
    Handler mHander = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report2);
        initView();
        initData();
        bindData();
    }

    private void initView() {
        titleView = (StockTitleView) findViewById(R.id.stock_title_view);
        mGoTop = (Button) findViewById(R.id.go_top_btn);
        mSubmit = (Button) findViewById(R.id.submit);

        scrollView = (ScrollView) findViewById(R.id.report_record2_fill);
        mReportHeaderArea = (EditText) findViewById(R.id.report_header_area);
        mReportHeaderStation = (EditText) findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) findViewById(R.id.report_header_checker);
        mReportHeaderDate = (EditText) findViewById(R.id.report_header_date);
        mReportRecordCcontainer = (LinearLayout) findViewById(R.id.report_record_container);

        mGoTop.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        titleView.setActionBtnShow(View.VISIBLE);
    }


    private void bindData() {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        try {
            InputStream open = getAssets().open(mSelectModel.path + ReportBuildConfig.Location_Suffix);
            List<String> strings = IOHelper.readListStrByCode(open, "utf-8");
            for (int i = 0; i < strings.size(); i++) {
                View inflate = View.inflate(this, R.layout.report_record2_fill_item, null);
                TextView textView = (TextView) inflate.findViewById(R.id.report_record2_title);
                TextView editView = (TextView) inflate.findViewById(R.id.report_record2_edit);
                textView.setText(strings.get(i));
                mReportRecordCcontainer.addView(inflate);
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_top_btn) {
            gotoTop();
        } else if (id == R.id.submit) {
            submitReport();
        }
    }

    private void submitReport() {
        final LocationClient mClient = new LocationClient(getApplicationContext());
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.i("lxltest", "location,lat:" + bdLocation.getLatitude() + ",long:" + bdLocation.getLongitude() + ",address:" + bdLocation.getAddrStr());
                mClient.stop();

                List<ReportRecord2Model> list = new ArrayList<>();

                int childCount = mReportRecordCcontainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = mReportRecordCcontainer.getChildAt(i);
                    TextView titleText = (TextView) childAt.findViewById(R.id.report_record2_title);
                    EditText editText = (EditText) childAt.findViewById(R.id.report_record2_edit);
                    String titleStr = titleText.getText().toString();
                    String editStr = editText.getText().toString();
                    ReportRecord2Model model = new ReportRecord2Model();
                    model.name = titleStr;
                    model.value = editStr;
                    list.add(model);
                }
                String inputListJson = JSON.toJSONString(list);
                // EditText mReportHeaderArea, mReportHeaderStation, mReportHeaderChecker, mReportHeaderDate;
                String areaStr = mReportHeaderArea.getText().toString();
                String stationStr = mReportHeaderStation.getText().toString();
                String checkerStr = mReportHeaderChecker.getText().toString();
                String dateStr = mReportHeaderDate.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("data", inputListJson);
                map.put("area", areaStr);
                map.put("station", stationStr);
                map.put("checker", checkerStr);
                map.put("date", dateStr);
                map.put("location", bdLocation.getAddrStr());//定位地址
                map.put("lat_long", bdLocation.getLatitude() + "*" + bdLocation.getLongitude());//经纬度坐标
                if (StringUtil.emptyOrNull(areaStr)) {
                    StockShowUtil.showToastOnMainThread(mContext, "请输入区域");
                    return;
                }
                if (StringUtil.emptyOrNull(checkerStr)) {
                    StockShowUtil.showToastOnMainThread(mContext, "请输入检查人");
                    return;
                }
                if (StringUtil.emptyOrNull(stationStr)) {
                    StockShowUtil.showToastOnMainThread(mContext, "请输入场站");
                    return;
                }
                sendSubmitReportService(map);
            }
        };
        mClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(100 * 1000);
        option.setIsNeedAddress(true);
        mClient.setLocOption(option);
        mClient.start();
    }


    public void sendSubmitReportService(final Map<String, Object> map) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = StockSender.requestGet(StockSender.SubmitUrl, map, "utf-8");
                if (s.equals("success")) {
                    StockShowUtil.showToastOnMainThread(ReportRecord2Activity.this, "提交成功！");
                    //成功提交，离开界面
                    mHander.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                } else {
                    //提交失败，留在当前界面
                    StockShowUtil.showToastOnMainThread(ReportRecord2Activity.this, "提交失败！");
                }
            }
        }).start();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StockTitleView.TAKE_PICTURE) {
            Log.i("lxltest", "onActivityResult");
        }
    }
}

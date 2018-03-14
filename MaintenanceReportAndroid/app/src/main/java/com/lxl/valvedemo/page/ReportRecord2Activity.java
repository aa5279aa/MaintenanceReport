package com.lxl.valvedemo.page;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.alibaba.fastjson.JSONObject;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.buildModel.type8.ReportRecord2Model;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.model.viewmodel.UploadImageModel;
import com.lxl.valvedemo.sender.StockSender;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.JsonUtil;
import com.lxl.valvedemo.util.NumberUtil;
import com.lxl.valvedemo.util.StockShowUtil;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.DropListDialog;
import com.lxl.valvedemo.view.StockTextView;
import com.lxl.valvedemo.view.StockTitleView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lxl.valvedemo.config.Definition.Serializable_Model.SELECT_MODEL;
import static com.lxl.valvedemo.view.StockTitleView.TAKE_PICTURE;


/**
 * Created by xiangleiliu on 2018/3/8.
 */

public class ReportRecord2Activity extends FragmentActivity implements View.OnClickListener {
    StockTitleView titleView;
    Button mGoTop, mSubmit;
    ScrollView scrollView;
    StockTextView mReportHeaderArea, mReportHeaderStation;
    EditText mReportHeaderChecker, mReportHeaderDate;
    LinearLayout mReportRecordCcontainer;

    SingleSelectionModel mSelectModel;

    Context mContext;
    Handler mHander = new Handler();

    File file;
    UploadImageModel uploadImageModel = new UploadImageModel();

    HashMap<String, List<String>> areaStationMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report2);
        areaStationMap = DataConfig.getAreaStationMap();
        initView();
        initData();
        bindData();
    }

    private void initView() {
        titleView = (StockTitleView) findViewById(R.id.stock_title_view);
        mGoTop = (Button) findViewById(R.id.go_top_btn);
        mSubmit = (Button) findViewById(R.id.submit);

        scrollView = (ScrollView) findViewById(R.id.report_record2_fill);
        mReportHeaderArea = (StockTextView) findViewById(R.id.report_header_area);
        mReportHeaderStation = (StockTextView) findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) findViewById(R.id.report_header_checker);
        mReportHeaderDate = (EditText) findViewById(R.id.report_header_date);
        mReportRecordCcontainer = (LinearLayout) findViewById(R.id.report_record_container);

        mGoTop.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        titleView.setActionBtnShow(View.VISIBLE);
        titleView.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = titleView.getOutputMediaFile();
                Uri fileUri = titleView.getOutputMediaFileUri(file);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

        mReportHeaderArea.setOnClickListener(this);
        mReportHeaderStation.setOnClickListener(this);
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
        } else if (id == R.id.report_header_area) {
            final List<String> result = new ArrayList<>(areaStationMap.keySet());

            DropListDialog dropListDialog = new DropListDialog(mContext);
            dropListDialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                @Override
                public void onItemSelected(View view1, int i, long l) {
                    mReportHeaderArea.setText(result.get(i));
                    List<String> list = areaStationMap.get(result.get(i));
                    if (list.size() == 0) {
                        return;
                    }
                    mReportHeaderStation.setText(list.get(0));
                }
            });
            dropListDialog.show(mReportHeaderArea, result);
        } else if (id == R.id.report_header_station) {
            DropListDialog dropListDialog = new DropListDialog(mContext);
            final List<String> result = areaStationMap.get(mReportHeaderArea.getText().toString());
            if (result.size() == 0) {
                StockShowUtil.showToastOnMainThread(mContext, "该节点下无筛选项");
                return;
            }
            dropListDialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                @Override
                public void onItemSelected(View view1, int i, long l) {
                    mReportHeaderStation.setText(result.get(i));
                }
            });
            dropListDialog.show(mReportHeaderArea, result);

        }
    }

    private void submitReport() {
//        final LocationClient mClient = new LocationClient(getApplicationContext());
//        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.i("lxltest", "location,lat:" + bdLocation.getLatitude() + ",long:" + bdLocation.getLongitude() + ",address:" + bdLocation.getAddrStr());
//                mClient.stop();

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
        map.put("type", getReportType());
        map.put("data", inputListJson);
        map.put("area", areaStr);
        map.put("station", stationStr);
        map.put("checker", checkerStr);
        map.put("date", dateStr);
//                map.put("location", bdLocation.getAddrStr());//定位地址
//                map.put("lat_long", bdLocation.getLatitude() + "*" + bdLocation.getLongitude());//经纬度坐标
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
//            }
//        };
//        mClient.registerLocationListener(listener);
//        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5 * 1000);
//        option.setIsNeedAddress(true);
//        mClient.setLocOption(option);
//        mClient.start();
    }

    private int getReportType() {
        if (mSelectModel == null) {
            return 0;
        }
        if (!mSelectModel.itemStr.contains("_")) {
            return 0;
        }
        String[] split = mSelectModel.itemStr.split("_");
        if (split.length <= 1) {
            return 0;
        }
        String s = split[1];
        return NumberUtil.parseInteger(s);
    }


    public void sendSubmitReportService(final Map<String, Object> map) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //上传图片
                if (uploadImageModel.isUploadSuccess) {
                    String s = StockSender.uploadFile(uploadImageModel.imgFile, StockSender.UploadImgUrl);
                    //解析成功，则添加地址
//                    {"imgpath":"C://img\\Maintenance_IMG_20180313_181830.jpg","result":200,"resultMessage":"success"}
                    JSONObject imgResultJson = JsonUtil.string2JSON(s);
                    String webImgPath = imgResultJson.getString("imgpath");
                    map.put("imgPath", webImgPath);
                } else {
                    map.put("imgPath", "null");
                }
                //上传参数
                String s = StockSender.requestGet(StockSender.SubmitUrl, map, "utf-8");
                JSONObject resultJSON = JsonUtil.string2JSON(s);
                if (resultJSON.getInteger("result") == 200) {
                    StockShowUtil.showToastOnMainThread(ReportRecord2Activity.this, "提交成功！");
                    //成功提交，离开界面
                    finishCurrencyAcitivityForDelay(2000);
                } else {
                    String resultMessage = resultJSON.getString("resultMessage");
                    //提交失败，留在当前界面
                    StockShowUtil.showToastOnMainThread(ReportRecord2Activity.this, resultMessage);
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
        if (requestCode == TAKE_PICTURE) {
            if (file == null || StringUtil.emptyOrNull(file.getAbsolutePath())) {
                return;
            }
            uploadImageModel = new UploadImageModel();
            uploadImageModel.imgFile = file;
            uploadImageModel.isUploadSuccess = true;
            Log.i("lxltest", "onActivityResult");
        }
    }

    private void finishCurrencyAcitivityForDelay(int time) {
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, time);
    }


}

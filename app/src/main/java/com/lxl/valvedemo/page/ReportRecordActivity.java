package com.lxl.valvedemo.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.config.TableConfig;
import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.page.fragment.BaseBuildFragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType1Fragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType2Fragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType3Fragment;
import com.lxl.valvedemo.service.BuildTye1Service;
import com.lxl.valvedemo.service.BuildTye2Service;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.HotelCustomDialog;
import com.lxl.valvedemo.view.StockTitleView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportRecordActivity extends FragmentActivity {

    public static final String SelectModel = "SelectModel";

    private StockTitleView titleText;
    private Context mContext;
    private Button mSubmit;
    SingleSelectionModel mSelectModel;

    BaseBuildFragment buildFragment;
    Handler mHander = new Handler();

    LocationClient mClient;
    BDAbstractLocationListener listener;
    List<LocationRecordModel> recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        initView();
        initData();
        bindData();
        startLocationService();
    }

    private void startLocationService() {
        mClient = new LocationClient(getApplicationContext());
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.i("lxltest", "location,lat:" + bdLocation.getLatitude() + ",long:" + bdLocation.getLongitude() + ",address:" + bdLocation.getAddrStr());
                LocationRecordModel recordModel = new LocationRecordModel();
                recordModel.latitude = bdLocation.getLatitude();
                recordModel.longitude = bdLocation.getLongitude();
                recordModel.addressText = bdLocation.getAddrStr();
                recordModel.cityText = bdLocation.getCity();
                recordList.add(recordModel);
            }
        };
        mClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
//        option.setOpenAutoNotifyMode(10, 0, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        option.setScanSpan(10 * 1000);
        option.setIsNeedAddress(true);
        mClient.setLocOption(option);
        mClient.start();
    }

    private void initView() {
        titleText = (StockTitleView) findViewById(R.id.text_title);
        mSubmit = (Button) findViewById(R.id.submit);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        mSelectModel = (SingleSelectionModel) extras.getSerializable(SelectModel);
        //获取到对应的execl表
        String path = "";
        try {
            path = TableConfig.getInstance(this).findExeclByModel(this, mSelectModel);
            Log.i("lxltest", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtil.emptyOrNull(path)) {
            return;
        }
    }

    private void bindData() {
        if ("1".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType1Fragment();
        } else if ("2".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType2Fragment();
        } else if ("3".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType3Fragment();
        } else {
            HotelCustomDialog dialog = new HotelCustomDialog();
            dialog.setContent("该类型不支持，点击关闭当前界面。", "确定", null);
            dialog.setDialogBtnClick(new HotelCustomDialog.HotelDialogBtnClickListener() {
                @Override
                public void leftBtnClick(HotelCustomDialog dialog) {
                    dialog.dismiss();
                    finish();
                }

                @Override
                public void rightBtnClick(HotelCustomDialog dialog) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show(getSupportFragmentManager(), "close");
            return;
        }
        buildFragment.setExecl(mSelectModel.path);
        if (buildFragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, buildFragment);
        fragmentTransaction.commit();
        titleText.setTitle(mSelectModel.itemStr);
        mSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "结果已提交，正在生成excel表", Toast.LENGTH_SHORT).show();
                final ReportBuildModel buildModel = buildFragment.buildBuildModel();
                buildModel.tableName = mSelectModel.itemStr;
                buildModel.dateStr = DateUtil.getCurrentTime();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            List<String> strList = locationRcord2List(recordList);
                            String s = IOHelper.listToStr(strList);
                            String path = ReportBuildConfig.reportBuildPath + File.separator + buildModel.tableName + "_" + buildModel.dateStr;
                            File outFile = new File(path + ReportBuildConfig.Location_Suffix);
                            IOHelper.writerStrByCodeToFile(outFile, s);
                            File file = new File(path + ReportBuildConfig.Execl_Suffix);
                            if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_ONE) {
                                IOHelper.checkParent(file);
                                new BuildTye1Service().buildReportTypeOne(file, buildModel.maintainReportModel, new BuildResultInter() {
                                    @Override
                                    public void buildSucess(String pathStr) {
                                        showResult("execl生成成功，位置：" + pathStr);
                                        //退回到大首页
                                        back2Home();
                                    }

                                    @Override
                                    public void buildFail(String caseStr) {
                                        showResult(caseStr);
                                    }
                                });
                            } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_TWO) {
                                IOHelper.checkParent(file);
                                new BuildTye2Service().writeReportTypeTwo(file, buildModel.inspectionReportModel, new BuildResultInter() {
                                    @Override
                                    public void buildSucess(String pathStr) {
                                        showResult("execl生成成功，位置：" + pathStr);
                                        //退回到大首页
                                        back2Home();
                                    }

                                    @Override
                                    public void buildFail(String caseStr) {
                                        showResult(caseStr);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void showResult(final String str) {
        mHander.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, str, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public List<String> locationRcord2List(List<LocationRecordModel> recordList) {
        List<String> strList = new ArrayList<>();
        for (LocationRecordModel locationRecordModel : recordList) {
            strList.add("时间：" + locationRecordModel.dataText + " 地点： " + locationRecordModel.addressText + " 经度：" + locationRecordModel.latitude + " 纬度：" + locationRecordModel.longitude);
        }
        return strList;
    }


    public void back2Home() {
        Intent intent = new Intent();
        intent.setClass(this, OperationActivity.class);
        startActivity(intent);
    }

}

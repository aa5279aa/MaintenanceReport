package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.lxl.valvedemo.page.fragment.ReportRecordType4Fragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType5Fragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType6Fragment;
import com.lxl.valvedemo.page.fragment.ReportRecordType7Fragment;
import com.lxl.valvedemo.service.BuildType1Service;
import com.lxl.valvedemo.service.BuildType2Service;
import com.lxl.valvedemo.service.BuildType3Service;
import com.lxl.valvedemo.service.BuildType4Service;
import com.lxl.valvedemo.service.BuildType5Service;
import com.lxl.valvedemo.service.BuildType6Service;
import com.lxl.valvedemo.service.BuildType7Service;
import com.lxl.valvedemo.service.BuildTypeBaseService;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.HotelCustomDialog;
import com.lxl.valvedemo.view.HotelCustomEditDialog;
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
    private Button mGoTOP;

    SingleSelectionModel mSelectModel;

    BaseBuildFragment buildFragment;
    Handler mHander = new Handler();

    LocationClient mClient;
    BDAbstractLocationListener listener;
    ArrayList<LocationRecordModel> recordList = new ArrayList<>();

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
                recordModel.dataText = DateUtil.getCurrentTime();
                recordList.add(recordModel);
            }
        };
        mClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(10 * 1000);
        option.setIsNeedAddress(true);
        mClient.setLocOption(option);
        mClient.start();
    }

    private void initView() {
        titleText = (StockTitleView) findViewById(R.id.stock_title_view);
        mSubmit = (Button) findViewById(R.id.submit);
        mGoTOP = (Button) findViewById(R.id.go_top_btn);
        titleText.setActionBtnShow(View.VISIBLE);
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
        } else if ("4".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType4Fragment();
        } else if ("5".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType5Fragment();
        } else if ("6".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType6Fragment();
        } else if ("7".equals(mSelectModel.parseType)) {
            buildFragment = new ReportRecordType7Fragment();
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
        mGoTOP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //回顶部
                buildFragment.gotoTop();
            }
        });

        mSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final ReportBuildModel buildModel = buildFragment.buildBuildModel();
                //检查必填内容
                BuildTypeBaseService serviceInter = null;
                if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_ONE) {
                    serviceInter = new BuildType1Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_TWO) {
                    serviceInter = new BuildType2Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_THREE) {
                    serviceInter = new BuildType3Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_FOUR) {
                    serviceInter = new BuildType4Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_FIVE) {
                    serviceInter = new BuildType5Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_SIX) {
                    serviceInter = new BuildType6Service();
                } else if (buildModel.buildType == ReportBuildModel.BUILD_TYPE_SEVEN) {
                    serviceInter = new BuildType7Service();
                }
                if (serviceInter == null) {
                    return;
                }
                String checkResult = serviceInter.checkInfo(buildModel);
                if (!StringUtil.emptyOrNull(checkResult)) {
                    showResult("请补全必填内容：" + checkResult);
                    return;
                }
                final BuildTypeBaseService service = serviceInter;
                HotelCustomEditDialog dialog = new HotelCustomEditDialog();
                dialog.setTitle("请输入表格名称");
                dialog.setContent("", "确定", "取消");
                dialog.setDialogBtnEditClick(new HotelCustomEditDialog.HotelDialogBtnEditClickListener() {
                    @Override
                    public void leftBtnClick(HotelCustomEditDialog dialog, String input) {
                        createTable(buildModel, input, service);
                    }

                    @Override
                    public void rightBtnClick(HotelCustomEditDialog dialog, String input) {
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void createTable(final ReportBuildModel buildModel, String input, final BuildTypeBaseService service) {
        Toast.makeText(mContext, "结果已提交，正在生成excel表", Toast.LENGTH_SHORT).show();
        buildModel.tableName = input + "_" + mSelectModel.itemStr;
        buildModel.dateStr = DateUtil.getCurrentDate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> strList = locationRcord2List(recordList);
                    String s = IOHelper.listToStr(strList);
                    final String path = ReportBuildConfig.getBaseBuildPath() + File.separator + buildModel.tableName + "_" + buildModel.dateStr;
                    File outFile = new File(path + ReportBuildConfig.Location_Suffix);
                    IOHelper.checkParent(outFile);
                    IOHelper.writerStrByCodeToFile(outFile, s);
                    outFile = new File(path + ReportBuildConfig.Execl_Suffix);
                    service.writeReport(outFile, buildModel, new BuildResultInter() {
                        @Override
                        public void buildSucess(String pathStr) {
                            showResult("execl生成成功，位置：" + pathStr + "\n即将跳转轨迹路径界面");
//                                    //退回到大首页
                            new Handler(getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    gotoTrajectoryPage(path, buildModel.tableName);

                                }
                            }, 2000);
                        }

                        @Override
                        public void buildFail(String caseStr) {
                            showResult(caseStr);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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


    public void gotoTrajectoryPage(String path, String tableName) {
        Intent intent = new Intent();
        intent.setClass(this, TrajectoryShowActivity.class);
        intent.putExtra(TrajectoryShowActivity.RECORD, recordList);
        intent.putExtra(TrajectoryShowActivity.PATH, path);
        intent.putExtra(TrajectoryShowActivity.NAME, tableName);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (titleText.mediaFile == null)
                return;
            final ContentValues values = new ContentValues(2);
            values.put(MediaStore.Video.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Video.Media.DATA, titleText.mediaFile.getAbsolutePath());
            // Add a new record (identified by uri)
            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }
}

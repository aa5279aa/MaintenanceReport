package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.valvedemo.MainActivity;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.TableConfig;
import com.lxl.valvedemo.model.ReportSelectionItemEntity;
import com.lxl.valvedemo.model.ReportSelectionSubItemEntity;
import com.lxl.valvedemo.model.SingleSelectionModel;
import com.lxl.valvedemo.page.fragment.ReportRecordType1Fragment;
import com.lxl.valvedemo.util.PoiHelper;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.view.ListLinearLayout;
import com.lxl.valvedemo.view.SelectionAdapter;
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
    private FrameLayout mFragmentContainer;
    SelectionAdapter adapter;
    SingleSelectionModel mSelectModel;

    Fragment type1;
    Fragment type2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        initView();
        initData();
        bindData();
    }

    private void initView() {
        titleText = (StockTitleView) findViewById(R.id.text_title);
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
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
        //使用对应的规则去解析execl
        readExecl(path, mSelectModel.parseType);
    }


    private void bindData() {
        if ("1".equals(mSelectModel.parseType)) {
            ReportRecordType1Fragment recordType1Fragment = new ReportRecordType1Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, recordType1Fragment);
            fragmentTransaction.commit();
        }
        titleText.setTitle(mSelectModel.itemStr);
        mSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 提交并且生成最后的结果
                Toast.makeText(mContext, "结果已提交，正在生成excel表", Toast.LENGTH_SHORT)
                        .show();
////                final String station, final String owner, final String checker, final String data
//                String station = ((TextView) findViewById(R.id.station)).getText().toString();
//                String owner = ((TextView) findViewById(R.id.owner)).getText().toString();
//                String checker = ((TextView) findViewById(R.id.checker)).getText().toString();
//                String date = ((TextView) findViewById(R.id.date)).getText().toString();
//                // 开启一个线程
//                createReport(adapter, station, owner, checker, date);
//                Intent intent = new Intent();
//                intent.setClass(mContext, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });
    }

    private void showFillTable(String parseType) {

    }

    private void readExecl(String path, String parseType) {

    }


    private void createReport(final SelectionAdapter adapter, final String station, final String owner, final String checker, final String data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<ReportSelectionItemEntity> list = new ArrayList<ReportSelectionItemEntity>();
                for (int i = 0; i < adapter.getCount(); i++) {
                    list.add(adapter.getItem(i));
                }
                List<String[]> all = new ArrayList<String[]>();
                all.add(new String[]{"维修队计量专业季度维护保养检查表"});
                all.add(new String[]{"场站：" + station});
                all.add(new String[]{"序号", "项目", "检查内容", "检查结果", "备注"});
                int index = 0;
                for (ReportSelectionItemEntity item : list) {
                    for (ReportSelectionSubItemEntity subItem : item.reportSelectionList) {
                        String[] line = new String[5];
                        line[0] = String.valueOf(index++);
                        line[1] = item.itemTitle;
                        line[2] = subItem.mCheckContent;
                        line[3] = subItem.mCheckResult;
                        line[4] = subItem.mDesc;
                        all.add(line);
                    }
                }
                all.add(new String[]{" "});
                all.add(new String[]{" "});
                all.add(new String[]{"场站负责人" + owner, "检查人" + checker, "日期" + data});

                String[][] strs = new String[all.size()][5];
                for (int i = 0; i < all.size(); i++) {
                    String[] line = all.get(i);
                    if (line.length == 1) {
                        strs[i][3] = line[0];
                    } else {
                        for (int k = 0; k < line.length; k++) {
                            strs[i][k] = line[k];
                        }
                    }
                }

                File externalStorageDirectory = Environment
                        .getExternalStorageDirectory();
                final String path = externalStorageDirectory.getPath()
                        + File.separator + station + "_" + data + ".xls";
//				String paths = Environment.getExternalStorageDirectory()
//						.getPath() + "//ctrip_crash_test//crash_log//xx.xls";
                final String result = PoiHelper.poiWrite(strs, path);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if ("success".equals(result)) {
                            Toast.makeText(mContext, "execl生成成功，位置：" + path, Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(mContext, result, Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });
            }
        }).start();
    }

}

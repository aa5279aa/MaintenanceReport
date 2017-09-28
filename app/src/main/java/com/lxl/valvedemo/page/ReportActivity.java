package com.lxl.valvedemo.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.valvedemo.BaseActivity;
import com.lxl.valvedemo.MainActivity;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.viewmodel.ReportSelectionItemEntity;
import com.lxl.valvedemo.model.buildModel.ReportSelectionSubItemEntity;
import com.lxl.valvedemo.util.PoiHelper;
import com.lxl.valvedemo.view.ListLinearLayout;
import com.lxl.valvedemo.view.SelectionAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends BaseActivity {

    protected TextView titleText;
    public Context mContext;
    public ListLinearLayout mList;
    public Button mSubmit;
    SelectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_report);
        titleText = (TextView) findViewById(R.id.text_title);
        mList = (ListLinearLayout) findViewById(R.id.listview);
        mSubmit = (Button) findViewById(R.id.submit);
        initView();
        initData();
        setParentContentView();
    }

    private void initView() {
        titleText.setText("计量专业季度保养检查表");
        adapter = new SelectionAdapter(mContext, -1);
        mList.setAdapter(adapter);
        mSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 提交并且生成最后的结果
                Toast.makeText(mContext, "结果已提交，正在生成excel表", Toast.LENGTH_SHORT)
                        .show();
//                final String station, final String owner, final String checker, final String data
                String station = ((TextView) findViewById(R.id.station)).getText().toString();
                String owner = ((TextView) findViewById(R.id.owner)).getText().toString();
                String checker = ((TextView) findViewById(R.id.checker)).getText().toString();
                String date = ((TextView) findViewById(R.id.date)).getText().toString();
                // 开启一个线程
                createReport(adapter, station, owner, checker, date);
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        String key = extras.getString("key");
        //添加头部的场信息
        addBottom(mList);
//        if (key != null) {
//            List<ReportSelectionItemEntity> list = DataConfig
//                    .getReportSelectionEntity(key);
//            adapter.addAll(list);
//        }
        mList.notifyDataSetChanged();
        //添加尾部的场站负责人等信息
    }


    private void addBottom(ListLinearLayout mList) {
        View inflate = View.inflate(this, R.layout.report_header_type2, null);
        mList.addChildView(inflate);
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

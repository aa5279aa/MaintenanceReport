package com.lxl.valvedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.entity.ReportSelectionItemEntity;
import com.lxl.valvedemo.entity.ReportSelectionSubItemEntity;
import com.lxl.valvedemo.util.PoiHelper;
import com.lxl.valvedemo.view.ListLinearLayout;

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
        addHeader(mList);
        if (key != null) {
            List<ReportSelectionItemEntity> list = DataConfig
                    .getReportSelectionEntity(key);
            adapter.addAll(list);
        }
        mList.notifyDataSetChanged();
        //添加尾部的场站负责人等信息
        addBottom(mList);
    }

    private void addHeader(ListLinearLayout mList) {
        View inflate = View.inflate(this, R.layout.report_header, null);
        mList.addChildView(inflate);
    }

    private void addBottom(ListLinearLayout mList) {
        View inflate = View.inflate(this, R.layout.report_bottom, null);
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

    public class SelectionAdapter extends ArrayAdapter<ReportSelectionItemEntity> {

        public SelectionAdapter(Context context, int resource) {
            super(context, resource);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.report_item, null);
                LinearLayout contanier = (LinearLayout) convertView
                        .findViewById(R.id.contanier);
                TextView choiceTetxt = (TextView) convertView
                        .findViewById(R.id.choice_text);
                LinearLayout choiceList = (LinearLayout) convertView
                        .findViewById(R.id.choice_list);
                holder.contanier = contanier;
                holder.choiceText = choiceTetxt;
                holder.choiceList = choiceList;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ReportSelectionItemEntity item = getItem(position);
            holder.choiceText.setText(item.itemTitle);
            List<ReportSelectionSubItemEntity> itemList = item.reportSelectionList;
            if (holder.choiceList.getChildCount() > 0) {
                return convertView;
            }
            for (ReportSelectionSubItemEntity subItemEntity : itemList) {
                ReportItemView reportItemView = new ReportItemView(mContext,
                        subItemEntity, position);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        -1, -2);
                holder.choiceList.addView(reportItemView.getView(), lp);
            }
            return convertView;
        }

        class ViewHolder {

            public LinearLayout contanier;
            public TextView choiceText;
            public LinearLayout choiceList;
        }

    }

    class ReportItemView {
        View view;
        CheckBox checkBox;
        TextView msg;
        TextView checkResult;
        TextView checkResultBtn;
        TextView checkDesc;
        TextView checkDescBtn;
        int mPosiion;
        ReportSelectionSubItemEntity mSubItemEntity;

        public ReportItemView(Context context, ReportSelectionSubItemEntity subItemEntity, int position) {
            mPosiion = position;
            this.mSubItemEntity = subItemEntity;
            view = getLayoutInflater().inflate(R.layout.report_sub_item, null);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            msg = (TextView) view.findViewById(R.id.msg);
            checkResult = (TextView) view.findViewById(R.id.check_result);
            checkResultBtn = (TextView) view.findViewById(R.id.check_result_btn);
            checkDesc = (TextView) view.findViewById(R.id.check_desc);
            checkDescBtn = (TextView) view.findViewById(R.id.check_desc_btn);
            msg.setText(subItemEntity.mCheckContent);
            init();
        }

        public void init() {
            checkResultBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean enabled = checkResult.isEnabled();
                    if (!enabled) {
                        checkResult.setEnabled(true);
                        checkResultBtn.setText("保存");
                    } else {
                        mSubItemEntity.mCheckResult = checkResult.getText().toString();
                        checkResult.setEnabled(false);
                        checkResultBtn.setText("编辑");
                    }
                }
            });
            checkDescBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean enabled = checkDesc.isEnabled();
                    if (!enabled) {
                        checkDesc.setEnabled(true);
                        checkDescBtn.setText("保存");
                    } else {
                        mSubItemEntity.mDesc = checkDesc.getText().toString();
                        checkDesc.setEnabled(false);
                        checkDescBtn.setText("编辑");
                    }
                }
            });
        }

        public View getView() {
            return view;
        }

    }

}

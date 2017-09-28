package com.lxl.valvedemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.viewmodel.ReportSelectionItemEntity;
import com.lxl.valvedemo.model.buildModel.ReportSelectionSubItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class SelectionAdapter extends ArrayAdapter<ReportSelectionItemEntity> {
    Context mContext;

    public SelectionAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
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
        view = View.inflate(context, R.layout.report_sub_item, null);
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
        checkResultBtn.setOnClickListener(new View.OnClickListener() {
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
        checkDescBtn.setOnClickListener(new View.OnClickListener() {
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
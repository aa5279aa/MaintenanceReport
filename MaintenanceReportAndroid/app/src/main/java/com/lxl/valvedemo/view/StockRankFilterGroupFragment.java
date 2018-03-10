package com.lxl.valvedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.page.ReportInquireActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiangleiliu on 2017/10/23.
 * 选股范围
 */
public class StockRankFilterGroupFragment extends StockRankFilterBaseFragment {

    LinearLayout mSubGroupView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stock_rank_filter_group_layout, null);
    }

    @Override
    protected void bindData() {
        List<String> list = new ArrayList<>();
        if (index == 1) {
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
            list.add("7");
        } else if (index == 2) {
            list.add("日照");
            list.add("枣庄");
            list.add("烟台");
        } else if (index == 3) {
            list.add("河北站");
            list.add("山东站");
        }
        for (int i = 0; i < list.size(); i++) {
            TextView text = new TextView(getContext());
            text.setText(list.get(i));
            text.setPadding(10, 10, 10, 10);
            text.setOnClickListener(this);
            mSubGroupView.addView(text);
        }
    }


    @Override
    protected void initView(View view) {
        mSubGroupView = (LinearLayout) view.findViewById(R.id.filter_sub_group_layout);
//        mItemView = (LinearLayout) view.findViewById(R.id.filter_item_layout);
    }

    @Override
    protected void clearSelect() {

    }


    protected void submitFilter(String str) {
        ((ReportInquireActivity) getActivity()).onReceiveResult(FilterFragmentCode, index, str);
    }

}

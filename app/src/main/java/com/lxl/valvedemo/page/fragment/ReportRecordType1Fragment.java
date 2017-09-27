package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.view.ListLinearLayout;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType1Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindDataType1(view);
    }


    private void bindDataType1(View view) {
        TextView fillAdd = (TextView) view.findViewById(R.id.report_fill_add);
        final LinearLayout fillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);

        fillAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNode2Container(fillContainer);
            }
        });
        addNode2Container(fillContainer);
    }

    private void addHeader(ListLinearLayout mList, String parseType) {
        View inflate = View.inflate(getContext(), R.layout.report_header_type2, null);
        mList.addChildView(inflate);
    }


    private void addNode2Container(LinearLayout fillContainer) {
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_one_additem, null);
        fillContainer.addView(inflate);
    }


}

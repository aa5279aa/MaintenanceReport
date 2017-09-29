package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.model.buildModel.maintain.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType1Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportHeaderDate;

    LinearLayout mReportFillContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType1(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mReportHeaderWorkArea = (EditText) view.findViewById(R.id.report_header_type1_workarea);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.report_header_type1_station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.report_header_type1_checker);
        mReportHeaderDate = (EditText) view.findViewById(R.id.report_header_type1_date);

        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
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

    private void addNode2Container(LinearLayout fillContainer) {
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_one_additem, null);
        fillContainer.addView(inflate);
    }


    @Override
    public ReportBuildModel getBuildModel() {
        ReportBuildModel model = new ReportBuildModel();
        //获取值
        String workArea = mReportHeaderWorkArea.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        model.buildType = ReportBuildModel.BUILD_TYPE_ONE;

        model.maintainReportModel.workAreaText = workArea;
        model.maintainReportModel.stationText = station;
        model.maintainReportModel.checkerText = checker;
        model.maintainReportModel.dateText = data;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            EditText equipmentEdit = (EditText) childAt.findViewById(R.id.report_fill_equipment_edit);
            EditText specificationsEdit = (EditText) childAt.findViewById(R.id.report_fill_specifications_edit);
            EditText numberEdit = (EditText) childAt.findViewById(R.id.report_fill_number_edit);
            EditText situationEdit = (EditText) childAt.findViewById(R.id.report_fill_situation_edit);
            EditText descEdit = (EditText) childAt.findViewById(R.id.report_fill_desc_edit);

            String equipmentStr = equipmentEdit.getText().toString();
            String specificationsStr = specificationsEdit.getText().toString();
            String numberStr = numberEdit.getText().toString();
            String situationStr = situationEdit.getText().toString();
            String descStr = descEdit.getText().toString();

            MaintainReportItemModel itemModel = new MaintainReportItemModel();
            itemModel.position = i;
            itemModel.equipmentName = equipmentStr;
            itemModel.specificationsName = specificationsStr;
            itemModel.equipmentId = numberStr;
            itemModel.maintainInfo = situationStr;
            itemModel.maintainDesc = descStr;
            model.maintainReportModel.maintainList.add(itemModel);
        }
        return model;
    }
}

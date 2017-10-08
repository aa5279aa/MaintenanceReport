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
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.StringUtil;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType1Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    TextView mReportHeaderDate;
    TextView mFillAdd;
    LinearLayout mReportFillContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType1(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mReportHeaderWorkArea = (EditText) view.findViewById(R.id.report_header_workarea);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.report_header_checker);
        mReportHeaderDate = (TextView) view.findViewById(R.id.report_header_date);
        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
        mFillAdd = (TextView) view.findViewById(R.id.report_fill_add);
    }


    private void bindDataType1(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        mFillAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNode2Container(mReportFillContainer);
            }
        });
        addNode2Container(mReportFillContainer);
    }

    private void addNode2Container(final LinearLayout fillContainer) {
        final View inflate = View.inflate(getContext(), R.layout.report_fill_type_1_additem, null);
        final TextView equipmentEdit = (TextView) inflate.findViewById(R.id.report_fill_equipment_edit);
        View.OnClickListener equipmentNamelistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(v instanceof TextView)) {
                    return;
                }
                String s = ((TextView) v).getText().toString();
                equipmentEdit.setText(s);
            }
        };
        inflate.findViewById(R.id.equipment_name_tag1).setOnClickListener(equipmentNamelistener);
        inflate.findViewById(R.id.equipment_name_tag2).setOnClickListener(equipmentNamelistener);
        inflate.findViewById(R.id.equipment_name_tag3).setOnClickListener(equipmentNamelistener);
        inflate.findViewById(R.id.equipment_name_tag4).setOnClickListener(equipmentNamelistener);

        final TextView fill_specificationEdit = (TextView) inflate.findViewById(R.id.report_fill_situation_edit);
        View.OnClickListener fillSituationlistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(v instanceof TextView)) {
                    return;
                }
                String s = ((TextView) v).getText().toString();
                String oldStr = fill_specificationEdit.getText().toString();
                if (oldStr.contains(s)) {
                    return;
                }
                if (StringUtil.emptyOrNull(oldStr)) {
                    fill_specificationEdit.setText(s);
                } else {
                    fill_specificationEdit.setText(oldStr + "," + s);
                }
            }
        };
        inflate.findViewById(R.id.fill_situation_tag1).setOnClickListener(fillSituationlistener);
        inflate.findViewById(R.id.fill_situation_tag2).setOnClickListener(fillSituationlistener);
        inflate.findViewById(R.id.fill_situation_tag3).setOnClickListener(fillSituationlistener);
        inflate.findViewById(R.id.fill_situation_tag4).setOnClickListener(fillSituationlistener);
        fillContainer.addView(inflate);

        inflate.findViewById(R.id.report_fill_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillContainer.removeView(inflate);
            }
        });
    }

    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_ONE;
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        //获取值
        String workArea = mReportHeaderWorkArea.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();


        model.maintainReportModel.workAreaText = workArea;
        model.maintainReportModel.stationText = station;
        model.maintainReportModel.checkerText = checker;
        model.maintainReportModel.dateText = data;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            TextView equipmentEdit = (TextView) childAt.findViewById(R.id.report_fill_equipment_edit);
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

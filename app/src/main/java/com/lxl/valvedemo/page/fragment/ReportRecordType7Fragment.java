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
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.service.BuildTye3Service;
import com.lxl.valvedemo.util.DateUtil;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType7Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportHeaderDate;
    TextView mFillAdd;

    LinearLayout mReportFillContainer;

    BuildTye3Service tyeThreeService = new BuildTye3Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_3, container, false);
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

    private void addNode2Container(LinearLayout fillContainer) {
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_1_additem, null);
        fillContainer.addView(inflate);
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_THREE;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            MaintainReportByAreaModel maintainReportByAreaModel = tyeThreeService.readReportTypeThree(open);
            mReportBuildModel.maintainReportByArea = maintainReportByAreaModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
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

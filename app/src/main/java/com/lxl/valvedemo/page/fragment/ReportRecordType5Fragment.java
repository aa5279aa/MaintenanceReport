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
import com.lxl.valvedemo.model.buildModel.type5.ReportModelType5;
import com.lxl.valvedemo.service.BuildType5Service;
import com.lxl.valvedemo.util.DateUtil;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType5Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderOwer;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportHeaderDate;
    TextView mFillAdd;

    LinearLayout mReportFillContainer;

    BuildType5Service typeService = new BuildType5Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_5, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType(view);
    }

    private void initView(View view) {
        mReportHeaderOwer = (EditText) view.findViewById(R.id.ower);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.checker);
        mReportHeaderDate = (EditText) view.findViewById(R.id.check_data);
        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
    }


    private void bindDataType(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        List<ReportModelType5.ReportModelType5SubModel> reportItemModelList = mReportBuildModel.reportModelType5.reportItemModelList;

        mReportFillContainer.removeAllViews();
        for (ReportModelType5.ReportModelType5SubModel subModel : reportItemModelList) {
            View inflate = View.inflate(getContext(), R.layout.report_fill_type_5_sub, null);
            bindSubDataType(inflate, subModel);
            mReportFillContainer.addView(inflate);
        }
    }

    private void bindSubDataType(View view, ReportModelType5.ReportModelType5SubModel subModel) {
        TextView projectTv = (TextView) view.findViewById(R.id.project_name);
        LinearLayout checkInfoView = (LinearLayout) view.findViewById(R.id.check_info_view);
        projectTv.setText(subModel.projectText);
        for (ReportModelType5.ReportModelType5CheckItem checkItem : subModel.checkInfoList) {
            View inflate = View.inflate(getContext(), R.layout.report_fill_type_5_item, null);
            TextView checkInfo = (TextView) inflate.findViewById(R.id.check_info);
//            TextView checkResult = (TextView) inflate.findViewById(R.id.check_result);
//            TextView checkDesc = (TextView) inflate.findViewById(R.id.check_desc);
            checkInfo.setText(checkItem.checkInfo);
            checkInfoView.addView(inflate);
        }
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_THREE;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            ReportModelType5 reportModelType5 = typeService.readReportType(open);
            mReportBuildModel.reportModelType5 = reportModelType5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        //获取值
        String ower = mReportHeaderOwer.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        model.buildType = ReportBuildModel.BUILD_TYPE_FIVE;
        ReportModelType5 reportModelType5 = model.reportModelType5;
        reportModelType5.checkerText = checker;
        reportModelType5.owerText = ower;
        reportModelType5.dateText = data;
        reportModelType5.stationText = station;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            TextView projectName = (TextView) childAt.findViewById(R.id.project_name);
            LinearLayout checkInfoView = (LinearLayout) childAt.findViewById(R.id.check_info_view);
            ReportModelType5.ReportModelType5SubModel subModel = reportModelType5.reportItemModelList.get(i);
            subModel.projectText = projectName.getText().toString();
            for (int j = 0; j < checkInfoView.getChildCount(); j++) {
                View childAt1 = checkInfoView.getChildAt(j);
                TextView checkInfo = (TextView) childAt1.findViewById(R.id.check_info);
                TextView checkResult = (TextView) childAt1.findViewById(R.id.check_result);
                TextView checkDesc = (TextView) childAt1.findViewById(R.id.check_desc);
                ReportModelType5.ReportModelType5CheckItem checkItem = subModel.checkInfoList.get(j);
                checkItem.checkInfo = checkInfo.getText().toString();
                checkItem.checkResult = checkResult.getText().toString();
                checkItem.checkDesc = checkDesc.getText().toString();
            }
        }
        return model;
    }

}

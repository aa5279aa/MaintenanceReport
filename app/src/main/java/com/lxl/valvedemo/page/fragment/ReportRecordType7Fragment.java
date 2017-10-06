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
import com.lxl.valvedemo.model.buildModel.type7.ReportModelType7;
import com.lxl.valvedemo.service.BuildType7Service;
import com.lxl.valvedemo.util.DateUtil;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType7Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    TextView mReportHeaderDate;

    EditText mDescEdit;

    LinearLayout mReportFillContainer;

    BuildType7Service typeService = new BuildType7Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_7, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mReportHeaderWorkArea = (EditText) view.findViewById(R.id.report_header_workarea);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.report_header_checker);
        mReportHeaderDate = (TextView) view.findViewById(R.id.report_header_date);
        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
        mDescEdit = (EditText) view.findViewById(R.id.desc_edit);
    }


    private void bindDataType(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        ReportModelType7 reportModelType7 = mReportBuildModel.reportModelType7;
        for (ReportModelType7.ReportModelType7SubModel subModel : reportModelType7.reportItemModelList) {
            View inflate = View.inflate(getContext(), R.layout.report_fill_type_7_sub, null);
            mReportFillContainer.addView(inflate);
            bindDataSubType(inflate, subModel);
        }

    }

    private void bindDataSubType(View fillContainer, ReportModelType7.ReportModelType7SubModel subModel) {
        TextView title = (TextView) fillContainer.findViewById(R.id.title);
        LinearLayout subContainer = (LinearLayout) fillContainer.findViewById(R.id.sub_container);
        title.setText(subModel.indexStr + subModel.projectText);
        if (subModel.subItemModelList.size() > 0) {
            for (ReportModelType7.ReportModelType7SubItemModel subItemModel : subModel.subItemModelList) {
                View inflate = View.inflate(getContext(), R.layout.report_fill_type_7_sub_item, null);
                TextView subCheckText = (TextView) inflate.findViewById(R.id.sub_check_text);
                subCheckText.setText(subItemModel.checkInfo);
                subContainer.addView(inflate);
            }
        } else {
            List<ReportModelType7.ReportModelType7SubSubModel> checkInfoSubList = subModel.checkInfoSubList;
            for (int i = 0; i < checkInfoSubList.size(); i++) {
                ReportModelType7.ReportModelType7SubSubModel subSubModel = checkInfoSubList.get(i);
                View inflate = View.inflate(getContext(), R.layout.report_fill_type_7_item, null);
                TextView subTitle = (TextView) inflate.findViewById(R.id.sub_title);
                LinearLayout checkContainer1 = (LinearLayout) inflate.findViewById(R.id.sub_check_container1);
                LinearLayout checkContainer2 = (LinearLayout) inflate.findViewById(R.id.sub_check_container2);
                LinearLayout checkContainer3 = (LinearLayout) inflate.findViewById(R.id.sub_check_container3);
                ReportModelType7.ReportModelType7SubItemModel subSubModel0 = null;
                ReportModelType7.ReportModelType7SubItemModel subSubModel1 = null;
                ReportModelType7.ReportModelType7SubItemModel subSubModel2 = null;
                if (subSubModel.checkInfoSubList.size() > 0) {
                    subSubModel0 = subSubModel.checkInfoSubList.get(0);
                }
                if (subSubModel.checkInfoSubList.size() > 1) {
                    subSubModel1 = subSubModel.checkInfoSubList.get(1);
                }
                if (subSubModel.checkInfoSubList.size() > 2) {
                    subSubModel2 = subSubModel.checkInfoSubList.get(2);
                }
                subTitle.setText(subSubModel.indexStr + subSubModel.projectText);
                bindType7Item(checkContainer1, subSubModel0);
                bindType7Item(checkContainer2, subSubModel1);
                bindType7Item(checkContainer3, subSubModel2);
                subContainer.addView(inflate);
            }
        }
    }

    private void bindType7Item(LinearLayout checkContainer, ReportModelType7.ReportModelType7SubItemModel subSubModel) {
        if (subSubModel == null) {
            checkContainer.setVisibility(View.GONE);
            return;
        }
        checkContainer.setVisibility(View.VISIBLE);
        TextView checkText = (TextView) checkContainer.findViewById(R.id.sub_check_text);
        EditText checkEdit = (EditText) checkContainer.findViewById(R.id.sub_check_edit);
        checkText.setText(subSubModel.checkInfo);
    }


    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_THREE;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            ReportModelType7 reportModelType7 = typeService.readReportType(open);
            mReportBuildModel.reportModelType7 = reportModelType7;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        ReportModelType7 reportModelType7 = model.reportModelType7;
        //获取值
        String workArea = mReportHeaderWorkArea.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        model.buildType = ReportBuildModel.BUILD_TYPE_SEVEN;

        reportModelType7.workAreaText = workArea;
        reportModelType7.stationText = station;
        reportModelType7.checkText = checker;
        reportModelType7.dateText = data;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            TextView title = (TextView) childAt.findViewById(R.id.title);
            LinearLayout subContainer = (LinearLayout) childAt.findViewById(R.id.sub_container);
            ReportModelType7.ReportModelType7SubModel subModel = reportModelType7.reportItemModelList.get(i);//济柴燃气发电机组
            for (int j = 0; j < subContainer.getChildCount(); j++) {
                View childAt1 = subContainer.getChildAt(j);
                int id = childAt1.getId();
                if (id == R.id.report_fill_type_7_item) {
                    ReportModelType7.ReportModelType7SubSubModel subSubModel = subModel.checkInfoSubList.get(j);//发动机
                    for (int k = 0; k < subSubModel.checkInfoSubList.size(); k++) {
                        ReportModelType7.ReportModelType7SubItemModel reportModelType7SubItemModel = subSubModel.checkInfoSubList.get(k);
                        LinearLayout subCheck_container;
                        if (k == 0) {
                            subCheck_container = (LinearLayout) childAt1.findViewById(R.id.sub_check_container1);
                        } else if (k == 1) {
                            subCheck_container = (LinearLayout) childAt1.findViewById(R.id.sub_check_container2);
                        } else {
                            subCheck_container = (LinearLayout) childAt1.findViewById(R.id.sub_check_container3);
                        }
                        TextView subCheckText = (TextView) subCheck_container.findViewById(R.id.sub_check_text);
                        EditText subCheckEdit = (EditText) subCheck_container.findViewById(R.id.sub_check_edit);
                        reportModelType7SubItemModel.checkInfo = subCheckText.getText().toString();
                        reportModelType7SubItemModel.checkDescText = subCheckEdit.getText().toString();
                    }
                } else {
                    LinearLayout container = (LinearLayout) childAt1;
                    TextView subCheckText = (TextView) container.findViewById(R.id.sub_check_text);
                    EditText subCheckEdit = (EditText) container.findViewById(R.id.sub_check_edit);
                    ReportModelType7.ReportModelType7SubItemModel reportModelType7SubItemModel = subModel.subItemModelList.get(j);
                    reportModelType7SubItemModel.checkInfo = subCheckText.getText().toString();
                    reportModelType7SubItemModel.checkDescText = subCheckEdit.getText().toString();
                }
            }
        }
        reportModelType7.descText = mDescEdit.getText().toString();
        return model;
    }

}

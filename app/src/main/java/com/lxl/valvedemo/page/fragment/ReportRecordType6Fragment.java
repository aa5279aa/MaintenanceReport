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
import com.lxl.valvedemo.model.buildModel.type6.ReportModelType6;
import com.lxl.valvedemo.service.BuildType3Service;
import com.lxl.valvedemo.service.BuildType6Service;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.HotelViewHolder;
import com.lxl.valvedemo.util.StringUtil;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType6Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportHeaderConfirm;
    EditText mReportHeaderDate;
    TextView mFillAdd;

    LinearLayout mReportFillContainer;

    BuildType6Service typeService = new BuildType6Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_6, container, false);
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
        mReportHeaderConfirm = (EditText) view.findViewById(R.id.report_header_confirm);
        mReportHeaderDate = (EditText) view.findViewById(R.id.report_header_date);


        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
    }


    private void bindDataType(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        ReportModelType6 reportModelType6 = mReportBuildModel.reportModelType6;
        for (ReportModelType6.ReportModelType6SubModel subModel : reportModelType6.reportItemModelList) {
            View inflate = View.inflate(getContext(), R.layout.report_fill_type_6_item, null);
            TextView typeTitle = (TextView) inflate.findViewById(R.id.type6_title);
            TextView checkInfo1 = (TextView) inflate.findViewById(R.id.check_info1);
            TextView checkInfo2 = (TextView) inflate.findViewById(R.id.check_info2);
            typeTitle.setText(subModel.projectText);
            String str1 = "";
            String str2 = "";
            if (subModel.checkInfoList.size() >= 1) {
                str1 = subModel.checkInfoList.get(0);
            }
            if (subModel.checkInfoList.size() >= 2) {
                str2 = subModel.checkInfoList.get(1);
            }
            HotelViewHolder.showText(checkInfo1, str1);
            HotelViewHolder.showText(checkInfo2, str2);
            mReportFillContainer.addView(inflate);
        }
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_SIX;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            mReportBuildModel.reportModelType6 = typeService.readReportType(open);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportModelType6 reportModelType6 = mReportBuildModel.reportModelType6;
        //获取值
        String workArea = mReportHeaderWorkArea.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        String confirm = mReportHeaderConfirm.getText().toString();

        reportModelType6.workAreaText = workArea;
        reportModelType6.stationText = station;
        reportModelType6.checkerText = checker;
        reportModelType6.dateText = data;
        reportModelType6.confirmText = confirm;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            EditText checkDescEdit = (EditText) childAt.findViewById(R.id.check_desc);
            String desc = checkDescEdit.getText().toString();
            reportModelType6.reportItemModelList.get(i).checkDesc = desc;
        }
        return mReportBuildModel;
    }

}

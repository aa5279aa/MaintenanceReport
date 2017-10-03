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
import com.lxl.valvedemo.model.buildModel.type4.AlertReportModel;
import com.lxl.valvedemo.service.BuildTye4Service;
import com.lxl.valvedemo.util.DateUtil;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType4Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mStandard;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportHeaderDate;
    TextView mFillAdd;

    LinearLayout mReportFillContainer;

    BuildTye4Service type4Service = new BuildTye4Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType4(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mStandard = (EditText) view.findViewById(R.id.standard);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.checker);
        mReportHeaderDate = (EditText) view.findViewById(R.id.check_data);
        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
        mFillAdd = (TextView) view.findViewById(R.id.report_fill_add);
    }


    private void bindDataType4(View view) {
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
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_4_additem, null);
        TextView positionView = (TextView) inflate.findViewById(R.id.index);
        positionView.setText("序号：" + String.valueOf(mReportFillContainer.getChildCount() + 1));
        fillContainer.addView(inflate);
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_FOUR;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            AlertReportModel alertReportModel = type4Service.readReportTypeFour(open);
            mReportBuildModel.alertReportModel = alertReportModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        AlertReportModel alertReportModel = model.alertReportModel;
        //获取值
        String standard = mStandard.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();

        alertReportModel.checkDateText = data;
        alertReportModel.checkerText = checker;
        alertReportModel.stationText = station;
        alertReportModel.standardText = standard;

        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            EditText installLocaton = (EditText) childAt.findViewById(R.id.report_fill_install_location);
            EditText showValueEdit = (EditText) childAt.findViewById(R.id.report_fill_show_value);
//            TextView indexEdit = (TextView) childAt.findViewById(R.id.index);

            String installLocationStr = installLocaton.getText().toString();
            String showValueStr = showValueEdit.getText().toString();
//            String indexStr = indexEdit.getText().toString();

            AlertReportModel.AlertReportItemModel itemModel = new AlertReportModel.AlertReportItemModel();
            itemModel.position = i + 1;
            itemModel.installPosition = installLocationStr;
            itemModel.showValue = showValueStr;
            model.alertReportModel.reportItemModelList.add(itemModel);
        }
        return model;
    }

}

package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByBase;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByCPU;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByESD;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByNormal;
import com.lxl.valvedemo.service.BuildType3Service;
import com.lxl.valvedemo.util.DateUtil;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType3Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderCreate;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    TextView mReportHeaderDate;

    LinearLayout mReportFillContainer;

    BuildType3Service tyeThreeService = new BuildType3Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mReportHeaderStation = (EditText) view.findViewById(R.id.report_header_station);
        mReportHeaderDate = (TextView) view.findViewById(R.id.report_header_date);
        mReportHeaderCreate = (EditText) view.findViewById(R.id.report_header_create);//厂区
        mReportHeaderChecker = (EditText) view.findViewById(R.id.report_header_checker);
        mReportFillContainer = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
    }


    private void bindDataType(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        MaintainReportByAreaModel maintainReportByArea = mReportBuildModel.maintainReportByArea;
        for (MaintainReportBySCADA reportBySCADA : maintainReportByArea.scadaList) {
            View inflate = View.inflate(getContext(), R.layout.report_fill_type_3_sub, null);
            TextView checkTitle = (TextView) inflate.findViewById(R.id.check_title);
            LinearLayout checkContainer = (LinearLayout) inflate.findViewById(R.id.check_container);
            checkTitle.setText(reportBySCADA.scadaTitle);
            bindSub(checkContainer, reportBySCADA);
            mReportFillContainer.addView(inflate);
        }
    }

    private void bindSub(LinearLayout container, MaintainReportBySCADA reportBySCADA) {
        for (MaintainReportSubByBase subByBase : reportBySCADA.subList) {
            if (subByBase instanceof MaintainReportSubByCPU) {
                bindCPUModel(container, (MaintainReportSubByCPU) subByBase);
            } else if (subByBase instanceof MaintainReportSubByESD) {
                bindESDModel(container, (MaintainReportSubByESD) subByBase);
            } else {
                bindNormalModel(container, (MaintainReportSubByNormal) subByBase);
            }

        }
    }

    private void bindCPUModel(LinearLayout container, MaintainReportSubByCPU subByCPU) {
        //冀宁 平台
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_3_item_cpu, null);
        TextView checkTitle = (TextView) inflate.findViewById(R.id.check_title);
        LinearLayout checkContainer = (LinearLayout) inflate.findViewById(R.id.check_info_container);
        checkTitle.setText(subByCPU.cpuTitle);
        for (MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue : subByCPU.cpuSubList) {
            for (int i = 0; i < cpuSubValue.cpuItemValueList.size(); i++) {
                MaintainReportSubByCPU.MaintainReportByCPUItemValue maintainReportByCPUItemValue = cpuSubValue.cpuItemValueList.get(i);
                View inflate1 = View.inflate(getContext(), R.layout.report_fill_type_3_item_cpu_item, null);
                TextView checkInfo1 = (TextView) inflate1.findViewById(R.id.check_info_1);
                TextView checkInfo2 = (TextView) inflate1.findViewById(R.id.check_info_2);
//                EditText checkInfo3 = (EditText) inflate1.findViewById(R.id.check_info_3);
//                EditText checkInfo4 = (EditText) inflate1.findViewById(R.id.check_info_4);
                EditText checkInfo5 = (EditText) inflate1.findViewById(R.id.check_info_5);
                EditText checkInfo6 = (EditText) inflate1.findViewById(R.id.check_info_6);
                if (mReportBuildModel.maintainReportByArea.tableName.contains("冀宁")) {
                    checkInfo5.setVisibility(View.GONE);
                    checkInfo6.setVisibility(View.GONE);
                }
                checkInfo1.setText(cpuSubValue.cpuSubName);
                checkInfo2.setText(maintainReportByCPUItemValue.subItemName);
                checkContainer.addView(inflate1);
            }
        }
        container.addView(inflate);
    }

    private void bindESDModel(LinearLayout container, MaintainReportSubByESD subByESD) {
        //冀宁 平台
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_3_item_esd, null);
        LinearLayout checkContainer = (LinearLayout) inflate.findViewById(R.id.check_info_container);
        for (MaintainReportSubByESD.MaintainReportByESDItemValue esdItemValue : subByESD.esdItemValueList) {
            View inflate1 = View.inflate(getContext(), R.layout.report_fill_type_3_item_esd_item, null);
            TextView checkTitle = (TextView) inflate1.findViewById(R.id.check_title);
            checkTitle.setText(esdItemValue.rowTitle);
            checkContainer.addView(inflate1);
        }
        container.addView(inflate);
    }

    private void bindNormalModel(LinearLayout container, MaintainReportSubByNormal subByNormal) {
        View inflate = View.inflate(getContext(), R.layout.report_fill_type_3_item_normal, null);
        TextView checkTitle = (TextView) inflate.findViewById(R.id.check_title);
        LinearLayout checkInfoContainer = (LinearLayout) inflate.findViewById(R.id.check_container);
        checkTitle.setText(subByNormal.subNormalTitle);
        for (MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue : subByNormal.normalItemValueList) {
            String columDesc = normalItemValue.columDesc;
            View inflate1 = View.inflate(getContext(), R.layout.report_fill_type_7_sub_item, null);
            TextView checkText = (TextView) inflate1.findViewById(R.id.sub_check_text);
            EditText checkEdit = (EditText) inflate1.findViewById(R.id.sub_check_edit);
            checkText.setText(columDesc);
            checkInfoContainer.addView(inflate1);
        }
        container.addView(inflate);
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_THREE;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            MaintainReportByAreaModel maintainReportByAreaModel = tyeThreeService.readReportType(open);
            mReportBuildModel.maintainReportByArea = maintainReportByAreaModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        MaintainReportByAreaModel maintainReportByArea = model.maintainReportByArea;
        //获取值
        String create = mReportHeaderCreate.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        model.buildType = ReportBuildModel.BUILD_TYPE_THREE;
        maintainReportByArea.stationText = station;
        maintainReportByArea.checkerText = checker;
        maintainReportByArea.productText = create;
        maintainReportByArea.dateText = data;

        //获取
        int childCount = mReportFillContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mReportFillContainer.getChildAt(i);
            TextView checkTitle = (TextView) childAt.findViewById(R.id.check_title);
            LinearLayout checkContainer = (LinearLayout) childAt.findViewById(R.id.check_container);
            int subChildCount = checkContainer.getChildCount();
            MaintainReportBySCADA reportBySCADA = maintainReportByArea.scadaList.get(i);
            for (int j = 0; j < subChildCount; j++) {
                MaintainReportSubByBase subByBase = reportBySCADA.subList.get(j);
                View childAt1 = checkContainer.getChildAt(j);
                int id = childAt1.getId();
                if (id == R.id.report_fill_type_3_item_esd) {
                    MaintainReportSubByESD subByESD = (MaintainReportSubByESD) subByBase;
                    LinearLayout checkInfoContainer = (LinearLayout) childAt1.findViewById(R.id.check_info_container);
                    for (int k = 0; k < checkInfoContainer.getChildCount(); k++) {
                        View childAt2 = checkInfoContainer.getChildAt(k);
                        EditText checkEdit1 = (EditText) childAt2.findViewById(R.id.check_edit1);
                        EditText checkEdit2 = (EditText) childAt2.findViewById(R.id.check_edit2);
                        EditText checkEdit3 = (EditText) childAt2.findViewById(R.id.check_edit3);
                        subByESD.esdItemValueList.get(k).rowText1 = checkEdit1.getText().toString();
                        subByESD.esdItemValueList.get(k).rowText2 = checkEdit2.getText().toString();
                        subByESD.esdItemValueList.get(k).rowText3 = checkEdit3.getText().toString();
                    }
                } else if (id == R.id.report_fill_type_3_item_cpu) {
                    MaintainReportSubByCPU subByCPU = (MaintainReportSubByCPU) subByBase;
                    LinearLayout checkInfoContainer = (LinearLayout) childAt1.findViewById(R.id.check_info_container);

                    int index = 0;
                    int lastSub = 0;
                    for (int k = 0; k < checkInfoContainer.getChildCount(); k++) {
                        LinearLayout linearLayout = (LinearLayout) checkInfoContainer.getChildAt(k).findViewById(R.id.report_fill_type_3_item_cpu_item);
                        TextView checkInfo1 = (TextView) linearLayout.findViewById(R.id.check_info_1);
                        TextView checkInfo2 = (TextView) linearLayout.findViewById(R.id.check_info_2);
                        EditText checkInfo3 = (EditText) linearLayout.findViewById(R.id.check_info_3);
                        EditText checkInfo4 = (EditText) linearLayout.findViewById(R.id.check_info_4);
                        EditText checkInfo5 = (EditText) linearLayout.findViewById(R.id.check_info_5);
                        EditText checkInfo6 = (EditText) linearLayout.findViewById(R.id.check_info_6);
                        MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue = subByCPU.cpuSubList.get(index);
                        if (cpuSubValue.cpuItemValueList.size() <= lastSub) {
                            index++;
                            lastSub = 0;
                        }
                        String checkInfo1Str = checkInfo1.getText().toString();
                        String checkInfo2Str = checkInfo2.getText().toString();

                        cpuSubValue.cpuItemValueList.get(lastSub).subItemValueText1 = checkInfo3.getText().toString();
                        cpuSubValue.cpuItemValueList.get(lastSub).subItemValueText2 = checkInfo4.getText().toString();
                        cpuSubValue.cpuItemValueList.get(lastSub).subItemValueText3 = checkInfo5.getText().toString();
                        cpuSubValue.cpuItemValueList.get(lastSub).subItemValueText4 = checkInfo6.getText().toString();
                        lastSub++;
                        Log.i("lxltest", "size：" + cpuSubValue.cpuItemValueList.size());
                    }
                } else {
                    MaintainReportSubByNormal subByNormal = (MaintainReportSubByNormal) subByBase;
                    TextView itemCheckTitle = (TextView) childAt1.findViewById(R.id.check_title);
                    LinearLayout itemCheckContainer = (LinearLayout) childAt1.findViewById(R.id.check_container);
                    for (int k = 0; k < itemCheckContainer.getChildCount(); k++) {
                        View childAt2 = itemCheckContainer.getChildAt(k);
//                        if(childAt2 == null){
//                            Log.i("lxltest","");
//                        }
                        TextView subCheckText = (TextView) childAt2.findViewById(R.id.sub_check_text);
                        EditText subCheckEdit = (EditText) childAt2.findViewById(R.id.sub_check_edit);
                        subByNormal.normalItemValueList.get(k).columText = subCheckEdit.getText().toString();
                    }
                }
            }
        }


        return model;
    }

}

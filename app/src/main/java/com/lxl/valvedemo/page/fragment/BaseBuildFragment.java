package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lxl.valvedemo.model.buildModel.ReportBuildModel;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public abstract class BaseBuildFragment extends Fragment {
    ReportBuildModel mReportBuildModel;
    String mPath = "";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReportBuildModel = getBuildModel();
    }

    public void setExecl(String path) {
        this.mPath = path;
    }

    public abstract ReportBuildModel getBuildModel();

    public abstract ReportBuildModel buildBuildModel();

}

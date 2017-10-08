package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import com.lxl.valvedemo.model.buildModel.ReportBuildModel;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public abstract class BaseBuildFragment extends Fragment {
    ReportBuildModel mReportBuildModel;
    String mPath = "";

    ScrollView scrollView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof ScrollView) {
            scrollView = (ScrollView) view;
        }
        mReportBuildModel = getBuildModel();
    }

    public void gotoTop() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
//                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
//                        scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    public void setExecl(String path) {
        this.mPath = path;
    }

    public abstract ReportBuildModel getBuildModel();

    public abstract ReportBuildModel buildBuildModel();

}

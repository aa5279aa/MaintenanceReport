package com.lxl.valvedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/9/29.
 */
public class LocationRecordShowView extends RelativeLayout {

    int mWidth = 0;//
    int mHeight = 0;//
    List<LocationRecordModel> mRecordModelList = new ArrayList<>();

    public LocationRecordShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewWidth(int width) {
        this.mWidth = width;
    }

    public void setViewHeight(int height) {
        this.mHeight = height;
    }

    public void setRecordList(List<LocationRecordModel> recordModelList) {
        this.mRecordModelList = recordModelList;
    }

    public void initView() {
        int[] maxWidthAndHeight = getMaxWidthAndHeight(mRecordModelList);
        int maxWidth = maxWidthAndHeight[0];
        int maxHeight = maxWidthAndHeight[1];

        for (LocationRecordModel recordModel : mRecordModelList) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2, -2);
            lp.setMargins(recordModel.x, recordModel.y, 0, 0);
            TextView text = new TextView(getContext());
            text.setText(recordModel.longitude + "_" + recordModel.latitude);
            addView(text);
        }


    }

    private int[] getMaxWidthAndHeight(List<LocationRecordModel> recordModelList) {
        int[] maxWidthAndHeight = new int[2];


        return maxWidthAndHeight;
    }


}

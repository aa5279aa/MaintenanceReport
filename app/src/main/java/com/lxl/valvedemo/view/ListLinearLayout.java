package com.lxl.valvedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xiangleiliu on 2017/8/27.
 */
public class ListLinearLayout extends LinearLayout {
    private SelectionAdapter adapter;

    public ListLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setAdapter(SelectionAdapter adapter) {
        this.adapter = adapter;
    }

    public void notifyDataSetChanged() {
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(0, null, this);
            addView(view);
            addView(createDivision());
        }
    }

    private View createDivision() {
        View division = new View(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, 10);
        division.setLayoutParams(lp);
        return division;
    }

    public void addChildView(View inflate) {
        addView(inflate);
        addView(createDivision());
    }
}

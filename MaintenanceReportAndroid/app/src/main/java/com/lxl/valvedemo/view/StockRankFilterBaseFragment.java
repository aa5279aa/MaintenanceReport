package com.lxl.valvedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.lxl.valvedemo.R;


/**
 * Created by xiangleiliu on 2017/10/23.
 */
public abstract class StockRankFilterBaseFragment extends Fragment implements View.OnClickListener {

    //    public static final String StockRankFilterGroupModelTag = "StockRankFilterGroupModelTag";
    public static final String SelectItemIndexTag = "SelectItemIndexTag";
    public static final int FilterFragmentCode = 123456;

    int index;
    View mFilterClear;
    View mFilterOK;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stock_rank_filter_block_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilterClear = view.findViewById(R.id.filter_clear);
        mFilterOK = view.findViewById(R.id.filter_ok);
        if (mFilterClear != null && mFilterOK != null) {
            mFilterClear.setOnClickListener(this);
            mFilterOK.setOnClickListener(this);
        }
        initData();
        initView(view);
        bindData();
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filter_clear) {
            //清空筛选
            clearSelect();
        } else if (v.getId() == R.id.filter_ok) {
            submitFilter("");
            closeFragment();
        } else if (v instanceof TextView) {
            submitFilter(((TextView) v).getText().toString());
            closeFragment();
        } else {
            closeFragment();
        }
    }

    private void closeFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected abstract void submitFilter(String str);


    protected void initData() {
        index = getArguments().getInt(SelectItemIndexTag);
    }

    protected abstract void bindData();

    protected abstract void initView(View view);

    protected abstract void clearSelect();

}

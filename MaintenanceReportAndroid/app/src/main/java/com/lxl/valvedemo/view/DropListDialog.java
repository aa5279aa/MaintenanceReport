package com.lxl.valvedemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lxl.valvedemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yanglei on 2018/3/14.
 */

public class DropListDialog {
    Context mContext;
    PopupWindow pop;
    List<String> data = new ArrayList<>();
    private ListView listView;
    private LayoutInflater mInflater;
    //自定义适配器
    private MyAdapter mAdapter;
    private int heiht;
    private int mPostion = 0;
    private StockTextView mTextName;
    private boolean isPopShow;
    private OnItemSelectedListenerSpinner onItemSelectedListener;


    public DropListDialog(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void show(StockTextView textName, List<String> dataList) {
        data = dataList;
        mTextName = textName;
        mAdapter = new MyAdapter();
        listView = new ListView(mContext);
        listView.setCacheColorHint(0x00000000);
        listView.setDividerHeight(0);
        listView.setBackgroundColor(Color.rgb(255, 255, 255));
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mPostion = i;
                mAdapter.notifyDataSetChanged();
                mTextName.setText(data.get(i));
                pop.dismiss();
                isPopShow = false;
                onItemSelectedListener.onItemSelected(view, i, l);
            }
        });

        if (heiht == 0) {
            int hei = setListViewHeightBasedOnChildren(listView);
            //这里设置下拉框的高度
            if (hei >= 550) {
                pop = new PopupWindow(listView, mTextName.getWidth(), 550, true);
            } else {
                pop = new PopupWindow(listView, mTextName.getWidth(), hei, true);
            }
        } else {
            pop = new PopupWindow(listView, mTextName.getWidth(), heiht, true);
        }
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setFocusable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopShow = false;
                mTextName.setCompoundDrawable(mInflater.getContext().getResources().getDrawable(R.drawable.hotel_arrow_down_blue), 2, 10, 10);
            }
        });
        mTextName.setCompoundDrawable(mInflater.getContext().getResources().getDrawable(R.drawable.hotel_arrow_up_blue), 2, 10, 10);
        pop.showAsDropDown(mTextName, 0, 0);
        isPopShow = false;
    }


    public static int setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        int ff = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        return ff;
    }

    public void setOnItemSelectedListener(OnItemSelectedListenerSpinner onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item, null);
            if (position == mPostion) {
                //选中条目的背景色
                view.setBackgroundColor(Color.rgb(26, 208, 189));
            }
            final TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(data.get(position));
            //设置按钮的监听事件
            view.setTag(tv_name);
            return view;
        }

    }

    public interface OnItemSelectedListenerSpinner {

        void onItemSelected(View view1, int i, long l);
    }

}

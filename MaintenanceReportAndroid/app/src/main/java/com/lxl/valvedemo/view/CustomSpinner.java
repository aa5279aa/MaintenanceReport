package com.lxl.valvedemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CustomSpinner extends LinearLayout {

    private View view;
    private TextView tv_name;
    private ImageView ib;

    //界面控件
    private ImageView spinner;
    //构造qq号用到的集合
    private List<String> list = new ArrayList<String>();
    //布局加载器
    //自定义适配器
    private MyAdapter mAdapter;
    //PopupWindow
    private PopupWindow pop;
    //是否显示PopupWindow，默认不显示
    private boolean isPopShow = true;
    private ListView listView;
    private LayoutInflater mInflater;
    private OnItemSelectedListenerSpinner onItemSelectedListener;
    private int heiht;
    private int postion = 0;

    public CustomSpinner(Context context) {
        super(context);
        initView(context);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.layout_customspinner, null);

        mAdapter = new MyAdapter();
        tv_name = (TextView) view.findViewById(R.id.et_name);
        ib = (ImageView) view.findViewById(R.id.spinner);
        tv_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != list) {
                    if (pop == null) {
                        listView = new ListView(context);
                        listView.setCacheColorHint(0x00000000);
                        listView.setDividerHeight(0);
                        listView.setBackgroundColor(Color.rgb(255, 255, 255));
                        listView.setAdapter(mAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                postion = i;
                                mAdapter.notifyDataSetChanged();
                                tv_name.setText(list.get(i));
                                ib.setImageResource(R.drawable.stock_common_icon_arrow);
                                pop.dismiss();
                                isPopShow = true;
                                CustomSpinner.this.view.setTag(getId());
                                onItemSelectedListener.onItemSelected(CustomSpinner.this.view, view, i, l);
                            }
                        });

                        if (heiht == 0) {
                            int hei = setListViewHeightBasedOnChildren(listView);
                            //这里设置下拉框的高度
                            if (hei >= 550) {
                                pop = new PopupWindow(listView, CustomSpinner.this.view.getWidth(), 550, true);
                            } else {
                                pop = new PopupWindow(listView, CustomSpinner.this.view.getWidth(), hei, true);
                            }
                        } else {
                            pop = new PopupWindow(listView, CustomSpinner.this.view.getWidth(), heiht, true);
                        }
                        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
                        pop.setFocusable(true);
                        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                isPopShow = true;
                                ib.setImageResource(R.drawable.stock_common_icon_arrow);
                            }
                        });
                        ib.setImageResource(R.drawable.stock_common_icon_arrow);
                        pop.showAsDropDown(view, 0, 0);
                        isPopShow = false;
                    } else {
                        if (isPopShow) {
                            ib.setImageResource(R.drawable.hotel_arrow_up_blue); //向上的箭头
                            pop.showAsDropDown(view, 0, 0);
                            isPopShow = false;
                        } else {
                            ib.setImageResource(R.drawable.hotel_arrow_down_blue); //向下的箭头
                            pop.dismiss();
                            isPopShow = true;
                        }
                    }
                }
                onClickCustom();
            }
        });
        if (list == null || list.size() == 0) {
            tv_name.setText("");
        } else {
            tv_name.setText(list.get(0));
        }
        addView(view);
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

    public void onClickCustom() {

    }

    public void attachDataSource(String tabName, List<String> list) {
        this.list = list;
        tv_name.setText(tabName);
    }

    public void setOnItemSelectedListener(OnItemSelectedListenerSpinner onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        Log.e("www", "走了");
    }

    public void setSpinnerHeiht(int heiht) {
        this.heiht = heiht;
    }

    public void setSelectedIndex(int index) {
        tv_name.setText(list.get(index));

        onItemSelectedListener.onItemSelected(null, null, index, index);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item, null);
            if (position == CustomSpinner.this.postion) {
                //选中条目的背景色
                view.setBackgroundColor(Color.rgb(26, 208, 189));
            }
            final TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(list.get(position));
            //设置按钮的监听事件
            view.setTag(tv_name);
            return view;
        }

    }

    @Override
    public void destroyDrawingCache() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
        super.destroyDrawingCache();
    }

    public interface OnItemSelectedListenerSpinner {

        void onItemSelected(View view, View view1, int i, long l);
    }

}
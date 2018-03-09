package com.lxl.valvedemo.util;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 */
public class HotelViewHolder {

    public static <T extends View> T requestView(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View view = viewHolder.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            viewHolder.put(id, view);
        }
        return (T) view;
    }

    public static void showText(View text, CharSequence msg) {
        if (!(text instanceof TextView)) {
            return;
        }
        TextView textView = (TextView) text;

        if (msg == null || StringUtil.emptyOrNull(msg.toString())) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(msg);
        }
    }

    public static void showTextOrDefault(View text, CharSequence msg, String defaultStr) {
        if (!(text instanceof TextView)) {
            return;
        }
        TextView textView = (TextView) text;
        textView.setVisibility(View.VISIBLE);
        if (msg == null || StringUtil.emptyOrNull(msg.toString())) {
            msg = defaultStr;
        }
        textView.setText(msg);
    }
}

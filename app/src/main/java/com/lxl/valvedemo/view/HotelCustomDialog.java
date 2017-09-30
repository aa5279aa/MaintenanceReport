package com.lxl.valvedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxl.valvedemo.R;


/**
 * @author XU_X
 * @ClassName: HotelCustomDialog
 * @Description: 对应一些需要自定义的dialog 公共提供的 ,有些样式不能自己定义样式
 * @date 2014-10-27 下午5:39:06
 */
public class HotelCustomDialog extends DialogFragment implements
        OnClickListener {
    private TextView mDlgTitle;// 文本title
    private TextView mDlgContent;// 文本view
    private TextView mLeftButton;// 左按钮，如果就一个,若要控制显示只一个按钮，只要第一个按钮文案不为null就可以
    private TextView mRightButton;// 右按钮
    private HotelDialogBtnClickListener mBtnClickListener;// 按钮点击监听
    private int mContentGravity = Gravity.CENTER;

    private CharSequence mTitle;//tipcontent
    private CharSequence mContent;//tipcontent
    private CharSequence mLeftBtnStyle;//left btn文案，只要第一个按钮文案不为null就可以
    private CharSequence mRightBtnStyle;//right btn文案

    private Object mBindData;

    public void setBindData(Object data) {
        mBindData = data;
    }

    public Object getBindData() {
        return mBindData;
    }

    public interface HotelDialogBtnClickListener {
        public void leftBtnClick(HotelCustomDialog dialog);

        public void rightBtnClick(HotelCustomDialog dialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeHolo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotel_custom_dialog_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDlgTitle = (TextView) view.findViewById(R.id.title_text);
        mDlgContent = (TextView) view.findViewById(R.id.content_text);
        mLeftButton = (TextView) view.findViewById(R.id.lef_btn);
        mLeftButton.setOnClickListener(this);
        view.setOnClickListener(dissmissClick);
        mRightButton = (TextView) view.findViewById(R.id.right_btn);
        mRightButton.setOnClickListener(this);
        if (mContent != null) {
            mDlgContent.setText(mContent);
            mDlgContent.setGravity(mContentGravity);
        }
        mDlgTitle.setText(mTitle);
        mDlgTitle.setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        if (mLeftBtnStyle != null) {
            mLeftButton.setText(mLeftBtnStyle);
        } else {
            mLeftButton.setVisibility(View.GONE);
        }
        if (mRightBtnStyle != null) {
            mRightButton.setText(mRightBtnStyle);
        } else {
            mRightButton.setVisibility(View.GONE);
            view.findViewById(R.id.btn_divider).setVisibility(View.GONE);
        }
    }

    /**
     * @param content       :文本内容
     * @param leftBtnStyle  ：第一个按钮样式 ，
     * @param rightBtnStyle ：第二个按钮样式,如果第二个不要，就直接传null值
     * @return void
     * @Title: setContent
     * @Description:定义内容，和按钮样式 最多2个按钮
     * @author XU_X
     * @date 2014-10-27 下午5:49:34
     */
    public void setContent(CharSequence content, CharSequence leftBtnStyle,
                           CharSequence rightBtnStyle) {
        mContent = content;
        mLeftBtnStyle = leftBtnStyle;
        mRightBtnStyle = rightBtnStyle;
    }

    public void setTitle(CharSequence content) {
        mTitle = content;
        if (mDlgTitle == null) {
            return;
        }
        mDlgTitle.setText(content);
        mDlgTitle.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
    }

    /**
     * @return void
     * @Title: setDialogBtnClick
     * @Description: 设置点击监听
     * @author XU_X
     * @date 2014-10-27 下午6:17:47
     */
    public void setDialogBtnClick(HotelDialogBtnClickListener clickAction) {
        mBtnClickListener = clickAction;
    }

    public void setContentGravity(int gravity) {
        mContentGravity = gravity;
    }

    private OnClickListener dissmissClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        dismiss();
        if (mBtnClickListener != null) {
            if (v.getId() == R.id.lef_btn) {
                mBtnClickListener.leftBtnClick(this);
            } else if (v.getId() == R.id.right_btn) {
                mBtnClickListener.rightBtnClick(this);
            }
        }
    }
}
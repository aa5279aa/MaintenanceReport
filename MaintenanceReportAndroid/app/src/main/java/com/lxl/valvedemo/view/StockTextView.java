package com.lxl.valvedemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lxl.valvedemo.R;


/**
 * Created by xiangleiliu on 2016/7/4.
 */
public class StockTextView extends TextView {


    public StockTextView(Context context) {
        this(context, null);
    }

    public StockTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StockTextView);

        Drawable drawable = a.getDrawable(R.styleable.StockTextView_stock_text_drawable_src);
        if (drawable != null) {
            int direction = a.getInt(R.styleable.StockTextView_stock_text_drawable_direction, 0);
            if (direction < 0 || direction > 3) {
                direction = 0;
            }

            int width = a.getDimensionPixelSize(R.styleable.StockTextView_stock_text_drawable_width, 0);
            int height = a.getDimensionPixelSize(R.styleable.StockTextView_stock_text_drawable_height, 0);
            int padding = a.getDimensionPixelSize(R.styleable.StockTextView_stock_text_drawable_padding, 0);
            setCompoundDrawablePadding(padding);
            setCompoundDrawable(drawable, direction, width, height);
        }

        a.recycle();
    }

    /**
     * 设置TextView的CompoundDrawable于默认方向（左）
     *
     * @param drawable CompoundDrawable对象
     */
    public void setCompoundDrawable(Drawable drawable) {
        setCompoundDrawable(drawable, 0, 0, 0);
    }

    /**
     * 设置TextView的CompoundDrawable
     *
     * @param drawable  CompoundDrawable对象
     * @param direction 显示方向
     * @param width     显示宽度, 等于0则按drawable实际宽度显示
     * @param height    显示高度, 等于0则按drawable实际高度显示
     */
    public void setCompoundDrawable(Drawable drawable, int direction, int width, int height) {
        if (drawable != null) {
            drawable.setBounds(0, 0,
                    width == 0 ? drawable.getIntrinsicWidth() : width,
                    height == 0 ? drawable.getIntrinsicHeight() : height);
        }

        switch (direction) {
            case 0:
                setCompoundDrawables(drawable, null, null, null);
                break;
            case 1:
                setCompoundDrawables(null, drawable, null, null);
                break;
            case 2:
                setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                break;
        }
    }
}

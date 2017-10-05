package com.lxl.valvedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/10/5 0005.
 */

public class DrawLineView extends View {
    private int mov_x;//声明起点坐标
    private int mov_y;
    private Paint linePaint;//声明画笔1
    private Paint pointPaint;//声明画笔2
    private Canvas canvas;//画布
    private Bitmap bitmap;//位图
    private int blcolor;

    public DrawLineView(Context context, AttributeSet attr) {
        super(context);
        linePaint = new Paint(Paint.DITHER_FLAG);//创建一个画笔
        linePaint.setStyle(Paint.Style.STROKE);//设置非填充
        linePaint.setStrokeWidth(2);//笔宽5像素
        linePaint.setColor(Color.YELLOW);//设置为红笔
        linePaint.setAntiAlias(true);//锯齿不显示

        pointPaint = new Paint(Paint.DITHER_FLAG);//创建一个画笔
        pointPaint.setStyle(Paint.Style.FILL);//设置非填充
        pointPaint.setStrokeWidth(10);//笔宽5像素
        pointPaint.setColor(Color.RED);//设置为红笔
        pointPaint.setAntiAlias(true);//锯齿不显示

        canvas = new Canvas();
    }

    public void initWidthAndHeight(int width, int height) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); //设置位图的宽高
        canvas.setBitmap(bitmap);
    }


    //画位图
    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {//如果拖动
//            canvas.drawLine(mov_x, mov_y, event.getX(), event.getY(), paint);//画线
//            invalidate();
//        }
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {//如果点击
//            mov_x = (int) event.getX();
//            mov_y = (int) event.getY();
//            canvas.drawPoint(mov_x, mov_y, paint);//画点
//            invalidate();
//        }
//        mov_x = (int) event.getX();
//        mov_y = (int) event.getY();
//        return true;
    }

    public void drawPoint(int x, int y) {
        canvas.drawPoint(x, y, pointPaint);//画点
        invalidate();
    }

    public void drawLine(int fromX, int fromY, int toX, int toY) {
        canvas.drawLine(fromX, fromY, toX, toY, linePaint);//画线
        invalidate();
    }
}
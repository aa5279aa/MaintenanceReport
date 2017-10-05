package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.viewmodel.LocationRecordModel;
import com.lxl.valvedemo.model.viewmodel.SingleSelectionModel;
import com.lxl.valvedemo.util.DeviceUtil;
import com.lxl.valvedemo.view.DrawLineView;
import com.lxl.valvedemo.view.StockTitleView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/5 0005.
 */

public class TrajectoryShowActivity extends Activity implements View.OnClickListener {

    public final static String RECORD = "RECORD";
    public final static String PATH = "PATH";
    ArrayList<LocationRecordModel> recordList = new ArrayList<>();
    String mPath;

    StockTitleView titleView;
    DrawLineView drawView;
    TextView openFile;
    TextView backBtn;

    int screenWidth;
    int[] center = new int[2];

    Handler mHander = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajectory_show_layout);
        screenWidth = DeviceUtil.getScreenWidth(this);
        initView();
        initData();
        bindData();
        initListener();

        final File file = new File(mPath + ReportBuildConfig.PNG_Suffix);
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewSaveToImage(file, drawView);
            }
        }, 1000);

    }

    private void initListener() {
        openFile.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }


    private void initView() {
        LinearLayout container = (LinearLayout) findViewById(R.id.contanier);
        titleView = (StockTitleView) findViewById(R.id.stock_title_view);
//        drawView = (DrawLineView) findViewById(R.id.stock_title_view2);
        openFile = (TextView) findViewById(R.id.open_file);
        backBtn = (TextView) findViewById(R.id.back_btn1);

        drawView = (DrawLineView) container.getChildAt(1);

        drawView.getLayoutParams().width = screenWidth;
        drawView.getLayoutParams().height = screenWidth;
        drawView.initWidthAndHeight(screenWidth, screenWidth);
    }

    private void initData() {
        ArrayList<LocationRecordModel> list = (ArrayList<LocationRecordModel>) getIntent().getSerializableExtra(RECORD);
        mPath = getIntent().getStringExtra(PATH);
        if (list == null) {
            recordList.addAll(DataConfig.getRecordList());
        } else {
            recordList.clear();
            recordList.addAll(list);
        }

        //初始化中心点位置
        center[0] = screenWidth / 2;
        center[1] = screenWidth / 2 + titleView.getLayoutParams().height;
    }


    private void bindData() {
        if (recordList.size() == 0) {
            return;
        }
        LocationRecordModel lastRecordModel = recordList.get(0);
        lastRecordModel.x = center[0];
        lastRecordModel.y = center[1];
        drawView.drawPoint(lastRecordModel.x, lastRecordModel.y);

        for (int i = 1; i < recordList.size(); i++) {
            LocationRecordModel recordModel = recordList.get(i);
            if (recordModel.latitude == lastRecordModel.latitude && recordModel.longitude == lastRecordModel.longitude) {
                lastRecordModel = recordModel;
                continue;
            }
            double diffLat = recordModel.latitude - lastRecordModel.latitude;
            double diffLong = recordModel.longitude - lastRecordModel.longitude;

            /**
             * 1经度100公里
             * 1000米的半径范围
             * 经度变化极限为0.005
             * 屏幕按照1000像素计算
             * 1000/2/0.005
             */
            int diffX = (int) (diffLat * 100000);
            int diffY = (int) (diffLong * 100000);

            recordModel.x = lastRecordModel.x + diffX;
            recordModel.y = lastRecordModel.y + diffY;
            drawView.drawPoint(recordModel.x, recordModel.y);
            drawView.drawLine(lastRecordModel.x, lastRecordModel.y, recordModel.x, recordModel.y);
            lastRecordModel = recordModel;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void viewSaveToImage(File file, View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        // 添加水印
        Bitmap bitmap = Bitmap.createBitmap(createWatermarkBitmap(cachebmp,
                "@轨迹记录图"));

        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    // 为图片target添加水印
    private Bitmap createWatermarkBitmap(Bitmap target, String str) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.RED);

        // 水印的字体大小
        p.setTextSize(16);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在中间位置开始添加水印
        canvas.drawText(str, w * 4 / 5, h * 4 / 5, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.open_file) {
            openAssignFolder(ReportBuildConfig.reportBuildPath);
        } else if (id == R.id.back_btn) {
            Intent intent = new Intent();
            intent.setClass(this, OperationActivity.class);
            startActivity(intent);
        }
    }

    private void openAssignFolder(String path) {
        File file = new File(path);
        if (null == file || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            startActivity(intent);
            startActivity(Intent.createChooser(intent, "选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
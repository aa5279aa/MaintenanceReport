package com.lxl.valvedemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.ReportBuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by xiangleiliu on 2017/8/24.
 */
public class StockTitleView extends RelativeLayout {

    Context mContext;
    TextView mTitle;
    ImageView mBackBtn;
    ImageView mActionBtn;

    int TAKE_PICTURE = 101;

    public StockTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StockTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StockTitleView);
        String title = a.getString(R.styleable.StockTitleView_stock_title_text_msg);
        int titleStyle = a.getResourceId(R.styleable.StockTitleView_stock_title_text_style, R.style.text_20_ffffff);
        a.recycle();
        inflate(context, R.layout.stock_view_basetitle, this);
        initView();
        setTitle(title, titleStyle);
        initListener();
    }

    private void initListener() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!(mContext instanceof Activity)) {
                    return;
                }
                ((Activity) mContext).finish();
            }
        });
        mActionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri fileUri = getOutputMediaFileUri();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                ((Activity) mContext).startActivityForResult(intent, TAKE_PICTURE);
            }
        });
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    public File mediaFile;

    public File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(ReportBuildConfig.getBaseBuildPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_view);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mActionBtn = (ImageView) findViewById(R.id.action_btn);
    }

    public void setTitle(String title) {
        setTitle(title, R.style.text_20_ffffff);
    }

    public void setTitle(String title, int style) {
        mTitle.setText(title);
        mTitle.setTextAppearance(mContext, style);
    }

    public void setActionBtnShow(int state) {
        mActionBtn.setVisibility(state);
    }

}

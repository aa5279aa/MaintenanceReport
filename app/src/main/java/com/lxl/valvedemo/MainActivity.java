package com.lxl.valvedemo;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lxl.valvedemo.config.DataConfig;
import com.lxl.valvedemo.page.SelectActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends Activity {
    TextView start;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView findViewById = (TextView) findViewById(R.id.text_title);
//        findViewById.setText(String.valueOf(getResources().getDisplayMetrics().density));
        findViewById.setText("维修队计量专业季度维护保养检查");
        start = (TextView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("lxltest", "start time:" + System.currentTimeMillis());
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SelectActivity.class);
                startActivity(intent);

            }
        });

//        final TextView imageSelect = (TextView) findViewById(R.id.image_select);
//        Drawable src = getResources().getDrawable(R.drawable.chat_comment_1);
//        Drawable drawable = tintDrawable(src, getResources().getColorStateList(R.color.selector_imagebutton_batman));
//        ColorStateList list=new ColorStateList();
//        imageSelect.setBackgroundDrawable(drawable);
//        imageSelect.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);

//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                imageSelect.setText("图片选择");
//            }
//        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void dumpState(Context context) {
        String currentThreadName = Thread.currentThread().getName();//当前线程名


    }


    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    public String test() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put("1");
        jsonArray.put("5");
        jsonArray.put("2");
        jsonArray.put("3");

        return jsonArray.toString();
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

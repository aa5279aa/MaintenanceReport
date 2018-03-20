package com.lxl.valvedemo.page;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.sender.StockSender;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.DeviceUtil;
import com.lxl.valvedemo.util.IOHelper;
import com.lxl.valvedemo.util.StockShowUtil;
import com.lxl.valvedemo.util.StringUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/8/24.
 * 账户注册
 */
public class StockRegisterActivity extends Activity implements View.OnClickListener {

    TextView mNextStep;
    EditText mPhoneEdit;

    HashMap<String, String> mUserMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_register_layout);
        initView();
        initData();
    }

    private void initData() {
        try {
            mUserMap.clear();
            InputStream user = getAssets().open("user.txt");
            List<String> strings = IOHelper.readListStrByCode(user, "utf-8");
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                mUserMap.put(split[0], split[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.version_text)).setText("版本号：" + DeviceUtil.getLocalVersionName(this));
    }

    private void initView() {
        mNextStep = (TextView) findViewById(R.id.stock_register_next_step);
        mNextStep.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.stock_register_next_step) {
            String user = ((EditText) findViewById(R.id.input_user)).getText().toString();
            String password = ((EditText) findViewById(R.id.input_password)).getText().toString();
            handleGoToNext(user, password);
        }
    }

    private void handleGoToNext(String user, String password) {
        String s = mUserMap.get(user);
//        if (StringUtil.emptyOrNull(s) || !s.equals(password)) {
//            StockShowUtil.showToastOnMainThread(this, "请输入正确的账号和密码！");
//            return;
//        }

        if (checkLoginPermission()) {
            Intent intent = new Intent();
//            intent.setClass(this, OperationActivity.class);
            intent.setClass(this, SelectActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //不可登录
        StockShowUtil.showToastOnMainThread(this, "不可登录状态，请联系管理员！");
    }

    /**
     * @return true为可登陆
     */
    private boolean checkLoginPermission() {
        final SharedPreferences login = getSharedPreferences("login", 0);
        String date = login.getString("date", "");
        final String currentDate = DateUtil.getCurrentDate();
        if (!date.equals(currentDate)) {
            //请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = StockSender.requestGet(StockSender.PermissionUrl, new HashMap<String, Object>(), "utf-8");
                    SharedPreferences.Editor edit = login.edit();
                    if ("noPermission".equals(s)) {
                        edit.putBoolean("canLogin", false);
                    } else if ("success".equals(s)) {
                        edit.putBoolean("canLogin", true);
                    }
                    edit.putString("date", currentDate);
                    edit.commit();
                }
            }).start();
        }
        boolean canLogin = login.getBoolean("canLogin", true);
        return canLogin;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(Activity.RESULT_OK);
        finish();
    }
}

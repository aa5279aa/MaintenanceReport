package com.lxl.valvedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends Activity {

	protected TextView titleText;
	protected TextView btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	protected void setParentContentView(){
		titleText = (TextView) findViewById(R.id.text_title);
		btnBack = (TextView) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendKeyBackEvent();
			}
		});
		titleText.setText(getClass().getSimpleName().replace("Activity", ""));
	}

	private void sendKeyBackEvent() {
		KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
				KeyEvent.KEYCODE_BACK);
		onKeyDown(KeyEvent.KEYCODE_BACK, keyEvent);
	}

}

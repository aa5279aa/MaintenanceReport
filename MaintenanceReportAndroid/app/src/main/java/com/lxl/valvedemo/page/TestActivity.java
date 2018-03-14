package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.view.DropListDialog;
import com.lxl.valvedemo.view.StockTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8 0008.
 */

public class TestActivity extends Activity {
    Context mContext;
    StockTextView textView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.test_layout);
        textView1 = (StockTextView) findViewById(R.id.filter1);
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DropListDialog dialog = new DropListDialog(mContext);
                dialog.setOnItemSelectedListener(new DropListDialog.OnItemSelectedListenerSpinner() {
                    @Override
                    public void onItemSelected(View view1, int i, long l) {
                        if (view1 instanceof TextView) {
                            String s = ((TextView) view1).getText().toString();
                            textView1.setText(s);
                        }
                    }
                });
                dialog.show(textView1, list);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

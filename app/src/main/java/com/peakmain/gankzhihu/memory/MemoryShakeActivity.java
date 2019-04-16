package com.peakmain.gankzhihu.memory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.peakmain.gankzhihu.R;

/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/16
 * mail:2726449200@qq.com
 * describe：模拟内存抖动的界面
 */
public class MemoryShakeActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 创造内存抖动
            for (int index = 0; index <= 100; index++){
                String arg[] = new String[100000];
            }
            mHandler.sendEmptyMessageDelayed(0,30);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        findViewById(R.id.bt_memory).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

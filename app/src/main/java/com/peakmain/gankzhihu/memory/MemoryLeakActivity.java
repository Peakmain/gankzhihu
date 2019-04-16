package com.peakmain.gankzhihu.memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.peakmain.gankzhihu.R;


/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/16
 * mail:2726449200@qq.com
 * describe：模拟内存泄露的Activity
 */
public class MemoryLeakActivity extends AppCompatActivity implements CallBack{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoryleak);
        ImageView imageView = findViewById(R.id.iv_memoryleak);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        imageView.setImageBitmap(bitmap);

        CallBackManager.addCallBack(this);
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        CallBackManager.removeCallBack(this);
    }
*/
    @Override
    public void dpOperate() {

    }
}

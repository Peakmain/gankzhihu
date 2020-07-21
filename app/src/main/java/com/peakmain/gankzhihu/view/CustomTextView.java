package com.peakmain.gankzhihu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.Executors;

/**
 * author ：Peakmain
 * createTime：2019/4/28
 * mail:2726449200@qq.com
 * describe：
 */
@Deprecated
public class CustomTextView extends View {
    private String text="测试StaticLayout";
    private TextPaint mTextPaint;
    private StaticLayout mStaticLayout;
    public CustomTextView(Context context) {
        this(context,null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLableView();
    }

    private void initLableView() {
        mTextPaint=new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(16*getResources().getDisplayMetrics().densityDpi);
        mTextPaint.setColor(Color.BLACK);
        int width= (int) mTextPaint.measureText(text);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mStaticLayout = new StaticLayout(text, mTextPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
                postInvalidate();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mStaticLayout != null){
            canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop());
            mStaticLayout.draw(canvas);
            canvas.restore();
        }
    }
}

package com.peakmain.gankzhihu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.peakmain.gankzhihu.R;


/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/7/18 0018 下午 7:16
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ShapeView extends View {
    //默认形状是圆形
    private Shape mCurrentShape = Shape.Circle;
    private Paint mPaint;
    //三角形的路径
    private Path mPath;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (mCurrentShape) {
            case Circle:
                // 画圆形
                int center = getWidth() / 2;
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.circle));
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                //画正方形
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.rect));
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triangle:
                // 画三角  Path 画路线
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.triangle));
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) ((getWidth() / 2) * Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) ((getWidth() / 2) * Math.sqrt(3)));
                    // path.lineTo(getWidth()/2,0);
                    mPath.close();// 把路径闭合
                }
                canvas.drawPath(mPath, mPaint);
                break;
            default:
                break;
        }
    }

    /**
     * 改变形状
     */
    public void exchange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                // 画三角  Path 画路线
                mCurrentShape = Shape.Circle;
                break;
            default:
                break;
        }
        // 不断重新绘制形状
        invalidate();
    }

    public enum Shape {
        //形状
        Circle, Square, Triangle
    }
    public Shape getCurrentShape() {
        return mCurrentShape;
    }
}

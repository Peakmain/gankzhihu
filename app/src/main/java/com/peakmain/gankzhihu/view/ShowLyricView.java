package com.peakmain.gankzhihu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Size;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.peakmain.gankzhihu.bean.video.Lyric;

import java.util.ArrayList;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/29 0029 下午 12:32
 * mail : 2726449200@qq.com
 * describe ：自定义歌词显示控件
 */
public class ShowLyricView extends android.support.v7.widget.AppCompatTextView {
    /**
     * 歌词列表
     */
    private ArrayList<Lyric> lyrics;
    //高亮的时候的颜色
    private Paint paint;
    //未高亮的时候的颜色
    private Paint whitepaint;
    private int width;
    private int height;
    /**
     * 歌词列表中的索引，是第几句歌词
     */
    private int index;
    /**
     * 当前播放进度
     */
    private float currentPositon;
    /**
     * 高亮显示的时间或者休眠时间
     */
    private float sleepTime;
    /**
     * 时间戳，什么时刻到高亮哪句歌词
     */
    private float timePoint;
    /**
     * 每行的高
     */
    private float textHeight;

    /**
     * 设置歌词列表
     *
     * @param lyrics
     */
    public void setLyrics(ArrayList<Lyric> lyrics) {
        this.lyrics = lyrics;
    }

    public ShowLyricView(Context context) {
        this(context, null);
    }

    public ShowLyricView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowLyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void initView(Context context) {
        textHeight = SizeUtils.dp2px(18);
        //创建画笔
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(SizeUtils.dp2px(16));
        paint.setAntiAlias(true);
        //设置居中对齐
        paint.setTextAlign(Paint.Align.CENTER);
        whitepaint = new Paint();
        whitepaint.setColor(Color.WHITE);
        whitepaint.setTextSize(SizeUtils.dp2px(16));
        whitepaint.setAntiAlias(true);
        //设置居中对齐
        whitepaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lyrics != null && lyrics.size() > 0) {
            //往上推移
            float plush = 0;
            if (sleepTime == 0) {
                plush = 0;
            } else {
                //平移
                //屏幕的的坐标 = 行高 + 移动的距离
                plush=textHeight+((currentPositon-timePoint)/sleepTime)*textHeight;
            }
            canvas.translate(0,-plush);
            //绘制歌词:绘制当前句
            String currentText=lyrics.get(index).getContent();
            canvas.drawText(currentText, width / 2, height / 2, paint);
            // 绘制前面部分
            float tempY = height / 2;//Y轴的中间坐标
            for (int i = index - 1; i >= 0; i--) {
                //每一句歌词
                String preContent = lyrics.get(i).getContent();
                tempY=tempY-textHeight;
                if(tempY<0){
                    break;
                }
                canvas.drawText(preContent, width / 2, tempY, whitepaint);
            }

            // 绘制后面部分
            tempY = height / 2;//Y轴的中间坐标
            for (int i = index + 1; i < lyrics.size(); i++) {
                //每一句歌词
                String nextContent = lyrics.get(i).getContent();
                tempY = tempY + textHeight;
                if (tempY > height) {
                    break;
                }
                canvas.drawText(nextContent, width / 2, tempY, whitepaint);
            }
        }else {
            //没有歌词
            canvas.drawText("亲，没有歌词", width / 2, height / 2, paint);
        }
    }
    /**
     * 根据当前播放的位置，找出该高亮显示哪句歌词
     *
     * @param currentPosition
     */
    public void setShowNextLyric(int currentPosition){
        this.currentPositon = currentPosition;
        if(lyrics==null||lyrics.size()==0){
            return;
        }
        for (int i = 1; i < lyrics.size(); i++) {
            if(currentPosition < lyrics.get(i).getTimePoint()){
                int tempIndex = i - 1;
                if(currentPosition >= lyrics.get(tempIndex).getTimePoint()){
                    //当前正在播放的哪句歌词
                    index = tempIndex;
                    sleepTime = lyrics.get(index).getSleepTime();
                    timePoint=lyrics.get(index).getTimePoint();
                }
            }
        }
        //重新绘制
        invalidate();//在主线程中
    }
}

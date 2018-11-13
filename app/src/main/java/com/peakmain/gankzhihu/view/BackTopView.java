package com.peakmain.gankzhihu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peakmain.gankzhihu.R;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/13 0013 下午 3:15
 * mail : 2726449200@qq.com
 * describe ：
 */
public class BackTopView extends RelativeLayout {
    //文字大小
    private int mTextSize;
    //默认文字大小
    private static final int DEFAULT_TEXT_SIZE = 30;
    //线的颜色
    private int mLineColor;
    //顶部图片资源id
    private int mBackTopImage;
    //顶部图片
    private ImageView mIvTop;
    //LinearLayout将变化的文字的进度+线+总量
    private LinearLayout mLlProgress;
    //文字进度
    private TextView mTvProgress;
    //上下文
    private Context mContext;
    //总量
    private TextView mTvMax;
    //滚动的位置
    private int mScrollY = 0;
    public BackTopView(Context context) {
        this(context, null);
    }

    public BackTopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //解析自定义属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BackTopView);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.BackTopView_topTextSize,
                DEFAULT_TEXT_SIZE);
        mLineColor = typedArray.getColor(R.styleable.BackTopView_lineColor,
                getResources().getColor(R.color.colorAccent));
        mBackTopImage = typedArray.getResourceId(R.styleable.BackTopView_backTopImage, R.drawable.bt_back_top);
        //一定要回收
        typedArray.recycle();
        this.mContext = context;
        initView();
    }

    private void initView() {
        //1.要添加到的LinearLayout设置参数
        mLlProgress = new LinearLayout(mContext);
        mLlProgress.setOrientation(LinearLayout.VERTICAL);
        mLlProgress.setBackgroundResource(R.drawable.bg_totop_progress);
        LayoutParams llparams = new LayoutParams(dp2px(50), dp2px(50));
        mLlProgress.setLayoutParams(llparams);
        mLlProgress.setGravity(Gravity.CENTER);
        //2.绘制图片
        mIvTop = new ImageView(mContext);
        mIvTop.setImageResource(mBackTopImage);
        //设置图片参数
        LayoutParams params = new LayoutParams(dp2px(50), dp2px(50));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mIvTop.setLayoutParams(params);
        //3.进度
        mTvProgress = new TextView(mContext);
        mTvProgress.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvProgress.setGravity(Gravity.CENTER);
        LayoutParams tvparams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTvProgress.setLayoutParams(tvparams);
        //4.绘制横线
        View line = new View(mContext);
        line.setBackgroundColor(mLineColor);
        LayoutParams lineParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp2px(1));
        line.setLayoutParams(lineParams);
        line.setPadding(dp2px(5), 0, dp2px(5), 0);
        //5.绘制总的数量
        mTvMax = new TextView(mContext);
        mTvMax.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvMax.setGravity(Gravity.CENTER);
        mTvMax.setLayoutParams(tvparams);
        //6.添加到LinearLayout中
        mLlProgress.addView(mTvProgress);
        mLlProgress.addView(line);
        mLlProgress.addView(mTvMax);
        //7.将回到顶部的文字和组合起来的文字添加到一起
        addView(mIvTop);
        addView(mLlProgress);
    }
    //将按钮和RecyclerView进行关联起来
    public void setRecyclerView(final RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //获取手机屏幕的高
                if (mScrollY >= getResources().getDisplayMetrics().heightPixels) {
                    setVisibility(VISIBLE);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止滑动
                        mIvTop.setVisibility(VISIBLE);
                        mLlProgress.setVisibility(GONE);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        //正在滑动滑动
                        mIvTop.setVisibility(GONE);
                        mLlProgress.setVisibility(VISIBLE);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mScrollY += dy;
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //整个item的数量，必须是LinearLayout
                int itemCount = manager.getItemCount();
                //最后一个可见数量的位置
                int lastItemPosition = manager.findLastVisibleItemPosition();
                mTvProgress.setText(String.valueOf(lastItemPosition));
                mTvMax.setText(String.valueOf(itemCount));
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //设置点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    // recyclerView.scrollToPosition(0);//快速滚动到顶部
                    recyclerView.smoothScrollToPosition(0);//缓慢滚动到顶部

                }
            }
        });
    }

    //sp转换成dp
    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mContext.getResources().getDisplayMetrics());
    }
}

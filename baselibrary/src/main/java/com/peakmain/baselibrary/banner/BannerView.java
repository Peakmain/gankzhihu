package com.peakmain.baselibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peakmain.baselibrary.R;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/7/16 0016 上午 9:53
 * mail : 2726449200@qq.com
 * describe ：
 */

public class BannerView extends RelativeLayout {
    private static final String TAG = "BannerView";
    // 4.自定义BannerView - 轮播的ViewPager
    private BannerViewPager mBannerVp;
    // 4.自定义BannerView - 轮播的描述
    private TextView mBannerDescTv;
    // 4.自定义BannerView - 点的容器
    private LinearLayout mDotContainerView;
    // 4.自定义BannerView - 自定义的BannerAdapter
    private BannerAdapter mAdapter;

    private Context mContext;

    // 5.初始化点的指示器 - 点选中的Drawable
    private Drawable mIndicatorFocusDrawable;
    // 5.初始化点的指示器 - 点默认的Drawable
    private Drawable mIndicatorNormalDrawable;

    // 6.Bug修复 - 当前位置
    private int mCurrentPosition = 0;

    // 8.自定义属性 - 点的显示位置  默认右边
    private int mDotGravity = 1;
    // 8.自定义属性 - 点的大小  默认8dp
    private int mDotSize = 8;
    // 8.自定义属性 - 点的间距  默认8dp
    private int mDotDistance = 8;
    // 8.自定义属性 - 底部容器
    private View mBannerBv;
    // 8.自定义属性 - 底部容器颜色默认透明
    private int mBottomColor = Color.TRANSPARENT;
    // 8.自定义属性 - 宽高比例
    private float mWidthProportion,mHeightProportion;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        // 把布局加载到这个View里面
        inflate(context, R.layout.ui_banner_layout, this);

        initAttribute(attrs);

        initView();
    }

    /**
     * 8.初始化自定义属性
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        // 获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        // 获取点的颜色（默认、选中）
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if(mIndicatorFocusDrawable == null){
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if(mIndicatorNormalDrawable == null){
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        // 获取点的大小和距离
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize,dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance,dip2px(mDotDistance));

        // 获取底部的颜色
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor,mBottomColor);

        // 获取宽高比例
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion,mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion,mHeightProportion);
        array.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        mBannerBv = findViewById(R.id.banner_bottom_view);
        mBannerBv.setBackgroundColor(mBottomColor);
    }

    /**
     * 4.设置适配器
     */
    public void setAdapter(BannerAdapter adapter){
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        // 5.初始化点的指示器
        initDotIndicator();

        // 6.Bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // 监听当前选中的位置
                pageSelect(position);
            }
        });

        // 6.初始化的时候获取第一条的描述
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);

        // 8.自适应高度 动态指定高度
        if(mHeightProportion == 0 || mWidthProportion == 0){
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                // 动态指定宽高  计算高度
                int width = getMeasuredWidth();
                // 计算高度
                int height = (int) (width * mHeightProportion / mWidthProportion);
                // 指定宽高
                getLayoutParams().height = height;
                mBannerVp.getLayoutParams().height = height;
            }
        });
    }

    /**
     * 6.页面切换的回调
     * @param position
     */
    private void pageSelect(int position) {
        // 6.1 把之前亮着的点 设置为默认
        DotIndicatorView oldIndicatorView = (DotIndicatorView)
                mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalDrawable);


        // 6.2 把当前位置的点 点亮  position 0 --> 2的31次方
        mCurrentPosition = position%mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView)
                mDotContainerView.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

        // 6.3设置广告描述
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerDesc);
    }

    /**
     * 5.初始化点的指示器
     */
    private void initDotIndicator() {
        // 获取广告的数量
        int count = mAdapter.getCount();

        // 让点的位置在右边
        mDotContainerView.setGravity(getDotGravity());

        for (int i = 0;i<count;i++){
            // 不断的往点的指示器添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            // 设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize,mDotSize);
            // 设置左右间距
            params.leftMargin = mDotDistance;
            indicatorView.setLayoutParams(params);

            if(i == 0) {
                // 选中位置
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            }else{
                // 未选中的
                indicatorView.setDrawable(mIndicatorNormalDrawable);
            }
            mDotContainerView.addView(indicatorView);
        }
    }

    /**
     * 5.把dip转成px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip,getResources().getDisplayMetrics());
    }

    /**
     * 4.开始滚动
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    /**
     * 8.获取点的位置
     * @return
     */
    public int getDotGravity() {
        switch (mDotGravity){
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            case 1:
                return Gravity.RIGHT;
        }
        return Gravity.LEFT;
    }

    /**
     * 10.设置点击回调监听
     */
    public void setOnBannerItemClickListener(BannerViewPager.BannerItemClickListener listener){
        mBannerVp.setOnBannerItemClickListener(listener);
    }
}

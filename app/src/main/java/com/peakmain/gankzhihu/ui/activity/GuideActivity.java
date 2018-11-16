package com.peakmain.gankzhihu.ui.activity;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.GuideAdapter;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/14 0014 上午 9:28
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path = "/activity/GuideActivity")
public class GuideActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_point_group)
    LinearLayout mLlPointGroup;
    @BindView(R.id.iv_red_point)
    ImageView mIvRedPoint;
    @BindView(R.id.btn_start_main)
    Button mBtnStartMain;
    private ArrayList<ImageView> imageViews;
    /**
     * 两点的间距
     */
    private int leftmax;
    private int pointwidth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        pointwidth = ScreenUtil.instance(this).dip2px(10);
        imageViews = new ArrayList<>();
        initData();

        //设置viewpager的适配器
        mViewPager.setAdapter(new GuideAdapter(imageViews));
        mIvRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(this);
        //得到屏幕滑动的百分比
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //准备数据
        int[] data = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3,
                R.drawable.guide_4
        };
        for (int i = 0; i < data.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(data[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //添加到集合中
            imageViews.add(imageView);
            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            //每个点设置参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointwidth, pointwidth);
            if (i != 0) {
                //除了0之外所有的点向左移动10个像素点
                params.leftMargin = pointwidth;
            }
            point.setLayoutParams(params);
            //添加到线性布局
            mLlPointGroup.addView(point);
        }
    }

    @OnClick(R.id.btn_start_main)
    public void startMain() {
        ARouter.getInstance().build("/activity/MainActivity")
                .navigation();
        SPUtils.getInstance().put("isFirst", true);
        finish();
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mIvRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            leftmax = mLlPointGroup.getChildAt(1).getLeft() - mLlPointGroup.getChildAt(0).getLeft();
        }
    }

    /**
     * 当页面回调了会回调这个方法
     * @param position 当前滑动页面的位置
     * @param positionOffset 页面滑动的百分比
     * @param positionOffsetPixels 滑动的像数
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //两点滑动距离对应的坐标=原来起始的坐标+两点间移动的距离
        int leftmargin = (int) (position * leftmax + (positionOffset * leftmax));
        //params.leftMargin = 两点间滑动距离对应的坐标
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvRedPoint.getLayoutParams();
        params.leftMargin = leftmargin;
        mIvRedPoint.setLayoutParams(params);
    }

    /**
     * 当页面被选中的时候，回调这个方法
     * @param position 被选中页面的对应的位置
     */
    @Override
    public void onPageSelected(int position) {
        if (position == imageViews.size() - 1) {
            //最后一个页面
            mBtnStartMain.setVisibility(View.VISIBLE);
        } else {
            //其他页面
            mBtnStartMain.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

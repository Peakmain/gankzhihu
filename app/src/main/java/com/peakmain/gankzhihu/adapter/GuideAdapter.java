package com.peakmain.gankzhihu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/14 0014 上午 11:23
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GuideAdapter extends PagerAdapter {
    private ArrayList<ImageView> mImageViews;
    @Inject
    public GuideAdapter(ArrayList<ImageView> imageViews){
        this.mImageViews=imageViews;
    }
    /**
     * 返回数据的总个数
     */
    @Override
    public int getCount() {
        return mImageViews.size();
    }

    /**
     * 作用，getView
     * @param container ViewPager
     * @param position  要创业页面的位置
     * @return 返回和创建当前页面右关系的值
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViews.get(position);
        //添加到容器中
        container.addView(imageView);
        return imageView;
    }
    /**
     * @param view   当前创建的视图
     * @param object 上面instantiateItem返回的结果值
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    /**
     * 销毁页面
     * @param container ViewPager
     * @param position  要销毁页面的位置
     * @param object    要销毁的页面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

package com.peakmain.gankzhihu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.peakmain.gankzhihu.base.BaseFragment;

import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:16
 * mail : 2726449200@qq.com
 * describe ：
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private String tag;

    private List<BaseFragment> fragmentList;
    public HomeViewPagerAdapter(FragmentManager supportFragmentManager, List<BaseFragment> fragmentList, String tag) {
        super(supportFragmentManager);
        this.fragmentList = fragmentList;
        this.tag = tag;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        }
        return 0;
    }
     //标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(tag.equals("home_view_pager")){
            switch (position) {
                case 0:
                    return "知乎";
                case 1:
                    return "干货";
                case 2:
                    return "满足你的好奇心";
            }
        }
        return super.getPageTitle(position);
    }
}

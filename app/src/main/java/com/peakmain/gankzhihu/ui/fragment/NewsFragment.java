package com.peakmain.gankzhihu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.HomeViewPagerAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/16 0016 下午 3:00
 * mail : 2726449200@qq.com
 * describe ：
 */
public class NewsFragment extends Fragment {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager mViewPager;
    private static NewsFragment instance = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this,view);
        initTabView();
        return view;
    }

    private void initTabView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new GankFragment());
        fragmentList.add(new DailyFragment());
        mViewPager.setOffscreenPageLimit(3);//防止重复创建和销毁，造成内存溢出
        //设置适配器
        mViewPager.setAdapter(new HomeViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, "home_view_pager"));
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
    }

    public static NewsFragment getInstance() {
        if (instance == null) {
            instance = new NewsFragment();
        }
        return instance;
    }

}

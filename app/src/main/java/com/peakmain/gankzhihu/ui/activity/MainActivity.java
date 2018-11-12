package com.peakmain.gankzhihu.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.HomeViewPagerAdapter;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.ui.fragment.DailyFragment;
import com.peakmain.gankzhihu.ui.fragment.GankFragment;
import com.peakmain.gankzhihu.ui.fragment.ZhihuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/activity/MainActivity")
public class MainActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initTabView();
    }

    private void initTabView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new GankFragment());
        fragmentList.add(new DailyFragment());
        mViewPager.setOffscreenPageLimit(3);//防止重复创建和销毁，造成内存溢出
        //设置适配器
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(), fragmentList, "home_view_pager"));
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.today_github) {
            String github_trending = "https://github.com/trending";
            ARouter.getInstance().build("/activity/GankWebActivity")
                    .withString(GankWebActivity.GANK_URL, github_trending);
            return true;
        } else if (item.getItemId() == R.id.about_me) {
            ARouter.getInstance().build("/activity/AboutActivity")
                    .navigation();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

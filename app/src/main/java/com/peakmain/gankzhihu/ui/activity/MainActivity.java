package com.peakmain.gankzhihu.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
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
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

@Route(path = "/activity/MainActivity")
public class MainActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private View mHeaderView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        initTabView();
        //获取头部
        mHeaderView = mNavigationView.getHeaderView(0);
        //设置点击事件
        mNavigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        //使icon为原来自己的颜色
        mNavigationView.setItemIconTintList(null);
        //将Toolbar与DrawableLayout关联起来
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //设置头部信息
        ImageView blurImageView = mHeaderView.findViewById(R.id.iv_blur);
        ImageView avatarImageView = mHeaderView.findViewById(R.id.iv_avatar);
        //登录
        avatarImageView.setOnClickListener(view -> ARouter.getInstance().build("/activity/LoginActivity")
                .navigation());
        TextView userName = mHeaderView.findViewById(R.id.tv_user_name);
        //毛玻璃效果
        Glide.with(this).load(R.mipmap.avatar)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(blurImageView);
        Glide.with(this).load(R.mipmap.avatar)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView);
        //获取是否登录
        String account = SPUtils.getInstance().getString("account");
        if (!TextUtils.isEmpty(account)) {
            userName.setText(account);
        }else{
            userName.setText("尚未登录");
        }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.today_github:
                String github_trending = "https://github.com/trending";
                ARouter.getInstance().build("/activity/GankWebActivity")
                        .withString(GankWebActivity.GANK_URL, github_trending)
                        .navigation();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.about_me:
                ARouter.getInstance().build("/activity/AboutActivity")
                        .navigation();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, "今日新闻:" + "https://github.com/RangersEZ/gankzhihu");
                startActivity(Intent.createChooser(shareIntent, "分享"));
                mDrawerLayout.closeDrawers();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}

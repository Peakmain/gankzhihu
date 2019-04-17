package com.peakmain.gankzhihu.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.rx.RegisterBus;
import com.peakmain.gankzhihu.rx.RxBus;
import com.peakmain.gankzhihu.ui.fragment.JokeFragment;
import com.peakmain.gankzhihu.ui.fragment.MusicFragment;
import com.peakmain.gankzhihu.ui.fragment.NewsFragment;
import com.peakmain.gankzhihu.ui.fragment.VideoFragment;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import org.json.JSONObject;

import java.util.Iterator;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = "/activity/MainActivity")
/*@Xml(layouts = "activity_main")*/
public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    private View mHeaderView;
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_JOKE = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MUSIC = 3;
    private NewsFragment mNewsFragment;
    private JokeFragment mJokeFragment;
    private VideoFragment mVideoFragment;
    private MusicFragment mMusicFragment;
    private int position;//当前选中的位置
    private TextView mUserName;

    private int mFrameCount = 0;
    private static final long MONITOR_INTERVAL = 160L; //单次计算FPS使用160毫秒
    private static final long MONITOR_INTERVAL_NANOS = MONITOR_INTERVAL * 1000L * 1000L;
    private static final long MAX_INTERVAL = 1000L; //设置计算fps的单位时间间隔1000ms,即fps/s;
    private long mStartFrameTime=0;

    private void getFPS() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mStartFrameTime == 0) {
                    mStartFrameTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - mStartFrameTime;
                if (interval > MONITOR_INTERVAL_NANOS) {
                    double fps = (((double) (mFrameCount * 1000L * 1000L)) / interval) * MAX_INTERVAL;
                    LogUtils.i(fps);
                    mFrameCount = 0;
                    mStartFrameTime = 0;
                } else {
                    ++mFrameCount;
                }

                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

  /*  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
      *//*  LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                long time = System.currentTimeMillis();
                View view = getDelegate().createView(parent, name, context, attrs);
                LogUtils.e(name + " cost " + (System.currentTimeMillis() - time));
                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });*//*

       new AsyncLayoutInflater(this).inflate(R.layout.activity_main, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
           @Override
           public void onInflateFinished(@NonNull View view, int i, @Nullable ViewGroup viewGroup) {
               setContentView(view);
               //初始化一些参数比如findViewById等
           }
       });
        super.onCreate(savedInstanceState);
    }
*/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

  /*  @Override
    public View getContentView() {
        X2C.setContentView(MainActivity.this,R.layout.activity_main);
        return null;
    }*/

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        getFPS();
        getWindow().setBackgroundDrawable(null);
        showFragment(FRAGMENT_NEWS);
        RxBus.getInstance().register(this);
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
        mUserName = mHeaderView.findViewById(R.id.tv_user_name);
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
            mUserName.setText(account);
        } else {
            mUserName.setText("尚未登录");
        }
        mBottomNavigationView.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);
    }

    @RegisterBus
    public void setUserInfo(String userName) {
        if (mUserName != null) {
            mUserName.setText(userName);
        }
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hintFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS://新闻
                mToolbar.setTitle(R.string.title_news);
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (mNewsFragment == null) {
                    mNewsFragment = mNewsFragment.getInstance();
                    ft.add(R.id.container, mNewsFragment, NewsFragment.class.getName());
                } else {
                    ft.show(mNewsFragment);
                }
                break;
            case FRAGMENT_JOKE:
                mToolbar.setTitle(R.string.title_jokes);
                if (mJokeFragment == null) {
                    mJokeFragment = new JokeFragment();
                    ft.add(R.id.container, mJokeFragment, JokeFragment.class.getName());
                } else {
                    ft.show(mJokeFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                mToolbar.setTitle(R.string.title_video);
                if (mVideoFragment == null) {
                    mVideoFragment = new VideoFragment();
                    ft.add(R.id.container, mVideoFragment, VideoFragment.class.getName());
                } else {
                    ft.show(mVideoFragment);
                }
                break;
            case FRAGMENT_MUSIC:
                mToolbar.setTitle(R.string.title_music);
                if (mMusicFragment == null) {
                    mMusicFragment = new MusicFragment();
                    ft.add(R.id.container, mMusicFragment, MusicFragment.class.getName());
                } else {
                    ft.show(mMusicFragment);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param ft
     */
    private void hintFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mNewsFragment != null) {
            ft.hide(mNewsFragment);
        }
        if (mJokeFragment != null) {
            ft.hide(mJokeFragment);
        }
        if (mVideoFragment != null) {
            ft.hide(mVideoFragment);
        }
        if (mMusicFragment != null)
            ft.hide(mMusicFragment);
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
            case R.id.point_play://点播
                ARouter.getInstance().build("/activity/PointPlayActivity")
                        .navigation();
                break;
            case R.id.action_news:
                showFragment(FRAGMENT_NEWS);
                break;
            case R.id.action_joke:
                showFragment(FRAGMENT_JOKE);
                break;
            case R.id.action_video:
                showFragment(FRAGMENT_VIDEO);
                break;
            case R.id.action_music:
                showFragment(FRAGMENT_MUSIC);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
    }
}

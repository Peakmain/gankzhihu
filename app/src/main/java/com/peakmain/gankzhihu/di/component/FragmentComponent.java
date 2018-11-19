package com.peakmain.gankzhihu.di.component;

import android.app.Activity;
import android.content.Context;

import com.peakmain.gankzhihu.di.module.FragmentModule;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.di.scope.PerFragment;
import com.peakmain.gankzhihu.ui.fragment.DailyFragment;
import com.peakmain.gankzhihu.ui.fragment.GankFragment;
import com.peakmain.gankzhihu.ui.fragment.JokeFragment;
import com.peakmain.gankzhihu.ui.fragment.VideoFragment;
import com.peakmain.gankzhihu.ui.fragment.ZhihuFragment;

import dagger.Component;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/20 0020 下午 2:46
 * mail : 2726449200@qq.com
 * describe ：
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getAcitivtyContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getAcitivty();

    void inject(ZhihuFragment zhihuFragment);

    void inject(GankFragment gankFragment);

    void inject(DailyFragment dailyFragment);

    void inject(JokeFragment jokeFragment);

    void inject(VideoFragment videoFragment);
}

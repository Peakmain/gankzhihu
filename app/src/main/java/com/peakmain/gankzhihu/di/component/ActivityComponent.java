package com.peakmain.gankzhihu.di.component;

import android.app.Activity;
import android.content.Context;

import com.peakmain.gankzhihu.di.module.ActivityModule;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.di.scope.PerActivity;
import com.peakmain.gankzhihu.ui.activity.DailyFeedActivity;
import com.peakmain.gankzhihu.ui.activity.GankWebActivity;
import com.peakmain.gankzhihu.ui.activity.ZhihuWebActivity;

import dagger.Component;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/19 0019 下午 7:39
 * mail : 2726449200@qq.com
 * describe ：
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(ZhihuWebActivity zhihuWebActivity);

    void inject(GankWebActivity gankWebActivity);

    void inject(DailyFeedActivity dailyFeedActivity);
}

package com.peakmain.gankzhihu.di.module;

import android.app.Activity;
import android.content.Context;

import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/19 0019 下午 7:40
 * mail : 2726449200@qq.com
 * describe ：
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }
    @PerActivity
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext(){
        return mActivity;
    }
    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}

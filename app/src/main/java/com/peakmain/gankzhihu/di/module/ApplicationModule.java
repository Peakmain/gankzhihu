package com.peakmain.gankzhihu.di.module;

import android.content.Context;

import com.peakmain.gankzhihu.App;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/19 0019 下午 6:56
 * mail : 2726449200@qq.com
 * describe ：
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }
    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext(){
        return mApplication.getApplicationContext();
    }
}

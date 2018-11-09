package com.peakmain.gankzhihu.di.component;

import android.content.Context;

import com.peakmain.gankzhihu.di.module.ApplicationModule;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.di.scope.PerApp;

import dagger.Component;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/19 0019 下午 6:51
 * mail : 2726449200@qq.com
 * describe ：
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}
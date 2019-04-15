package com.peakmain.gankzhihu;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.peakmain.gankzhihu.di.component.ApplicationComponent;
import com.peakmain.gankzhihu.di.component.DaggerApplicationComponent;
import com.peakmain.gankzhihu.di.module.ApplicationModule;
import com.peakmain.gankzhihu.launchstarter.TaskDispatcher;
import com.peakmain.gankzhihu.launchstarter.utils.LaunchTimer;
import com.peakmain.gankzhihu.tasks.ARouterTasks;
import com.peakmain.gankzhihu.tasks.UtilsTasks;

/**
 * author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 3:58
 * mail : 2726449200@qq.com
 * describe ：
 */
public class App extends Application {
    public static Context mContext;
    public static Handler handler;
    public static int mainThreadId;
    private ApplicationComponent mApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initApplicationComponent();
        LaunchTimer.startRecord();
        TaskDispatcher.init(App.this);

        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new ARouterTasks())
                .addTask(new UtilsTasks())
                .start();
        // dispatcher.await();
        LaunchTimer.endRecord();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchTimer.startRecord();
        MultiDex.install(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mContext.getApplicationContext();
    }

    private void initApplicationComponent() {
        mApplicationComponent =
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(this)).build();
    }
}

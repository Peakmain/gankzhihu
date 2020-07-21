package com.peakmain.gankzhihu;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.peakmain.baselibrary.launchstarter.TaskDispatcher;
import com.peakmain.baselibrary.launchstarter.utils.LaunchTimer;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;
import com.peakmain.gankzhihu.di.component.ApplicationComponent;
import com.peakmain.gankzhihu.di.component.DaggerApplicationComponent;
import com.peakmain.gankzhihu.di.module.ApplicationModule;
import com.peakmain.gankzhihu.handler.HandlerHelper;
import com.peakmain.gankzhihu.tasks.ARouterTasks;
import com.peakmain.gankzhihu.tasks.StethoTask;
import com.peakmain.gankzhihu.tasks.UtilsTasks;
import com.tencent.mmkv.MMKV;

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
        HandlerHelper.init();
        MMKV.initialize(this);
        MMKV.defaultMMKV().encode("times",100);
        MMKV.defaultMMKV().decodeInt("times");
        initApplicationComponent();
        LaunchTimer.startRecord();
        TaskDispatcher.init(App.this);

        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new ARouterTasks())
                .addTask(new UtilsTasks())
                .addTask(new StethoTask())
                .start();
        // dispatcher.await();
        LaunchTimer.endRecord();


        initStrictMode();
       /* BlockCanary.install(this, new AppBlockCanaryContext()).start();*/
        //new ANRWatchDog().start();
    }

    private boolean DEV_MODE = true;

    private void initStrictMode() {
        if (!DEV_MODE) {
            //线程策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()//API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()//在Logcat 中打印违规异常信息
                    .build());
            //虚拟机策略
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    //模拟限制数量1
                    .setClassInstanceLimit(NewsTimeLine.class, 1)
                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog()
                    .build());

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchTimer.startRecord();
        MultiDex.install(this);
/*        DexposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Thread thread = (Thread) param.thisObject;
                LogUtils.i(thread.getName() + " stack " + Log.getStackTraceString(new Throwable()));
            }
        });*/
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

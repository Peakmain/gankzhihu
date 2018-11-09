package com.peakmain.gankzhihu;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.peakmain.gankzhihu.di.component.ApplicationComponent;
import com.peakmain.gankzhihu.di.component.DaggerApplicationComponent;
import com.peakmain.gankzhihu.di.module.ApplicationModule;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 3:58
 * mail : 2726449200@qq.com
 * describe ：
 */
public class App extends Application {
    public static Context mContext;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initApplicationComponent();
        intARouter();
        //配置ToastUtils的相关的属性
        Utils.init(this);
        ToastUtils.setGravity(Gravity.TOP,0, (int) (80 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5));
        ToastUtils.setBgColor(getResources().getColor(R.color.white));
        ToastUtils.setMsgColor(getResources().getColor(R.color.colorAccent));
    }

    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
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

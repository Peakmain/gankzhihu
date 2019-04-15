package com.peakmain.gankzhihu.tasks;

import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.App;
import com.peakmain.gankzhihu.launchstarter.task.Task;

/**
 * author ：Peakmain
 * createTime：2019/4/15
 * mail:2726449200@qq.com
 * describe：
 */
public class ARouterTasks extends Task {
    @Override
    public void run() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init((App)mContext); // 尽可能早，推荐在Application中初始化
    }
}

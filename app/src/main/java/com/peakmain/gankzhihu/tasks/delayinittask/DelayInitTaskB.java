package com.peakmain.gankzhihu.tasks.delayinittask;


import com.blankj.utilcode.util.LogUtils;
import com.peakmain.baselibrary.launchstarter.task.MainTask;
/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/15
 * mail:2726449200@qq.com
 * describe：
 */
public class DelayInitTaskB extends MainTask {

    @Override
    public void run() {
        // 模拟一些操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i("DelayInitTaskB finished");
    }
}

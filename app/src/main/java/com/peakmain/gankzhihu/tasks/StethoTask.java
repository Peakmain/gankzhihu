package com.peakmain.gankzhihu.tasks;

import com.facebook.stetho.Stetho;
import com.peakmain.baselibrary.launchstarter.task.Task;

/**
 * author ：Peakmain
 * createTime：2019/4/23
 * mail:2726449200@qq.com
 * describe：
 */
public class StethoTask extends Task{
    @Override
    public void run() {
        Stetho.initializeWithDefaults(mContext);
    }
}

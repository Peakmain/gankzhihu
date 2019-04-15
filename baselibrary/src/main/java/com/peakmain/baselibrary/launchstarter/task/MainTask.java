package com.peakmain.baselibrary.launchstarter.task;

/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/15
 * mail:2726449200@qq.com
 * describe：
 */
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}

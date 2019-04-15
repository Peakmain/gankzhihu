package com.peakmain.gankzhihu.launchstarter.utils;

import android.util.Log;

/**
 * author ：Peakmain
 * createTime：2019/4/11
 * mail:2726449200@qq.com
 * describe：
 */
public class LaunchTimer {
    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord() {
        long cost = System.currentTimeMillis()-sTime;
        Log.e("TAG","cost time"+cost);
    }
}

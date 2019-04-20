package com.peakmain.gankzhihu.aop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import me.ele.lancet.base.Origin;
import me.ele.lancet.base.Scope;
import me.ele.lancet.base.annotations.Insert;
import me.ele.lancet.base.annotations.Proxy;
import me.ele.lancet.base.annotations.TargetClass;

/**
 * author ：Peakmain
 * createTime：2019/4/18
 * mail:2726449200@qq.com
 * describe：
 */
public class ActivityHooker {
    public static ActivityRecord sActivityRecord;
    static {
        sActivityRecord=new ActivityRecord();
    }
    @Insert(value = "onCreate",mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity",scope = Scope.ALL)
    protected void onCreate(Bundle savedInstanceState) {
        sActivityRecord.mOnCreateTime = System.currentTimeMillis();
        Origin.callVoid();
    }


    @Insert(value = "onWindowFocusChanged",mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity",scope = Scope.ALL)
    public void onWindowFocusChanged(boolean hasFocus) {
        sActivityRecord.mOnWindowsFocusChangedTime = System.currentTimeMillis();
        Log.i("ActivityHooker","onWindowFocusChanged cost "+(sActivityRecord.mOnWindowsFocusChangedTime - sActivityRecord.mOnCreateTime));
        Origin.callVoid();
    }

    /**
     * hook系统方法
     */
    @Proxy("i")
    @TargetClass("android.util.Log")
    public static int i(String tag, String msg) {
        msg = msg + "ActivityHooker";
        return (int) Origin.call();
    }

}

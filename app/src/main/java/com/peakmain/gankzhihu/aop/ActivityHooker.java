package com.peakmain.gankzhihu.aop;

/**
 * author ：Peakmain
 * createTime：2019/4/18
 * mail:2726449200@qq.com
 * describe：
 */
public  class ActivityHooker {
/*    public static ActivityRecord sActivityRecord;

    static {
        sActivityRecord = new ActivityRecord();
    }

    private static String sTrace;

    @Insert(value = "onCreate", mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity", scope = Scope.ALL)
    protected void onCreate(Bundle savedInstanceState) {
        sActivityRecord.mOnCreateTime = System.currentTimeMillis();
        Origin.callVoid();
    }


    @Insert(value = "onWindowFocusChanged", mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity", scope = Scope.ALL)
    public void onWindowFocusChanged(boolean hasFocus) {
        sActivityRecord.mOnWindowsFocusChangedTime = System.currentTimeMillis();
        Log.i("ActivityHooker", "onWindowFocusChanged cost " + (sActivityRecord.mOnWindowsFocusChangedTime - sActivityRecord.mOnCreateTime));
        Origin.callVoid();
    }

    *//**
     * hook系统方法
     *//*
    @Proxy("i")
    @TargetClass("android.util.Log")
    public static int i(String tag, String msg) {
        msg = msg + "ActivityHooker";
        return (int) Origin.call();
    }

    private static long sStartTime = 0;

    @Insert(value = "acquire")
    @TargetClass(value = "com.peakmain.gankzhihu.wakelock.WakeLockUtils", scope = Scope.ALL)
    public static void acquire(Context context) {
        sTrace = Log.getStackTraceString(new Throwable());
        sStartTime = System.currentTimeMillis();
        Origin.callVoid();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WakeLockUtils.release();
            }
        }, 1000);

    }

    @Insert(value = "release")
    @TargetClass(value = "com.peakmain.gankzhihu.wakelock.WakeLockUtils", scope = Scope.ALL)
    public static void release() {
        LogUtils.e("PowerManager " + (System.currentTimeMillis() - sStartTime) + sTrace);
        Origin.callVoid();
    }
    public  long runTime=0;
   //线程使用时长
    @Insert(value = "run")
    @TargetClass(value = "java.lang.Runnable", scope = Scope.ALL)
    public  void run () {
        runTime=System.currentTimeMillis();
        Origin.callVoid();
        LogUtils.e("runTime " + (System.currentTimeMillis() - runTime));
    }*/
}

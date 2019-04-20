package com.peakmain.gankzhihu.handler;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.peakmain.gankzhihu.App;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author ：Peakmain
 * createTime：2019/4/19
 * mail:2726449200@qq.com
 * describe：
 */
public class HandlerHelper {
    private static long mStartTime = System.currentTimeMillis();
    private static ConcurrentHashMap<Message, String> sMsgDetail = new ConcurrentHashMap<>();
    public static void init(Context context) {

        List<Handler> handlers = getActivitiesByApplication((App) context);
        Log.e("Looper",handlers.size()+"");
        for (Handler handler : handlers) {
            PeakmainHandler superHandler=new PeakmainHandler();
            handler=superHandler;
            Log.e("Looper",handler.getLooper()+"");
        }

 /*       try {
            //获取系统的Handler的sendMessageAtTime
            Class<?> handlerClass = Class.forName("android.os.Handler");
            Method sendMessageAtTime = handlerClass.getDeclaredMethod("sendMessageAtTime", new Class[]{Message.class, long.class});
            Object o = Proxy.newProxyInstance(handlerClass.getClassLoader(), handlerClass.getInterfaces(), new HandlerProxy(handlerClass,sMsgDetail));
            Message message=new Message();

            sendMessageAtTime.invoke(handlerClass,o);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public static List<Handler> getActivitiesByApplication(Application application) {
        List<Handler> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mH");
            mActivitiesField.setAccessible(true);
            Object mH = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            list.add((Handler) mH);

        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    private  static class HandlerProxy implements InvocationHandler {


        private  ConcurrentHashMap<Message, String> sMsgDetail;
        private  Class<?> mHandlerClass;

        public HandlerProxy(Class<?> handlerClass, ConcurrentHashMap<Message, String> msgDetail) {
            this.mHandlerClass=handlerClass;
            this.sMsgDetail=msgDetail;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            boolean send= (boolean) method.invoke(mHandlerClass,objects);
            Log.e("PeakmainHandler","自己的sendMessageAtTime被调用了");
            if (send) {
                sMsgDetail.put((Message) objects[0], Log.getStackTraceString(new Throwable()).replace("java.lang.Throwable", ""));
            }
            return send;
        }
    }
}

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
import java.lang.reflect.Proxy;
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

    public static void init() {

        try {
            //获取系统的Handler的sendMessageAtTime
            Class<?> handlerClass = Class.forName("android.os.Handler");
            Method sendMessageAtTime = handlerClass.getDeclaredMethod("sendMessageAtTime", new Class[]{Message.class, long.class});
            PeakmainHandler peakmainHandler=new PeakmainHandler();
            handlerClass=peakmainHandler.getClass();
            Object obj = Proxy.newProxyInstance(handlerClass.getClassLoader(), handlerClass.getInterfaces(), new HandlerProxy(handlerClass));
            sendMessageAtTime.invoke(handlerClass,obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     private  static class HandlerProxy implements InvocationHandler {


        private  Class<?> mHandlerClass;

        public HandlerProxy(Class<?> handlerClass) {
            this.mHandlerClass=handlerClass;

        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            return method.invoke(mHandlerClass,objects);
        }
    }
    public static List<Handler> getHandlerByApplication(Application application) {
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
}

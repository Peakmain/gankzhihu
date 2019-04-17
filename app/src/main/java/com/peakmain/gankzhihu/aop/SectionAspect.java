package com.peakmain.gankzhihu.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * author ：Peakmain
 * createTime：2019/4/12
 * mail:2726449200@qq.com
 * describe：
 */
@Aspect
public class SectionAspect {
    @Around("call (* com.peakmain.gankzhihu.App.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Log.e("SectionAspect", signature.getName() + "  cost Time:" + (System.currentTimeMillis() - time));
    }

    @Around("execution (* android.app.Activity.setContentView**(..))")
    public void getSetContentViewTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Log.e("SectionAspect", signature.getName() + "  cost Time:" + (System.currentTimeMillis() - time));
    }
}

package com.peakmain.gankzhihu.async;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author ：Peakmain
 * createTime：2019/4/22
 * mail:2726449200@qq.com
 * describe：
 */
public class ThreadPoolUtils {
    /**
     * 会提示：手动创建线程池，效果会更好哦
     */
    private static ExecutorService sService = Executors.newFixedThreadPool(5, new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            Thread thread = new Thread();
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            thread.setName("ThreadPoolUtils's ExecutorService");
            return thread;
        }
    });

    public static ExecutorService getService() {
        return sService;
    }
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return new Thread(r, "ThreadPoolUtils's ThreadFactory" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;


    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return THREAD_POOL_EXECUTOR;
    }

}

package com.peakmain.gankzhihu.net;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * author ：Peakmain
 * createTime：2019/4/24
 * mail:2726449200@qq.com
 * describe：
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //此处执行在主线程
        //模拟一些处理：批量网络请求，APM日志上报
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

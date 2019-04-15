package com.peakmain.gankzhihu.tasks;

import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.peakmain.gankzhihu.App;
import com.peakmain.gankzhihu.R;
import com.peakmain.baselibrary.launchstarter.task.Task;

/**
 * author ：Peakmain
 * createTime：2019/4/15
 * mail:2726449200@qq.com
 * describe：
 */
public class UtilsTasks extends Task {

    @Override
    public void run() {
        //配置ToastUtils的相关的属性
        Utils.init((App)mContext);
        ToastUtils.setGravity(Gravity.TOP, 0, (int) (80 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5));
        ToastUtils.setBgColor(mContext.getResources().getColor(R.color.white));
        ToastUtils.setMsgColor(mContext.getResources().getColor(R.color.colorAccent));
    }
}

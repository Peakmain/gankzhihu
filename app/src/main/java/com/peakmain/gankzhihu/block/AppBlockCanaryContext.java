package com.peakmain.gankzhihu.block;

import android.content.Context;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/18
 * mail:2726449200@qq.com
 * describe：BlockCanary配置的各种信息
 */
public class AppBlockCanaryContext extends BlockCanaryContext {


    @Override
    public String provideQualifier() {
        return "unknown";
    }

    @Override
    public String provideUid() {
        return "uid";
    }


    @Override
    public String provideNetworkType() {
        return "unknown";
    }

    @Override
    public int provideMonitorDuration() {
        return -1;
    }


    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    @Override
    public int provideDumpInterval() {
        return provideBlockThreshold();
    }


    @Override
    public String providePath() {
        return "/blockcanary/";
    }


    @Override
    public boolean displayNotification() {
        return true;
    }


    @Override
    public boolean zip(File[] src, File dest) {
        return false;
    }


    @Override
    public void upload(File zippedFile) {
        throw new UnsupportedOperationException();
    }



    @Override
    public List<String> concernPackages() {
        return null;
    }


    @Override
    public boolean filterNonConcernStack() {
        return false;
    }


    @Override
    public List<String> provideWhiteList() {
        LinkedList<String> whiteList = new LinkedList<>();
        whiteList.add("org.chromium");
        return whiteList;
    }

   @Override
    public boolean deleteFilesInWhiteList() {
        return true;
    }


    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {
        Log.i("lz","blockInfo "+blockInfo.toString());
    }
}

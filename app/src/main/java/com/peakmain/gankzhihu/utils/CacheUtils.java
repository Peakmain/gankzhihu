package com.peakmain.gankzhihu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.peakmain.gankzhihu.ui.service.MusicPlayerService;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/29 0029 下午 1:35
 * mail : 2726449200@qq.com
 * describe ：
 */
public class CacheUtils {
    /**
     * 保持播放模式
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putPlaymode(Context context, String key, int values) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("peakmain", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, values).commit();

    }

    /**
     * 得到播放模式
     */
    public static int getPlaymode(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("peakmain", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, MusicPlayerService.REPEAT_NORMAL);
    }
}

package com.peakmain.gankzhihu.memory;

import java.util.ArrayList;
/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/4/16
 * mail:2726449200@qq.com
 * describe：
 */
public class CallBackManager {

    public static ArrayList<CallBack> sCallBacks = new ArrayList<>();

    public static void addCallBack(CallBack callBack) {
        sCallBacks.add(callBack);
    }

    public static void removeCallBack(CallBack callBack) {
        sCallBacks.remove(callBack);
    }

}

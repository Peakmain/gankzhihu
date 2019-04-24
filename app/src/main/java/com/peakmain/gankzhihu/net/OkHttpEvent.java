package com.peakmain.gankzhihu.net;

/**
 * author ：Peakmain
 * createTime：2019/4/24
 * mail:2726449200@qq.com
 * describe：
 */
public class OkHttpEvent {
    public long dnsStartTime;
    public long dnsEndTime;
    public long responseBodySize;
    //判断有没有成功
    public boolean apiSuccess;
    public String errReson;
}

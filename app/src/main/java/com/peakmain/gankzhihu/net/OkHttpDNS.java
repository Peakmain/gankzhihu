package com.peakmain.gankzhihu.net;

import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

/**
 * author ：Peakmain
 * createTime：2019/4/24
 * mail:2726449200@qq.com
 * describe：
 */
public class OkHttpDNS implements Dns {
    private HttpDnsService mDnsService;
    private static OkHttpDNS instance = null;
    private OkHttpDNS(Context context) {
        //第二个参数阿里云的id,不可为空
        mDnsService = HttpDns.getService(context, "");
    }

    public static OkHttpDNS getInstance(Context context) {
        if (instance == null) {
            synchronized (OkHttpDNS.class) {
                if (instance == null) {
                    instance = new OkHttpDNS(context);
                }
            }
        }
        return instance;
    }
    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        String ip = mDnsService.getIpByHostAsync(hostname);
        if(ip!=null){
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            return inetAddresses;
        }
        //系统的DNS
        return Dns.SYSTEM.lookup(hostname);
    }
}

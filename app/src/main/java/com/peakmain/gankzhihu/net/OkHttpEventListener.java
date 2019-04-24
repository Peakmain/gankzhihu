package com.peakmain.gankzhihu.net;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import javax.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author ：Peakmain
 * createTime：2019/4/24
 * mail:2726449200@qq.com
 * describe：
 */
public class OkHttpEventListener extends EventListener {
    public static final Factory FACTORY=new Factory() {
        @Override
        public EventListener create(Call call) {
            return new OkHttpEventListener();
        }
    };
   private OkHttpEvent mOkHttpEvent;
    public OkHttpEventListener() {
        super();
        mOkHttpEvent=new OkHttpEvent();
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);

    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        mOkHttpEvent.dnsStartTime=System.currentTimeMillis();
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        mOkHttpEvent.dnsEndTime=System.currentTimeMillis();
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
    }

    @Override
    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        mOkHttpEvent.apiSuccess=true;
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        mOkHttpEvent.apiSuccess=false;
        mOkHttpEvent.errReson= Log.getStackTraceString(ioe);
        LogUtils.e("reason"+mOkHttpEvent.errReson);
    }
}

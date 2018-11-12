package com.peakmain.gankzhihu.ui.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.ui.contract.GankWebContract;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 10:44
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankWebPresenter extends BasePresenter<GankWebContract.View> implements GankWebContract.Presenter {
    private Activity activity;

    @Inject
    public GankWebPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setWebView(String url) {
        ProgressBar progressBar = mView.getProgressBar();
        WebView webView = mView.getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击放大缩小
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                activity.setTitle(title);
                super.onReceivedTitle(view, title);

            }
        });
        webView.loadUrl(url);
    }
}

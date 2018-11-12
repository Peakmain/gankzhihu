package com.peakmain.gankzhihu.ui.activity;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.ui.contract.GankWebContract;
import com.peakmain.gankzhihu.ui.presenter.GankWebPresenter;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 10:41
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path="/activity/GankWebActivity")
public class GankWebActivity extends BaseActivity<GankWebPresenter> implements GankWebContract.View {
    public static final String GANK_URL = "gank_url";
    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.url_web)
    WebView mWebView;
    private String gank_url;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_web;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        parseIntent();
        if (gank_url.contains("app3.qdaily.com/app3")) {
            gank_url = gank_url.replace("app3.qdaily.com/app3", "m.qdaily.com/mobile");
        }
        mPresenter.setWebView(gank_url);
    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        gank_url = getIntent().getStringExtra(GANK_URL);
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public WebView getWebView() {
        return mWebView;
    }


}

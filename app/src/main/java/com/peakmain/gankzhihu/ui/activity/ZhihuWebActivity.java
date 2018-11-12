package com.peakmain.gankzhihu.ui.activity;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.ui.contract.ZhiHuWebContract;
import com.peakmain.gankzhihu.ui.presenter.ZhihuWebViewPresenter;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 9:40
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path = "/activity/ZhihuWebActivity")
public class ZhihuWebActivity extends BaseActivity<ZhihuWebViewPresenter> implements ZhiHuWebContract.View {
    public static final String ID = "id";

    private String id;

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.iv_web_img)
    ImageView mIvWebImg;
    @BindView(R.id.tv_img_title)
    TextView mTvImgTitle;
    @BindView(R.id.tv_img_source)
    TextView mTvImgSource;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initInjector()
    {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        parseIntent();
        mPresenter.getDetailNews(id);
    }

    private void parseIntent() {
        id = getIntent().getStringExtra(ID);
    }

    @Override
    public WebView getWebView() {
        return mWebView;
    }

    @Override
    public ImageView getWebImg() {
        return mIvWebImg;
    }

    @Override
    public TextView getImgTitle() {
        return mTvImgTitle;
    }

    @Override
    public TextView getImgSource() {
        return mTvImgSource;
    }
}

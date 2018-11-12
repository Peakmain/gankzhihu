package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.zhihu.News;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.ZhihuApi;
import com.peakmain.gankzhihu.ui.contract.ZhiHuWebContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 9:44
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuWebViewPresenter extends BasePresenter<ZhiHuWebContract.View> implements ZhiHuWebContract.Presenter {
    private Context mContext;

    @Inject
    public ZhihuWebViewPresenter(@ContextLife Context context) {
        mContext = context;
    }

    @Override
    public void getDetailNews(String id) {
        RetrofitManager.createZhiHuIo(ZhihuApi.class).getDetailNews(id)
                .compose(mView.bindToLife())
                .compose(RxSchedulers.applySchedulers())
                .subscribe(news -> {
                    setWebView(news);
                }, this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    ImageView webImg;

    public void setWebView(News news) {
        WebView webView = mView.getWebView();
        webImg = mView.getWebImg();
        TextView imgTitle = mView.getImgTitle();
        TextView imgSource = mView.getImgSource();
        WebSettings settings = webView.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        //web推荐使用的窗口
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + news.getCss()[0] + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + news.getBody().replace(img, " ");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        Glide.with(mContext).load(news.getImage()).centerCrop().into(webImg);
        imgTitle.setText(news.getTitle());
        imgSource.setText(news.getImage_source());
    }
}

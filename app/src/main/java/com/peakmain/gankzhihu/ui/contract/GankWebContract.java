package com.peakmain.gankzhihu.ui.contract;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 10:41
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankWebContract  {
    public interface View extends BaseContract.BaseView{
        ProgressBar getProgressBar();
        WebView getWebView();
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void setWebView(String url);
    }
}

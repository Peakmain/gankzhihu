package com.peakmain.gankzhihu.ui.contract;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 9:41
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhiHuWebContract {
   public interface View extends BaseContract.BaseView{
        WebView getWebView();
        ImageView getWebImg();
        TextView getImgTitle();
        TextView getImgSource();
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getDetailNews(String id);
    }
}

package com.peakmain.gankzhihu.ui.contract;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 11:09
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DailyFeedContract {
    public interface View extends BaseContract.BaseView {

        void setDataRefresh(Boolean refresh);
        RecyclerView getRecyclerView();
        GridLayoutManager getLayoutManager();
    }

    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getDailyFeedDetail(String id,String num);
    }
}

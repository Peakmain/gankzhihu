package com.peakmain.gankzhihu.ui.contract;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 1:34
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankContract {

    public interface View extends BaseContract.BaseView{
        void setDataRefresh(Boolean refresh);
        GridLayoutManager getLayoutManager();
        RecyclerView getRecyclerView();
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getGankData();
    }
}

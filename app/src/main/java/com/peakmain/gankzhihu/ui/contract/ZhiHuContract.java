package com.peakmain.gankzhihu.ui.contract;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.base.BaseContract;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:57
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhiHuContract {
    public interface View extends BaseContract.BaseView{
        void setDataRefresh(Boolean refresh);
        RecyclerView getRecyclerView();
        LinearLayoutManager getLayoutManager();
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getBeforeNews(String time);
        void getLatestNews();
    }
}

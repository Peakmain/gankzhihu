package com.peakmain.gankzhihu.ui.contract;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/16 0016 下午 5:49
 * mail : 2726449200@qq.com
 * describe ：
 */
public class JokeContract {
    public interface View extends BaseContract.BaseView{
        void setDataRefresh(Boolean refresh);
        LinearLayoutManager getLayoutManager();
        RecyclerView getRecyclerView();
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        public void getDetailData(int page);
    }
}

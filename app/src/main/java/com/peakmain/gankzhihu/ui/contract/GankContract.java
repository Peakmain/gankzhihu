package com.peakmain.gankzhihu.ui.contract;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.gankzhihu.base.BaseContract;
import com.peakmain.gankzhihu.bean.gank.Gank;

import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 1:34
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankContract {

    public interface View extends BaseContract.BaseView{
         void displayMeizhi( List<Gank> meiZhiList);
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getGankData(int pageNum);
    }
}

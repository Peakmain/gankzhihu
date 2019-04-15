package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.NetworkUtils;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.adapter.ZhihuListAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.ZhihuApi;
import com.peakmain.gankzhihu.ui.contract.ZhiHuContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:54
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuPresenter extends BasePresenter<ZhiHuContract.View> implements ZhiHuContract.Presenter {

    private Context mContext;


    @Inject
    public ZhihuPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

    @Override
    public void getBeforeNews(String time) {
        if (mView != null) {
            mView.showLoading();
            RetrofitManager.createZhiHuIo(ZhihuApi.class).getBeforetNews(time)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.<NewsTimeLine>applySchedulers())
                    .subscribe(newsTimeLine -> {
                       mView.disPlayZhihuList(newsTimeLine, mContext);
                    }, this::loadError);
        }
    }
    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        if (!NetworkUtils.isConnected()) {
            mView.showNoNet();
        }
        mView.showFaild(throwable.getMessage());
        // mView.hideLoading();
    }


    @Override
    public void getLatestNews() {
        if (mView != null) {
            mView.showLoading();

            RetrofitManager.createZhiHuIo(ZhihuApi.class)
                    .getLatestNews()
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(newsTimeLine -> {
                        mView.disPlayZhihuList(newsTimeLine, mContext);
                    }, this::loadError);
        }
    }


}

package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.adapter.JokesAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.jandan.JokeBean;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.JokeApi;
import com.peakmain.gankzhihu.ui.contract.JokeContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/16 0016 下午 5:49
 * mail : 2726449200@qq.com
 * describe ：
 */
public class JokePresenter extends BasePresenter<JokeContract.View> implements JokeContract.Presenter {
    public final String Type_JOKE = "jandan.get_duan_comments";



    private Context mContext;

    @Inject
    public JokePresenter(@ContextLife Context context) {
        mContext = context;
    }

    @Override
    public void getDetailData(int page) {
        if (mView != null) {
            mView.showLoading();
            RetrofitManager.createJokeIo(JokeApi.class)
                    .getDetailData(Type_JOKE, page)
                    .compose(RxSchedulers.applySchedulers())
                    .compose(mView.bindToLife())
                    .subscribe(jokeBean -> {
                        mView.displayJokeList(jokeBean);
                    }, this::loadError);
        }
    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
    }

}

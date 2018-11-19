package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JokesAdapter mAdapter;
    private int pageNum = 2;
    private JokeBean mJokeBean;
    private boolean isLoadMore = false; // 是否加载过更多
    private Context mContext;
    private int lastVisibleItem;//最后一个可见
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    public JokePresenter(@ContextLife Context context) {
        mContext = context;
    }

    @Override
    public void getDetailData(int page) {
        if (mView != null) {
            mView.showLoading();
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            if (isLoadMore) {
                pageNum = pageNum + 1;
            }
            RetrofitManager.createJokeIo(JokeApi.class)
                    .getDetailData(Type_JOKE, page)
                    .compose(RxSchedulers.applySchedulers())
                    .compose(mView.bindToLife())
                    .subscribe(jokeBean -> {
                        displayJokeList(jokeBean);
                    }, this::loadError);
        }
    }

    private void displayJokeList(JokeBean jokeBean) {
        for (JokeBean.CommentsBean bean : jokeBean.getComments()) {
            if (bean.getPics() != null) {
                if (bean.getPics().size() > 1) {
                    bean.itemType = JokeBean.CommentsBean.TYPE_MULTIPLE;
                } else {
                    bean.itemType = JokeBean.CommentsBean.TYPE_SINGLE;
                }
            }
        }
        if (isLoadMore) {//加载更多
            if (jokeBean.getComments() == null) {
                mView.setDataRefresh(false);
                mAdapter.updateLoadStatus(mAdapter.LOAD_NONE);
                return;
            } else {
                mJokeBean.getComments().addAll(jokeBean.getComments());
            }
            mAdapter.notifyDataSetChanged();
        } else {
            this.mJokeBean = jokeBean;
            mAdapter = new JokesAdapter(mContext, mJokeBean.getComments());
            mRecyclerView.setAdapter(mAdapter);
        }
        mView.setDataRefresh(false);
        mView.hideLoading();
    }

    /**
     * recyclerView滚动监听事件
     */
    public void scrollRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager
                            .findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        mAdapter.updateLoadStatus(mAdapter.LOAD_NONE);
                        return;
                    }
                }
                if (lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                    if (mAdapter != null) {
                        mAdapter.updateLoadStatus(mAdapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        mAdapter.updateLoadStatus(mAdapter.LOAD_MORE);
                        mHandler.postDelayed(() -> getDetailData(pageNum), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
    }
}

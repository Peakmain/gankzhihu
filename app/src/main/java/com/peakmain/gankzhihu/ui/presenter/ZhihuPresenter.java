package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.ZhihuListAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.ZhihuApi;
import com.peakmain.gankzhihu.ui.contract.ZhiHuContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

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
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadMore = false; // 是否加载过更多

    private ZhihuListAdapter adapter;
    private NewsTimeLine mNewsTimeLine;
    private int lastVisibleItem;//最后一个可见
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Inject
    public ZhihuPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

    @Override
    public void getBeforeNews(String time) {
        if (mView != null) {
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            RetrofitManager.createZhiHuIo(ZhihuApi.class).getBeforetNews(time)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.<NewsTimeLine>applySchedulers())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine, mContext, mRecyclerView);
                    }, this::loadError);

        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
        mView.hideLoading();
    }

    String time;

    private void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (time == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                mView.setDataRefresh(false);
                return;
            } else {
                mNewsTimeLine.getStories().addAll(newsTimeLine.getStories());
            }
            adapter.notifyDataSetChanged();
        } else {
            this.mNewsTimeLine = newsTimeLine;
            adapter = new ZhihuListAdapter(context, mNewsTimeLine);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        mView.setDataRefresh(false);
        mView.hideLoading();
        time = mNewsTimeLine.getDate();
    }

    @Override
    public void getLatestNews() {
        if (mView != null) {
            mView.showLoading();
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            RetrofitManager.createZhiHuIo(ZhihuApi.class)
                    .getLatestNews()
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine, mContext, mRecyclerView);
                    }, this::loadError);
        }
    }
    /**
     * recyclerView滚动监听事件
     */
    public void scrollRecyclerView(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager
                            .findLastVisibleItemPosition();
                    if(mLayoutManager.getItemCount()==1){
                        adapter.updateLoadStatus(adapter.LOAD_NONE);
                        return;
                    }
                }
                if(lastVisibleItem+1==mLayoutManager.getItemCount()){
                    adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                    isLoadMore = true;
                    adapter.updateLoadStatus(adapter.LOAD_MORE);
                    mHandler.postDelayed(() -> getBeforeNews(time), 1000);
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

}

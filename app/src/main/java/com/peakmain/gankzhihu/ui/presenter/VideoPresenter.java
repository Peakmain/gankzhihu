package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.adapter.VideoPageAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.video.VideoPageData;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.VideoApi;
import com.peakmain.gankzhihu.ui.contract.VideoContract;
import com.peakmain.gankzhihu.ui.contract.VideoContract.Presenter;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:19
 * mail : 2726449200@qq.com
 * describe ：
 */
public class VideoPresenter extends BasePresenter<VideoContract.View> implements Presenter {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private int page = 20;
    private boolean isLoadMore = false;//判断是否加载更多
    private VideoPageAdapter mAdapter;
    private VideoPageData mVideoPageData;
    private Context mContext;
    private int lastVisibleItem;//最后一个可见
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Inject
    public VideoPresenter(@ContextLife Context context) {
        mContext = context;
    }

    @Override
    public void getVideoData() {
        if (mView != null) {
            mView.showLoading();
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            if (isLoadMore) {
                page += 10;
            }
            RetrofitManager.createVideoIo(VideoApi.class)
                    .getVideoData(page, "baidu", "863425026599592", "baisibudejie",
                            "4.2.2", "android", "", "98%3A6c%3Af5%3A4b%3A72%3A6d", "6.2.8")
                    .compose(RxSchedulers.applySchedulers())
                    .compose(mView.bindToLife())
                    .subscribe(videoPageData -> {
                        displayVideo(videoPageData);
                    }, this::loadError);

        }
    }

    private void displayVideo(VideoPageData videoPageData) {
        if (isLoadMore) {
            if (videoPageData == null) {
                mView.setDataRefresh(false);
                mAdapter.updateLoadStatus(mAdapter.LOAD_NONE);
                return;
            } else {
                mVideoPageData.getList().addAll(videoPageData.getList());
            }
            mAdapter.notifyDataSetChanged();
        } else {
            this.mVideoPageData = videoPageData;
            mAdapter = new VideoPageAdapter(mContext, videoPageData.getList());
            mRecyclerView.setAdapter(mAdapter);
        }
        mView.setDataRefresh(false);
        mView.hideLoading();
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
                        mAdapter.updateLoadStatus(mAdapter.LOAD_NONE);
                        return;
                    }
                }
                if(lastVisibleItem+1==mLayoutManager.getItemCount()){
                    mAdapter.updateLoadStatus(mAdapter.LOAD_PULL_TO);
                    isLoadMore = true;
                    mAdapter.updateLoadStatus(mAdapter.LOAD_MORE);
                    mHandler.postDelayed(() -> getVideoData(), 1000);
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

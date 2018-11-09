package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.DailyListAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.daily.Daily;
import com.peakmain.gankzhihu.bean.daily.DailyTimeLine;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.DailyApi;
import com.peakmain.gankzhihu.ui.contract.DailyContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 4:07
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DailyTimeLine mDailyTimeLine;
    private DailyListAdapter adapter;
    private int lastVisibleItem;
    private String has_more;
    private String next_pager;
    private boolean isLoadMore = false; // 是否加载过更多
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Inject
    public DailyPresenter(@ContextLife Context context) {
        this.mContext = context;
    }
    @Override
    public void getDailyTimeLine(String num) {
        if(mView!=null){
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            RetrofitManager.createDailyIo(DailyApi.class)
                    .getDailyTimeLine(num)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(dailyTimeLine -> {
                        if(dailyTimeLine.getMeta().getMsg().equals("success")){
                            disPlayDailyTimeLine(dailyTimeLine,mRecyclerView);
                        }
                    },this::loadError);
        }
    }
    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.setDataRefresh(false);
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
    private void disPlayDailyTimeLine(DailyTimeLine dailyTimeLine, RecyclerView recyclerView) {
        if(dailyTimeLine.getResponse().getLast_key()!=null){
            next_pager = dailyTimeLine.getResponse().getLast_key();
        }
        has_more = dailyTimeLine.getResponse().getHas_more();
        if (isLoadMore) {
            if (dailyTimeLine.getResponse().getFeeds() == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                mView.setDataRefresh(false);
                return;
            }else{
                mDailyTimeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds());
        }
            adapter.notifyDataSetChanged();
        }else{
            this.mDailyTimeLine=dailyTimeLine;
            adapter = new DailyListAdapter(mContext, mDailyTimeLine.getResponse());
            recyclerView.setAdapter(adapter);
        }
        mView.setDataRefresh(false);
    }
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager
                            .findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(adapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager
                            .getItemCount()) {
                        adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                        if(has_more.equals("true")) {
                            isLoadMore = true;
                        }
                        adapter.updateLoadStatus(adapter.LOAD_MORE);
                        mHandler.postDelayed(() -> getDailyTimeLine(next_pager), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

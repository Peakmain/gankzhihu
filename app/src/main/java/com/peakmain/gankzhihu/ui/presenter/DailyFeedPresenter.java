package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.DailyFeedAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.daily.DailyTimeLine;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.DailyApi;
import com.peakmain.gankzhihu.ui.contract.DailyFeedContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 11:13
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DailyFeedPresenter extends BasePresenter<DailyFeedContract.View> implements DailyFeedContract.Presenter {
    private Context mContext;
    private String mId;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private DailyFeedAdapter adapter;
    //是否有更多
    private String has_more;
    //下一页
    private String next_pager;
    private boolean isLoadMore = false; // 是否加载过更多
    private DailyTimeLine timeLine;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    public DailyFeedPresenter(@ContextLife Context context) {
        this.mContext = context;
    }


    @Override
    public void getDailyFeedDetail(String id, String num) {
        this.mId = id;
        if (mView != null) {
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            RetrofitManager.createDailyIo(DailyApi.class)
                    .getDailyFeedDetail(id, num)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(dailyTimeLine -> {
                        disPlayDailyTimeLine(mContext, dailyTimeLine);
                    }, this::loadError);
        }
    }

    private void disPlayDailyTimeLine(Context context, DailyTimeLine dailyTimeLine) {
        if (dailyTimeLine.getResponse().getLast_key() != null) {
            next_pager = dailyTimeLine.getResponse().getLast_key();
            has_more = dailyTimeLine.getResponse().getHas_more();
        }
        if (isLoadMore) {
            if (dailyTimeLine.getResponse().getOptions() == null) {
                mView.setDataRefresh(false);
                return;
            } else {
                timeLine.getResponse().getOptions().addAll(dailyTimeLine.getResponse().getOptions());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = dailyTimeLine;
            adapter = new DailyFeedAdapter(context, timeLine.getResponse().getOptions());
            mRecyclerView.setAdapter(adapter);
        }
        mView.setDataRefresh(false);
        mView.hideLoading();
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.setDataRefresh(false);
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
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
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager
                            .getItemCount()) {
                        if (has_more.equals("true")) {
                            isLoadMore = true;
                            mView.setDataRefresh(true);
                            mHandler.postDelayed(() -> getDailyFeedDetail(mId, next_pager), 1000);
                        }
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
}

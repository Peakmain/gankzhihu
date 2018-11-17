package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.GankListAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.gank.Gank;
import com.peakmain.gankzhihu.bean.gank.Meizhi;
import com.peakmain.gankzhihu.bean.gank.Video;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.GankApi;
import com.peakmain.gankzhihu.ui.contract.GankContract;
import com.peakmain.gankzhihu.utils.DateUtils;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 1:33
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankPresenter extends BasePresenter<GankContract.View> implements GankContract.Presenter {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private List<Gank> list;
    private int page = 2;
    private GankListAdapter adapter;
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Inject
    public GankPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

    @Override
    public void getGankData(int pageNum) {
        if (mView != null) {
            mView.showLoading();
            mRecyclerView = mView.getRecyclerView();
            mLayoutManager = mView.getLayoutManager();
            if (isLoadMore) {
                page = page + 1;
            }
            GankApi gankApi = RetrofitManager.createGankIo(GankApi.class);
            Observable.zip(gankApi.getMeizhiData(pageNum)
                    , gankApi.getVideoData(pageNum), this::creatDesc)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(meizhi1 -> {
                        displayMeizhi( meizhi1.getResults());
                    }, this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.setDataRefresh(false);
        mView.hideLoading();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    //展示妹子数据
    private void displayMeizhi( List<Gank> meiZhiList) {
        if (isLoadMore) {
            if (meiZhiList == null) {
                mView.setDataRefresh(false);
                return;
            } else {
                list.addAll(meiZhiList);
            }
            adapter.notifyDataSetChanged();
        } else {
            list = meiZhiList;
            adapter = new GankListAdapter(mContext, list);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        mView.setDataRefresh(false);
        mView.hideLoading();
    }

    //滚动
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItem == 1) {
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                        mView.setDataRefresh(true);
                        isLoadMore = true;
                        mHandler.postDelayed(() -> getGankData(page), 1000);
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

    private Meizhi creatDesc(Meizhi meizhi, Video video) {
        for (Gank gankmeizhi : meizhi.getResults()) {
            gankmeizhi.desc = gankmeizhi.desc + " " +
                    getVideoDesc(gankmeizhi.getPublishedAt(), video.getResults());
        }
        return meizhi;
    }

    //匹配同一天的福利描述和视频描述
    private String getVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = 0; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.getPublishedAt() == null) video.setPublishedAt(video.getCreatedAt());
            if (DateUtils.isSameDate(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                break;
            }
        }
        return videoDesc;
    }

    @Override
    public void detachView() {
        super.detachView();
        mHandler.removeCallbacksAndMessages(null);
    }
}

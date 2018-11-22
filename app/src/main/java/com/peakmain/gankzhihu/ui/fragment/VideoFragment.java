package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.VideoPageAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.video.VideoPageData;
import com.peakmain.gankzhihu.ui.contract.VideoContract;
import com.peakmain.gankzhihu.ui.presenter.VideoPresenter;
import com.peakmain.gankzhihu.view.BackTopView;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:18
 * mail : 2726449200@qq.com
 * describe ：
 */
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View, LoadRefreshRecyclerView.OnLoadMoreListener, RefreshRecyclerView.OnRefreshListener {
    @BindView(R.id.mRecyclerView)
    LoadRefreshRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.btn_to_top)
    BackTopView mBackTopView;
    private boolean isLoadMore = false;//判断是否加载更多
    private VideoPageAdapter mAdapter;
    private VideoPageData mVideoPageData;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPresenter.getVideoData();
        mBackTopView.setRecyclerView(mRecyclerView);
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setOnRefreshListener(this);
    }



    @Override
    public void displayVideo(VideoPageData videoPageData) {
        if (isLoadMore) {
            if (videoPageData == null) {
                return;
            } else {
                mVideoPageData.getList().addAll(videoPageData.getList());
            }
            mAdapter.notifyDataSetChanged();
        } else {
            this.mVideoPageData = videoPageData;
            mAdapter = new VideoPageAdapter(getContext(), videoPageData.getList());
            mRecyclerView.setAdapter(mAdapter);
        }
        hideLoading();
    }

    @Override
    public void onLoad() {
        isLoadMore=true;
        mPresenter.getVideoData();
        mRecyclerView.onStopLoad();
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mPresenter.getVideoData();
        mRecyclerView.onStopRefresh();
    }
}

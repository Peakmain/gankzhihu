package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.DailyListAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.daily.DailyTimeLine;
import com.peakmain.gankzhihu.ui.contract.DailyContract;
import com.peakmain.gankzhihu.ui.presenter.DailyPresenter;
import com.peakmain.gankzhihu.view.BackTopView;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：好奇心日报
 */
public class DailyFragment extends BaseFragment<DailyPresenter> implements DailyContract.View, RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    LoadRefreshRecyclerView mRecyclerView;
    @BindView(R.id.btn_to_top)
    BackTopView mTopView;
    private boolean isLoadMore = false; // 是否加载过更多
    private String next_pager;
    private String has_more;
    private DailyListAdapter adapter;
    private DailyTimeLine mDailyTimeLine;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPresenter.getDailyTimeLine("0");
        //recylerview和按钮进行关联
        mTopView.setRecyclerView(mRecyclerView);
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
    }


    @Override
    public void disPlayDailyTimeLine(DailyTimeLine dailyTimeLine) {
        if (dailyTimeLine.getResponse().getLast_key() != null) {
            next_pager = dailyTimeLine.getResponse().getLast_key();
        }
        has_more = dailyTimeLine.getResponse().getHas_more();
        if (isLoadMore) {
            if (dailyTimeLine.getResponse().getFeeds() == null) {
                return;
            } else {
                mDailyTimeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds());
            }
            adapter.notifyDataSetChanged();
        } else {
            this.mDailyTimeLine = dailyTimeLine;
            adapter = new DailyListAdapter(getContext(), mDailyTimeLine.getResponse());
            mRecyclerView.setAdapter(adapter);
        }
        hideLoading();
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mPresenter.getDailyTimeLine("0");
        mRecyclerView.onStopRefresh();
    }

    @Override
    public void onLoad() {
        isLoadMore = true;
        mPresenter.getDailyTimeLine(next_pager);
        mRecyclerView.onStopLoad();
        adapter.notifyDataSetChanged();
    }
}

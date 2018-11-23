package com.peakmain.gankzhihu.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.GankListAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.gank.Gank;
import com.peakmain.gankzhihu.ui.contract.GankContract;
import com.peakmain.gankzhihu.ui.presenter.GankPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：干货
 */
public class GankFragment extends BaseFragment<GankPresenter> implements GankContract.View, RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    private GridLayoutManager mGridLayoutManager;
    private boolean isLoadMore = false; // 是否加载过更多
    @BindView(R.id.content_list)
    LoadRefreshRecyclerView mRecyclerView;
    private int page = 1;
    private List<Gank> list;
    private GankListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mPresenter.getGankData(page);
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
    }

    //展示妹子数据
    @Override
    public void displayMeizhi(List<Gank> meiZhiList) {
        if (isLoadMore) {
            if (meiZhiList == null) {
                return;
            } else {
                list.addAll(meiZhiList);
            }
            adapter.notifyDataSetChanged();
        } else {
            this.list = meiZhiList;
            adapter = new GankListAdapter(getContext(), list);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        hideLoading();
    }

    @Override
    public void onRefresh() {
        page = 1;
        isLoadMore = false;
        mPresenter.getGankData(page);
        mRecyclerView.onStopRefresh();
    }

    @Override
    public void onLoad() {
        page++;
        isLoadMore = true;
        mPresenter.getGankData(page);
        mRecyclerView.onStopLoad();
    }
}

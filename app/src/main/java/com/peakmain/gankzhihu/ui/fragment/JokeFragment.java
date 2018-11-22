package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.JokesAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.jandan.JokeBean;
import com.peakmain.gankzhihu.ui.contract.JokeContract;
import com.peakmain.gankzhihu.ui.presenter.JokePresenter;
import com.peakmain.gankzhihu.view.BackTopView;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/16 0016 下午 5:48
 * mail : 2726449200@qq.com
 * describe ：
 */
public class JokeFragment extends BaseFragment<JokePresenter> implements JokeContract.View, RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    @BindView(R.id.mRecyclerView)
    LoadRefreshRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    @BindView(R.id.btn_to_top)
    BackTopView mBackTopView;
    private boolean isLoadMore = false; // 是否加载过更多
    private JokesAdapter mAdapter;
    private JokeBean mJokeBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPresenter.getDetailData(page);
        mBackTopView.setRecyclerView(mRecyclerView);
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
    }

    @Override
    public void displayJokeList(JokeBean jokeBean) {
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
                return;
            } else {
                mJokeBean.getComments().addAll(jokeBean.getComments());
            }
            mAdapter.notifyDataSetChanged();
        } else {
            this.mJokeBean = jokeBean;
            mAdapter = new JokesAdapter(getContext(), mJokeBean.getComments());
            mRecyclerView.setAdapter(mAdapter);
        }
        hideLoading();
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        page=1;
        mPresenter.getDetailData(page);
        mRecyclerView.onStopRefresh();
    }

    @Override
    public void onLoad() {
        isLoadMore=true;
        page++;
        mPresenter.getDetailData(page);
        mRecyclerView.onStopRefresh();
    }
}

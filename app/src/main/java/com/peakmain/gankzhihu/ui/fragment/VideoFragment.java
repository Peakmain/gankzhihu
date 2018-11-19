package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseFragment;
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
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.btn_to_top)
    BackTopView mBackTopView;
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
        setDataRefresh(true);
        mPresenter.getVideoData();
        mPresenter.scrollRecyclerView();
        mBackTopView.setRecyclerView(mRecyclerView);
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);

    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getVideoData();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }
}

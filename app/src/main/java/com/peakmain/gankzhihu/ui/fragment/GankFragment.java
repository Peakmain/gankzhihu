package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.ui.contract.GankContract;
import com.peakmain.gankzhihu.ui.presenter.GankPresenter;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankFragment extends BaseFragment<GankPresenter> implements GankContract.View {
    private GridLayoutManager mGridLayoutManager;

    @BindView(R.id.content_list)
    RecyclerView mRecyclerView;
    private int page=1;

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
        setDataRefresh(true);
        mPresenter.getGankData(page);
        mPresenter.scrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getGankData(page);
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return mGridLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}

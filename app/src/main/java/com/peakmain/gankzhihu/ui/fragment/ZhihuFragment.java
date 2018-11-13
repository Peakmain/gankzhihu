package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.ui.contract.ZhiHuContract;
import com.peakmain.gankzhihu.ui.presenter.ZhihuPresenter;
import com.peakmain.gankzhihu.view.BackTopView;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuFragment extends BaseFragment<ZhihuPresenter> implements ZhiHuContract.View {
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_to_top)
    BackTopView mTopView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setDataRefresh(true);
        mPresenter.getLatestNews();
        mPresenter.scrollRecyclerView();
        //recylerview和按钮进行关联
        mTopView.setRecyclerView(mRecyclerView);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getLatestNews();
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }


}

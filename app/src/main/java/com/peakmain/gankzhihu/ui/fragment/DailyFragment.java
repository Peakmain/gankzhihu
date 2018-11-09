package com.peakmain.gankzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.ui.contract.DailyContract;
import com.peakmain.gankzhihu.ui.presenter.DailyPresenter;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DailyFragment extends BaseFragment<DailyPresenter> implements DailyContract.View {
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    RecyclerView mRecyclerView;
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
        setDataRefresh(true);
        mPresenter.getDailyTimeLine("0");
        mPresenter.scrollRecycleView();
    }
    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getDailyTimeLine("0");
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
}

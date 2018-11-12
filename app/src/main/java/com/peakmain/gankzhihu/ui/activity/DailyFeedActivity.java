package com.peakmain.gankzhihu.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.ui.contract.DailyFeedContract;
import com.peakmain.gankzhihu.ui.presenter.DailyFeedPresenter;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/12 0012 上午 11:09
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path = "/activity/DailyFeedActivity")
public class DailyFeedActivity extends BaseActivity<DailyFeedPresenter> implements DailyFeedContract.View {
    public static final String FEED_ID = "feed_id";
    public static final String FEED_DESC = "feed_desc";
    public static final String FEED_TITLE = "feed_title";
    public static final String FEED_IMG = "feed_img";
    private String id;
    private String desc;
    private String title;
    private String img;

    @BindView(R.id.iv_feed_img)
    ImageView mIvFeedImg;
    @BindView(R.id.tv_feed_title)
    TextView mTvFeedTitle;
    @BindView(R.id.tv_feed_desc)
    TextView mTvFeedDesc;
    @BindView(R.id.feed_list)
    RecyclerView mFeedList;

    private GridLayoutManager mGridLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_feed;
    }

    @Override
    protected void initInjector() {
      mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        parseIntent();
        mTvFeedTitle.setText(title);
        mTvFeedDesc.setText(desc);
        Glide.with(this).load(img).centerCrop().into(mIvFeedImg);
        mGridLayoutManager = new GridLayoutManager(this,2);
        mFeedList.setLayoutManager(mGridLayoutManager);

        mPresenter.getDailyFeedDetail(id,"0");
        mPresenter.scrollRecycleView();
    }
    private void parseIntent() {
        id = getIntent().getStringExtra(FEED_ID);
        desc = getIntent().getStringExtra(FEED_DESC);
        title = getIntent().getStringExtra(FEED_TITLE);
        img = getIntent().getStringExtra(FEED_IMG);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPresenter.getDailyFeedDetail(id,"0");
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mFeedList;
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return mGridLayoutManager;
    }
}

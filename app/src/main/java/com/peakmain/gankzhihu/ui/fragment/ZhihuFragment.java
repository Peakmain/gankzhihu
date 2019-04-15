package com.peakmain.gankzhihu.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.peakmain.baselibrary.banner.BannerAdapter;
import com.peakmain.baselibrary.banner.BannerView;
import com.peakmain.baselibrary.banner.BannerViewPager;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.OnFeedShowCallBack;
import com.peakmain.gankzhihu.adapter.ZhihuListAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;
import com.peakmain.gankzhihu.bean.zhihu.TopStories;
import com.peakmain.gankzhihu.launchstarter.utils.DelayInitDispatcher;
import com.peakmain.gankzhihu.tasks.delayinittask.DelayInitTaskA;
import com.peakmain.gankzhihu.tasks.delayinittask.DelayInitTaskB;
import com.peakmain.gankzhihu.ui.activity.ZhihuWebActivity;
import com.peakmain.gankzhihu.ui.contract.ZhiHuContract;
import com.peakmain.gankzhihu.ui.presenter.ZhihuPresenter;
import com.peakmain.gankzhihu.utils.BannerUtils;
import com.peakmain.gankzhihu.view.BackTopView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:14
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuFragment extends BaseFragment<ZhihuPresenter> implements ZhiHuContract.View, LoadRefreshRecyclerView.OnLoadMoreListener, RefreshRecyclerView.OnRefreshListener {
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.content_list)
    LoadRefreshRecyclerView mRecyclerView;

    @BindView(R.id.btn_to_top)
    BackTopView mTopView;
    NewsTimeLine mNewsTimeLine;
    private ZhihuListAdapter adapter;
    private String time;
    private boolean isLoadMore = false;
    private ArrayList<String> mBannerImage;
    private ArrayList<String> mBannerTitle;
    private View mTopBannerView;

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
        mPresenter.getLatestNews();
        //recylerview和按钮进行关联
        mTopView.setRecyclerView(mRecyclerView);
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setOnRefreshListener(this);

    }

    private void addTopView() {
        mTopBannerView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_zhihu_top_stories, mRecyclerView, false);
        List<TopStories> topList = mNewsTimeLine.getTop_stories();
        mBannerImage = new ArrayList<>();
        mBannerTitle = new ArrayList<>();

        for (TopStories topStories : topList) {
            mBannerImage.add(topStories.getImage());
            mBannerTitle.add(topStories.getTitle());
        }
        BannerView mBanner = mTopBannerView.findViewById(R.id.vp_top_stories);
        mBanner.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(getContext());
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(getContext()).load(topList.get(position).getImage()).into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return topList.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return topList.get(position).getTitle();
            }
        });
        //BannerUtils.initBanner(mBanner, mBannerImage, mBannerTitle);
        //下标从0开始
        mBanner.setOnBannerItemClickListener(new BannerViewPager.BannerItemClickListener() {
            @Override
            public void click(int position) {
                ARouter.getInstance().build("/activity/ZhihuWebActivity")
                        .withString(ZhihuWebActivity.ID, topList.get(position).getId())
                        .navigation();
            }
        });
        mRecyclerView.addHeaderView(mTopBannerView);
    }

    @Override
    public void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context) {
        if (isLoadMore) {
            if (time == null) {
                return;
            } else {
                mNewsTimeLine.getStories().addAll(newsTimeLine.getStories());
            }
            adapter.notifyDataSetChanged();
            if (mTopBannerView == null)
                addTopView();
        } else {
            this.mNewsTimeLine = newsTimeLine;
            adapter = new ZhihuListAdapter(context, mNewsTimeLine.getStories());
            mRecyclerView.setAdapter(adapter);
            adapter.setOnFeedShowCallBack(() -> {
                DelayInitDispatcher delayInitDispatcher = new DelayInitDispatcher();
                delayInitDispatcher.addTask(new DelayInitTaskA())
                        .addTask(new DelayInitTaskB())
                        .start();
            });
            adapter.notifyDataSetChanged();
            addTopView();
        }
        hideLoading();
        time = mNewsTimeLine.getDate();

    }


    @Override
    public void hideLoading() {
        super.hideLoading();
    }


    @Override
    public void onLoad() {
        mPresenter.getBeforeNews(time);
        isLoadMore = true;
        mRecyclerView.onStopLoad();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mPresenter.getLatestNews();
        mRecyclerView.onStopRefresh();
    }
}

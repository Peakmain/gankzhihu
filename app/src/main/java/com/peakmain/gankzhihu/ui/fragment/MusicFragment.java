package com.peakmain.gankzhihu.ui.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peakmain.baselibrary.recylerview.widget.WrapRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.MusicPagerAdapter;
import com.peakmain.gankzhihu.base.BaseFragment;
import com.peakmain.gankzhihu.bean.video.MediaItem;
import com.peakmain.gankzhihu.ui.activity.MainActivity;
import com.peakmain.gankzhihu.ui.contract.MusicContract;
import com.peakmain.gankzhihu.ui.presenter.MusicPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/29 0029 上午 10:31
 * mail : 2726449200@qq.com
 * describe ：
 */
public class MusicFragment extends BaseFragment<MusicPresenter> implements MusicContract.View {
    @BindView(R.id.wrap_recycler_view)
    WrapRecyclerView mWrapRecyclerView;
    private MusicPagerAdapter mMusicPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        //加载本地视频数据
        mPresenter.getMusicDataFromLocal();
        mWrapRecyclerView.addEmptyView(view.findViewById(R.id.empty_view));
    }


    @Override
    public void displayMusic(List<MediaItem> mediaItems) {
        if (mediaItems != null && mediaItems.size() > 0) {
            mMusicPagerAdapter = new MusicPagerAdapter(getContext(), mediaItems);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mWrapRecyclerView.setLayoutManager(layoutManager);
            mWrapRecyclerView.setAdapter(mMusicPagerAdapter);
            mMusicPagerAdapter.notifyDataSetChanged();
        }
    }
}

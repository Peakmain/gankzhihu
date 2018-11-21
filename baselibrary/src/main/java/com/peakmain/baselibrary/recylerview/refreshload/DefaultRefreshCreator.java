package com.peakmain.baselibrary.recylerview.refreshload;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.peakmain.baselibrary.R;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshViewCreator;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/21 0021 下午 3:31
 * mail : 2726449200@qq.com
 * describe ：默认样式的刷新头部辅助类,这里使用的是百思不得姐
 */
public class DefaultRefreshCreator extends RefreshViewCreator {
    // 加载数据的ImageView
    private ImageView mRefreshIv;
    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.img_progress);
        return refreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
        if (currentRefreshStatus == LoadRefreshRecyclerView.LOAD_STATUS_PULL_DOWN_REFRESH) {
            mRefreshIv.setImageResource(R.drawable.list_view_pull);
        }
        if (currentRefreshStatus == LoadRefreshRecyclerView.LOAD_STATUS_LOOSEN_LOADING) {
            mRefreshIv.setImageResource(R.drawable.list_view_release);
        }
    }

    @Override
    public void onRefreshing() {
        mRefreshIv.setImageResource(R.drawable.load_more_anim);
        ((AnimationDrawable) mRefreshIv.getBackground()).start();
    }

    @Override
    public void onStopRefresh() {
        // 停止加载的时候清除动画
        mRefreshIv.setRotation(0);
        ((AnimationDrawable) mRefreshIv.getBackground()).stop();
    }
}

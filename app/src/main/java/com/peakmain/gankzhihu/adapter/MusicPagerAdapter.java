package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.Formatter;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.video.MediaItem;
import com.peakmain.gankzhihu.ui.activity.MainActivity;
import com.peakmain.gankzhihu.utils.DateUtils;

import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/29 0029 上午 10:37
 * mail : 2726449200@qq.com
 * describe ：
 */
public class MusicPagerAdapter extends CommonRecyclerAdapter<MediaItem> {
    private Context mContext;
    public MusicPagerAdapter(Context context, List<MediaItem> data) {
        super(context, data, R.layout.item_music_pager);
        this.mContext=context;
    }

    @Override
    public void convert(ViewHolder holder, MediaItem mediaItem) {
        holder.setText(R.id.tv_name,mediaItem.getName());
        holder.setText(R.id.tv_size,Formatter.formatFileSize(mContext, mediaItem.getSize()));
        holder.setText(R.id.tv_time, DateUtils.stringForTime((int) mediaItem.getDuration()));
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/activity/MusicPlayerActivity")
                        .withInt("position", holder.getAdapterPosition())
                        .navigation();
            }
        });
    }
}

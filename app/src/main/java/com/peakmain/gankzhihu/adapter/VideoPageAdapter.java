package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.MultiTypeSupport;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.video.VideoPageData;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.utils.DateUtils;
import com.peakmain.gankzhihu.utils.ScreenUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:43
 * mail : 2726449200@qq.com
 * describe ：
 */
public class VideoPageAdapter extends CommonRecyclerAdapter<VideoPageData.ListBean> {
    private Context mContext;

    @Inject
    public VideoPageAdapter(@ContextLife Context context, List<VideoPageData.ListBean> data) {
        super(context, data, new MultiTypeSupport<VideoPageData.ListBean>() {
            @Override
            public int getLayoutId(VideoPageData.ListBean bean, int position) {
                String type = bean.getType();
                if ("video".equals(type)) {
                    return R.layout.item_video;
                } else if ("image".equals(type)) {
                    return R.layout.item_image_item;
                } else if ("text".equals(type)) {
                    return R.layout.item_text_item;
                } else if ("gif".equals(type)) {
                    return R.layout.item_gif_item;
                } else {
                    return R.layout.item_ad_item;//广播
                }
            }
        });
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, VideoPageData.ListBean mediaItem) {
        if (mediaItem.getU() != null && mediaItem.getU().getHeader() != null && mediaItem.getU().getHeader().get(0) != null) {
            Glide.with(mContext).load(mediaItem.getU().getHeader().get(0)).centerCrop().into((ImageView) holder.getView(R.id.iv_headpic));
        }
        if (mediaItem.getU() != null && mediaItem.getU().getName() != null) {
            holder.setText(R.id.tv_name, mediaItem.getU().getName() + "");
        }
        holder.setText(R.id.tv_time_refresh, mediaItem.getPasstime());
        //设置标签
        List<VideoPageData.ListBean.TagsBean> tagsEntities = mediaItem.getTags();
        if (tagsEntities != null && tagsEntities.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < tagsEntities.size(); i++) {
                buffer.append(tagsEntities.get(i).getName() + " ");
            }
            holder.setText(R.id.tv_video_kind_text, buffer.toString());
        }
        //设置点赞，踩,转发
        holder.setText(R.id.tv_shenhe_ding_number,mediaItem.getUp());
        holder.setText(R.id.tv_shenhe_cai_number,mediaItem.getDown() + "");
        holder.setText(R.id.tv_posts_number,mediaItem.getForward() + "");
        holder.setText(R.id.tv_context,mediaItem.getText());

        String type = mediaItem.getType();
        if ("video".equals(type)) {
            JzvdStd jzv_videoplayer = holder.getView(R.id.jzv_videoplayer);
            //第一个参数是视频播放地址
            jzv_videoplayer.setUp(mediaItem.getVideo().getVideo().get(0), null, Jzvd.SCREEN_WINDOW_NORMAL);
            Glide.with(mContext).load(mediaItem.getVideo().getThumbnail().get(0)).centerCrop().into(jzv_videoplayer.thumbImageView);
            holder.setText(R.id.tv_play_nums, mediaItem.getVideo().getPlaycount() + "次播放");
            holder.setText(R.id.tv_video_duration, DateUtils.stringForTime(mediaItem.getVideo().getDuration() * 1000) + "");
        } else if ("image".equals(type)) {
            ImageView iv_image_icon = holder.getView(R.id.iv_image_icon);
            holder.setImageResource(R.id.iv_image_icon, R.drawable.bg_item);
            int height = mediaItem.getImage().getHeight() <= ScreenUtil.instance(mContext).getScreenHeight() * 0.75 ? mediaItem.getImage().getHeight() : (int) (ScreenUtil.instance(mContext).getScreenHeight() * 0.75);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.instance(mContext).getScreenWidth(), height);
            iv_image_icon.setLayoutParams(params);
            if (mediaItem.getImage() != null && mediaItem.getImage().getBig() != null && mediaItem.getImage().getBig().size() > 0) {
                Glide.with(mContext).load(mediaItem.getImage().getBig().get(0)).placeholder(R.drawable.bg_item).error(R.drawable.bg_item).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_image_icon);
            }
        } else if ("text".equals(type)) {

        } else if ("gif".equals(type)) {
            Glide.with(mContext).load(mediaItem.getGif().getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.iv_image_gif));
        } else {
        }
    }


}

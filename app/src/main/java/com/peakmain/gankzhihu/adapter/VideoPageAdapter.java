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
public class VideoPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoPageData.ListBean> mDatas;
    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_FOOTER = -1;

    /**
     * 视频
     */
    private static final int TYPE_VIDEO = 100;

    /**
     * 图片
     */
    private static final int TYPE_IMAGE = 101;

    /**
     * 文字
     */
    private static final int TYPE_TEXT = 102;

    /**
     * GIF图片
     */
    private static final int TYPE_GIF = 103;


    /**
     * 软件推广
     */
    private static final int TYPE_AD = 104;

    @Inject
    public VideoPageAdapter(@ContextLife Context context, List<VideoPageData.ListBean> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public int getItemViewType(int position) {
        VideoPageData.ListBean bean = mDatas.get(position);
        String type = bean.getType();//video,text,image,gif,ad
        if ("video".equals(type)) {
            return TYPE_VIDEO;
        } else if ("image".equals(type)) {
            return TYPE_IMAGE;
        } else if ("text".equals(type)) {
            return TYPE_TEXT;
        } else if ("gif".equals(type)) {
            return TYPE_GIF;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_AD;//广播
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = View.inflate(parent.getContext(), R.layout.activity_view_footer, null);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_VIDEO) {//视频
            View videoView = View.inflate(parent.getContext(), R.layout.item_video, null);
            return new VideoViewHolder(videoView);
        } else if (viewType == TYPE_IMAGE) {//图片
            View imageView = View.inflate(parent.getContext(), R.layout.item_image_item, null);
            return new ImageViewHolder(imageView);
        } else if (viewType == TYPE_TEXT) {//文字
            View textView = View.inflate(parent.getContext(), R.layout.item_text_item, null);
            return new TextViewHolder(textView);
        } else if (viewType == TYPE_GIF) {//gif
            View gifView = View.inflate(parent.getContext(), R.layout.item_gif_item, null);
            return new GifViewHolder(gifView);
        } else {//软件广告
            View adView = View.inflate(parent.getContext(), R.layout.item_ad_item, null);
            return new AdViewHolder(adView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof VideoViewHolder) {
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
            videoViewHolder.bindItem(mDatas.get(position));
        } else if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.bindItem(mDatas.get(position));
        } else if (holder instanceof GifViewHolder) {
            GifViewHolder gifViewHolder = (GifViewHolder) holder;
            gifViewHolder.bindItem(mDatas.get(position));
        }
        if (holder != null) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            baseViewHolder.bindItem(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * footer view
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_load_prompt)
        TextView tv_load_prompt;
        @BindView(R.id.progress)
        ProgressBar progress;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(mContext).dip2px(40));
            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    progress.setVisibility(View.VISIBLE);
                    tv_load_prompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("已无更多加载");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }

    class VideoViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_play_nums)
        TextView tv_play_nums;
        @BindView(R.id.tv_video_duration)
        TextView tv_video_duration;
        @BindView(R.id.iv_commant)
        ImageView iv_commant;
        @BindView(R.id.tv_commant_context)
        TextView tv_commant_context;
        @BindView(R.id.jzv_videoplayer)
        JzvdStd jzv_videoplayer;

        public VideoViewHolder(View itemView) {
            super(itemView);
        }

        private void bindItem(VideoPageData.ListBean mediaItem) {
            //第一个参数是视频播放地址
            jzv_videoplayer.setUp(mediaItem.getVideo().getVideo().get(0), null, Jzvd.SCREEN_WINDOW_NORMAL);
            Glide.with(mContext).load(mediaItem.getVideo().getThumbnail().get(0)).centerCrop().into(jzv_videoplayer.thumbImageView);
            tv_play_nums.setText(mediaItem.getVideo().getPlaycount() + "次播放");
            tv_video_duration.setText(DateUtils.stringForTime(mediaItem.getVideo().getDuration() * 1000) + "");
        }
    }

    class ImageViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_image_icon)
        ImageView iv_image_icon;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        private void bindItem(VideoPageData.ListBean mediaItem) {
            iv_image_icon.setImageResource(R.drawable.bg_item);
            int height = mediaItem.getImage().getHeight() <= ScreenUtil.instance(mContext).getScreenHeight() * 0.75 ? mediaItem.getImage().getHeight() : (int) (ScreenUtil.instance(mContext).getScreenHeight() * 0.75);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.instance(mContext).getScreenWidth(), height);
            iv_image_icon.setLayoutParams(params);
            if (mediaItem.getImage() != null && mediaItem.getImage().getBig() != null && mediaItem.getImage().getBig().size() > 0) {
//                    x.image().bind(viewHolder.iv_image_icon, mediaItem.getImage().getBig().get(0));
                Glide.with(mContext).load(mediaItem.getImage().getBig().get(0)).placeholder(R.drawable.bg_item).error(R.drawable.bg_item).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_image_icon);
            }
        }
    }

    class TextViewHolder extends BaseViewHolder {
        public TextViewHolder(View itemView) {
            super(itemView);

        }
    }

    class GifViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_image_gif)
        GifImageView iv_image_gif;

        public GifViewHolder(View itemView) {
            super(itemView);
        }

        private void bindItem(VideoPageData.ListBean mediaItem) {
            Glide.with(mContext).load(mediaItem.getGif().getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_image_gif);
        }
    }

    class AdViewHolder extends BaseViewHolder {
        @BindView(R.id.btn_install)
        Button btn_install;

        public AdViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        //user_info
        @BindView(R.id.iv_headpic)
        ImageView iv_headpic;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time_refresh)
        TextView tv_time_refresh;
        @BindView(R.id.iv_right_more)
        ImageView iv_right_more;
        //bottom
        @BindView(R.id.iv_video_kind)
        ImageView iv_video_kind;
        @BindView(R.id.tv_video_kind_text)
        TextView tv_video_kind_text;
        @BindView(R.id.tv_shenhe_ding_number)
        TextView tv_shenhe_ding_number;
        @BindView(R.id.tv_shenhe_cai_number)
        TextView tv_shenhe_cai_number;
        @BindView(R.id.tv_posts_number)
        TextView tv_posts_number;
        @BindView(R.id.ll_download)
        LinearLayout ll_download;

        //中间公共部分 -所有的都有
        @BindView(R.id.tv_context)
        TextView tv_context;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindItem(VideoPageData.ListBean mediaItem) {
            if (mediaItem.getU() != null && mediaItem.getU().getHeader() != null && mediaItem.getU().getHeader().get(0) != null) {
                Glide.with(mContext).load(mediaItem.getU().getHeader().get(0)).centerCrop().into(iv_headpic);
            }
            if (mediaItem.getU() != null && mediaItem.getU().getName() != null) {
                tv_name.setText(mediaItem.getU().getName() + "");
            }
            tv_time_refresh.setText(mediaItem.getPasstime());
            //设置标签
            List<VideoPageData.ListBean.TagsBean> tagsEntities = mediaItem.getTags();
            if (tagsEntities != null && tagsEntities.size() > 0) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < tagsEntities.size(); i++) {
                    buffer.append(tagsEntities.get(i).getName() + " ");
                }
                tv_video_kind_text.setText(buffer.toString());
            }
            //设置点赞，踩,转发
            tv_shenhe_ding_number.setText(mediaItem.getUp());
            tv_shenhe_cai_number.setText(mediaItem.getDown() + "");
            tv_posts_number.setText(mediaItem.getForward() + "");
            tv_context.setText(mediaItem.getText());
        }
    }

    //改变recylerview的状态
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }
}

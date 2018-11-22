package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.peakmain.baselibrary.banner.BannerView;
import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.MultiTypeSupport;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.baselibrary.recylerview.loader.GlideImageLoader;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.zhihu.NewsTimeLine;
import com.peakmain.gankzhihu.bean.zhihu.Stories;
import com.peakmain.gankzhihu.bean.zhihu.TopStories;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.ui.activity.ZhihuWebActivity;
import com.peakmain.gankzhihu.utils.BannerUtils;
import com.peakmain.gankzhihu.utils.ScreenUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 10:18
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuListAdapter extends CommonRecyclerAdapter<Stories> {
    private Context mContext;

    @Inject
    public ZhihuListAdapter(@ContextLife Context context, List<Stories> data) {
        super(context, data, R.layout.item_zhihu_stories);
        this.mContext = context;
    }
    @Override
    public void convert(ViewHolder holder, Stories stories) {


        ScreenUtil screenUtil = ScreenUtil.instance(mContext);
        int screenWidth = screenUtil.getScreenWidth();
        CardView card_stories = holder.getView(R.id.card_stories);
        card_stories.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        holder.setText(R.id.tv_stories_title, stories.getTitle());
        String[] images = stories.getImages();
        holder.setImageByUrl(R.id.iv_stories_img, new GlideImageLoader(images[0]));
        /*context.startActivity(ZhihuWebActivity.newIntent(context,stories.getId()))*/
        card_stories.setOnClickListener(v ->
                ARouter.getInstance().build("/activity/ZhihuWebActivity")
                        .withString(ZhihuWebActivity.ID, stories.getId())
                        .navigation());
    }

}






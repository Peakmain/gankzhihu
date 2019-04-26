package com.peakmain.gankzhihu.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.peakmain.baselibrary.launchstarter.utils.LaunchTimer;
import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.baselibrary.recylerview.loader.GlideImageLoader;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.zhihu.Stories;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.ui.activity.ZhihuWebActivity;
import com.peakmain.gankzhihu.utils.ScreenUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 10:18
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ZhihuListAdapter extends CommonRecyclerAdapter<Stories> {
    private Context mContext;
    private boolean mHasRecord;
    private OnFeedShowCallBack mCallBack;

    public void setOnFeedShowCallBack(OnFeedShowCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Inject
    public ZhihuListAdapter(@ContextLife Context context, List<Stories> data) {
        super(context, data, R.layout.item_zhihu_stories);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Stories stories) {
        if (holder.getAdapterPosition() == 0 && !mHasRecord) {
            mHasRecord = true;
            holder.getView(R.id.card_stories).getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            holder.getView(R.id.card_stories).getViewTreeObserver().removeOnPreDrawListener(this);
                            LogUtils.i("FeedShow");
                            LaunchTimer.endRecord();
                            if (mCallBack != null) {
                                mCallBack.onFeedShow();
                            }
                        /*    WakeLockUtils.acquire(holder.getView(R.id.card_stories).getContext());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    WakeLockUtils.release();
                                }
                            },200);*/
                            return true;
                        }
                    });
        }

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
       /*       mContext.startActivity(new Intent(mContext,MemoryLeakActivity.class)));*/
    }

}






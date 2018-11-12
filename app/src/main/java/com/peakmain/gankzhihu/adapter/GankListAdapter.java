package com.peakmain.gankzhihu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.gank.Gank;
import com.peakmain.gankzhihu.ui.activity.PictureActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 1:51
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Gank> mList;

    public GankListAdapter(Context mContext, List<Gank> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_meizi,parent,false);
        return new GankMeiZhiViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankMeiZhiViewHolder){
            GankMeiZhiViewHolder gankMeiZhiViewHolder = (GankMeiZhiViewHolder) holder;
            gankMeiZhiViewHolder.bindItem(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GankMeiZhiViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.card_meizhi)
        CardView card_meizhi;
        @BindView(R.id.iv_meizhi)
        ImageView iv_meizhi;
        @BindView(R.id.tv_meizhi_title)
        TextView tv_meizhi_title;

        public GankMeiZhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }

        public void bindItem(Gank meizhi){
            tv_meizhi_title.setText(meizhi.getDesc());
            Glide.with(mContext).load(meizhi.getUrl()).centerCrop().into(iv_meizhi);

            //点击图片
            iv_meizhi.setOnClickListener(v -> {
                Intent intent = PictureActivity.newIntent(mContext,meizhi.getUrl(),meizhi.getDesc());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,iv_meizhi,PictureActivity.TRANSIT_PIC);
                // Android 5.0+
                try {
                    ActivityCompat.startActivity((Activity) mContext,intent,optionsCompat.toBundle());
                } catch (Exception e) {
                    e.printStackTrace();
                    mContext.startActivity(intent);
                }
            });

        }
    }
}

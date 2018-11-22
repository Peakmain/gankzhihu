package com.peakmain.gankzhihu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.baselibrary.recylerview.loader.GlideImageLoader;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.gank.Gank;
import com.peakmain.gankzhihu.ui.activity.PictureActivity;

import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 1:51
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GankListAdapter extends CommonRecyclerAdapter<Gank> {
    private Context mContext;



    public GankListAdapter(Context context, List<Gank> data) {
        super(context, data, R.layout.item_gank_meizi);
        this.mContext = context;
    }


    @Override
    public void convert(ViewHolder holder, Gank gank) {
        holder.setText(R.id.tv_meizhi_title,gank.getDesc());
       holder.setImageByUrl(R.id.iv_meizhi, new GlideImageLoader(gank.getUrl()));
       holder.setOnItemClickListener(R.id.iv_meizhi,new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = PictureActivity.newIntent(mContext,gank.getUrl(),gank.getDesc());
               ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext
                       ,holder.getView(R.id.iv_meizhi),PictureActivity.TRANSIT_PIC);
               // Android 5.0+
               try {
                   ActivityCompat.startActivity((Activity) mContext,intent,optionsCompat.toBundle());
               } catch (Exception e) {
                   e.printStackTrace();
                   mContext.startActivity(intent);
               }
           }
       });
    }


}

package com.peakmain.gankzhihu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.baselibrary.recylerview.loader.GlideImageLoader;
import com.peakmain.gankzhihu.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 下午 2:44
 * mail : 2726449200@qq.com
 * describe ：
 */
public class LiveItemAdapter extends CommonRecyclerAdapter<String>{
    // 数据集
    private String[] mDataList = new String[] {
            "CCTV-1 综合","CCTV-2 财经","CCTV-3 综艺","CCTV-4 中文国际(亚)","CCTV-5 体育",
            "CCTV-6 电影","CCTV-7 军事农业","CCTV-8 电视剧", "CCTV-9 纪录","CCTV-10 科教",
            "CCTV-11 戏曲","CCTV-12 社会与法","CCTV-13 新闻","CCTV-14 少儿","CCTV-15 音乐",
            "湖南卫视","北京卫视","天津卫视","湖北卫视","东方卫视",
    };

    private int[] mIconList = new int[] {
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3, R.drawable.cctv_4, R.drawable.cctv_5,
            R.drawable.cctv_6, R.drawable.cctv_7, R.drawable.cctv_8, R.drawable.cctv_9, R.drawable.cctv_10,
            R.drawable.cctv_11, R.drawable.cctv_12, R.drawable.cctv_13, R.drawable.cctv_14, R.drawable.cctv_15,
            R.drawable.hunan_tv,R.drawable.beijing_tv,R.drawable.tianjing_tv,R.drawable.hubei_tv,R.drawable.dongfang_tv,
    };


    public LiveItemAdapter(Context context, List<String> data) {
            super(context ,data, R.layout.item_live);
    }


    @Override
    public void convert(ViewHolder holder, String item) {
        int position = holder.getAdapterPosition();
        holder.setText(R.id.tv_live_title,mDataList[position]);
        holder.setImageResource(R.id.iv_live_icon,mIconList[position]);
        holder.setOnItemClickListener(v -> {

        });
    }
}

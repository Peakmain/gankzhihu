package com.peakmain.baselibrary.recylerview.loader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/22 0022 下午 1:32
 * mail : 2726449200@qq.com
 * describe ：
 */
public class GlideImageLoader extends ViewHolder.HolderImageLoader {
    public GlideImageLoader(String imagePath) {
        super(imagePath);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String imagePath) {
        Glide.with(context).load(imagePath).centerCrop().into(imageView);
    }
}

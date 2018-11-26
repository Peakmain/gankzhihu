package com.peakmain.gankzhihu.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 下午 3:11
 * mail : 2726449200@qq.com
 * describe ：
 */
public class ListVideoView extends FrameLayout {
    private Context mContext;
    public ListVideoView(@NonNull Context context) {
        this(context, null);
    }

    public ListVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {

    }

}

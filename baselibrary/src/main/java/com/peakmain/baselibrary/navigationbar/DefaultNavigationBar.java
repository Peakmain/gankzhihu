package com.peakmain.baselibrary.navigationbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.peakmain.baselibrary.R;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 下午 12:15
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder>{
    DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    @Override
    public void attachNavigationParams() {
        super.attachNavigationParams();
        // 处理特有的
        TextView leftView = findViewById(R.id.back_tv);
        leftView.setVisibility(getBuilder().mLeftVisible);

    }

    public static class Builder extends AbsNavigationBar.Builder<DefaultNavigationBar.Builder> {
        public int mLeftVisible = View.VISIBLE;
        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.ui_defualt_navigation_bar, parent);
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }
        public Builder setLeftText(String text){
            setText(R.id.back_tv,text);
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener clickListener){
            setOnClickListener(R.id.back_tv,clickListener);
            return this;
        }

        public Builder hideLeftText() {
            mLeftVisible = View.GONE;
            return this;
        }
    }
}

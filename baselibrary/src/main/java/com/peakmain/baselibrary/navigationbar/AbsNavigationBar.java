package com.peakmain.baselibrary.navigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 上午 11:45
 * mail : 2726449200@qq.com
 * describe ：
 */
public class AbsNavigationBar<B extends AbsNavigationBar.Builder> implements INavigation {
    private B mBuilder;
    private View mNavigationBarView;

    AbsNavigationBar(B builder) {
        mBuilder = builder;
        createNavigationBar();
    }

    @Override
    public void createNavigationBar() {
        mNavigationBarView = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId, mBuilder.mParent, false);
        //添加
        attachParent(mNavigationBarView, mBuilder.mParent);
        //绑定参数
        attachNavigationParams();
    }

    /**
     * 绑定参数
     */
    public void attachNavigationParams() {
        //设置文本
        Map<Integer, CharSequence> textMaps = mBuilder.mTextMaps;
        for (Map.Entry<Integer, CharSequence> entry : textMaps.entrySet()) {
            TextView textView = findViewById(entry.getKey());
            textView.setText(entry.getValue());
        }
        // 设置点击事件
        Map<Integer, View.OnClickListener> clickListenerMaps = mBuilder.mOnClickListenerMaps;
        for (Map.Entry<Integer, View.OnClickListener> entry : clickListenerMaps.entrySet()) {
            View view = findViewById(entry.getKey());
            view.setOnClickListener(entry.getValue());
        }
    }

    /**
     * 将NavigationView添加到父布局
     *
     * @param navigationBarView
     * @param parent
     */
    @Override
    public void attachParent(View navigationBarView, ViewGroup parent) {
        parent.addView(navigationBarView, 0);
    }

    /**
     * 返回NavigationBar的builder
     *
     * @return
     */
    public B getBuilder() {
        return mBuilder;
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationBarView.findViewById(viewId);
    }

    /**
     * Build构建类
     * 构建Navigationbar和存储参数
     */
    public abstract static class Builder<B extends Builder> {
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public Map<Integer, CharSequence> mTextMaps;
        public Map<Integer, View.OnClickListener> mOnClickListenerMaps;

        public Builder(Context context, int layoutId, ViewGroup parent) {
            mContext = context;
            mLayoutId = layoutId;
            mParent = parent;
            mTextMaps = new HashMap<>();
            mOnClickListenerMaps = new HashMap<>();
        }

        /**
         * 用来创建Navigationbar
         *
         * @return
         */
        public abstract AbsNavigationBar create();

        public B setText(int viewId, String text) {
            mTextMaps.put(viewId, text);
            return (B) this;
        }

        public B setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            mOnClickListenerMaps.put(viewId, onClickListener);
            return (B) this;
        }
    }
}

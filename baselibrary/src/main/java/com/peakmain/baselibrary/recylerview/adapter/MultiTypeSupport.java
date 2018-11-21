package com.peakmain.baselibrary.recylerview.adapter;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/21 0021 下午 2:04
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    int getLayoutId(T item,int position);
}

package com.peakmain.gankzhihu.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 4:02
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface BaseContract {
    public interface BasePresenter<T extends BaseContract.BaseView> {
        void attachView(T view);

        void detachView();
    }

    public interface BaseView {
        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFaild(String message);

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }
}

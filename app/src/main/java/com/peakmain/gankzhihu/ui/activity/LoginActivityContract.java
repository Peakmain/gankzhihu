package com.peakmain.gankzhihu.ui.activity;

import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/13 0013 下午 3:55
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface LoginActivityContract {
    interface View extends BaseContract.BaseView {
        /**
         * 登录成功
         */
        void showLoginSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 登录用户
         *
         * @param account  用户名
         * @param password 密码
         */
        void Login(String account, String password);

    }
}
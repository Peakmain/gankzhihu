package com.peakmain.gankzhihu.ui.contract;


import com.peakmain.gankzhihu.base.BaseContract;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/20 0020 上午 11:04
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface RegisterActivityContract {
    public interface View extends BaseContract.BaseView {
        /**
         * 注册成功后业务逻辑
         */
        void showRegisterSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 注册接口
         */
        void register(String account, String password, String rePassword);
    }
}

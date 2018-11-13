package com.peakmain.gankzhihu.ui.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.wanandroid.DataResponse;
import com.peakmain.gankzhihu.bean.wanandroid.User;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.WanAndroidApi;
import com.peakmain.gankzhihu.ui.activity.LoginActivityContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/13 0013 下午 3:55
 * mail : 2726449200@qq.com
 * describe ：
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View> implements LoginActivityContract.Presenter {
    @Inject
    public LoginActivityPresenter(){

    }
    @Override
    public void Login(String account, String password) {
        mView.showLoading();

        RetrofitManager.create(WanAndroidApi.class).login(account,password)
                .compose(mView.<DataResponse<User>>bindToLife())
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> userDataResponse) throws Exception {
                        if (userDataResponse.getErrorCode()!=0){
                            //表示出错
                            mView.showFaild(userDataResponse.getErrorMsg().toString());
                        }else{
                            mView.showLoginSuccess();
                        }

                        mView.hideLoading();

                    }
                }, this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort( R.string.load_error);
    }
}


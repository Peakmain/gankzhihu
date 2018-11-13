package com.peakmain.gankzhihu.ui.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.wanandroid.DataResponse;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.WanAndroidApi;
import com.peakmain.gankzhihu.ui.contract.RegisterActivityContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/20 0020 上午 11:08
 * mail : 2726449200@qq.com
 * describe ：
 */
public class RegisterActivityImp extends BasePresenter<RegisterActivityContract.View> implements RegisterActivityContract.Presenter{
    @Inject
    public RegisterActivityImp(){

    }
    @Override
    public void register(String account, String password, String rePassword) {
           mView.showLoading();
        RetrofitManager.create(WanAndroidApi.class).register(account,password,rePassword)
                .compose(mView.bindToLife())
                .compose(RxSchedulers.applySchedulers())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode()!=0){
                            mView.showFaild(dataResponse.getErrorMsg().toString());
                        }else {
                            mView.showRegisterSuccess();
                        }
                        mView.hideLoading();
                    }
                }, this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort(R.string.load_error);
    }
}

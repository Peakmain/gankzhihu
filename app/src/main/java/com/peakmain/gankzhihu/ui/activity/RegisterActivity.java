package com.peakmain.gankzhihu.ui.activity;

import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.ui.contract.RegisterActivityContract;
import com.peakmain.gankzhihu.ui.presenter.RegisterActivityImp;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/20 0020 上午 11:00
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path = "/activity/RegisterActivity")
public class RegisterActivity extends BaseActivity<RegisterActivityImp> implements RegisterActivityContract.View{
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.repassword)
    EditText repassword;
    @BindView(R.id.regitster)
    Button regitster;

    @Override
    public void showRegisterSuccess() {
        showSuccess("注册成功,重新登录!");
        SPUtils.getInstance().put("account",account.getText().toString().trim());
        //跳转到登录页面
        ARouter.getInstance().build("/activity/LoginActivity")
                .navigation();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }
    @OnClick(R.id.regitster)
    public void onViewClick(){
        mPresenter.register(account.getText().toString().trim(),password.getText().toString().trim(),repassword.getText().toString().trim());
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}

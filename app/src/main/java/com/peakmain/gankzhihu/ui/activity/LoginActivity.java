package com.peakmain.gankzhihu.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.rx.RxBus;
import com.peakmain.gankzhihu.ui.presenter.LoginActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Function;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/10/19 0019 下午 5:35
 * mail : 2726449200@qq.com
 * describe ：
 */
@Route(path = "/activity/LoginActivity")
public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {
    @BindView(R.id.tv_al_account)
    AutoCompleteTextView tvAlAccount;
    @BindView(R.id.tv_al_password)
    EditText tvAlPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.regitster)
    TextView regitster;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.login_form)
    ScrollView loginForm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
        RxBus.getInstance().register(this);
    }

    @Override
    protected void initView() {
        String account = SPUtils.getInstance().getString("account");
        String password = SPUtils.getInstance().getString("password");
        if (!TextUtils.isEmpty(account)){
            tvAlAccount.setText(account);
        }
        if (!TextUtils.isEmpty(password)){
            tvAlPassword.setText(password);
        }
    }

    @OnClick({R.id.login, R.id.regitster})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (TextUtils.isEmpty(tvAlAccount.getText().toString()) && TextUtils.isEmpty(tvAlPassword.getText().toString())) {
                    showSuccess("用户名或密码不能为空!");
                } else {
                    mPresenter.Login(tvAlAccount.getText().toString().trim(), tvAlPassword.getText().toString().trim());
                }
                break;
            case R.id.regitster:
                ARouter.getInstance().build("/activity/RegisterActivity")
                        .navigation();
                break;
        }
    }

    @Override
    public void showLoginSuccess() {
        showSuccess("登录成功!");
        SPUtils.getInstance().put("account",tvAlAccount.getText().toString().trim());
        SPUtils.getInstance().put("password",tvAlPassword.getText().toString().trim());
        RxBus.getInstance().chainProcess(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return tvAlAccount.getText().toString().trim();
            }
        });
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
    }
}

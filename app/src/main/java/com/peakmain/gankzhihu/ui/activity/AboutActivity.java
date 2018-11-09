package com.peakmain.gankzhihu.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.TextView;

import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.base.BaseActivity;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 上午 9:31
 * mail : 2726449200@qq.com
 * describe ：
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_github)
    TextView tv_github;
    @BindView(R.id.tv_blog)
    TextView tv_blog;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initToolbar();

        tv_github.setOnClickListener(this);
        tv_blog.setOnClickListener(this);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolbar() {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("感谢支持");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_github:
                Intent it1 = new Intent(Intent.ACTION_VIEW);
                it1.setDataAndType(Uri.parse(tv_github.getText().toString()), "text/html");
                startActivity(it1);
                break;
            case R.id.tv_blog:
                Intent it2 = new Intent(Intent.ACTION_VIEW);
                it2.setDataAndType(Uri.parse(tv_blog.getText().toString()), "text/html");
                startActivity(it2);
                break;
        }
    }
}

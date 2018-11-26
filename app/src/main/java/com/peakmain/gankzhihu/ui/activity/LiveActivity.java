package com.peakmain.gankzhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.peakmain.baselibrary.navigationbar.DefaultNavigationBar;
import com.peakmain.baselibrary.recylerview.itemdecoration.DividerItemDecoration;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.LiveItemAdapter;
import com.peakmain.gankzhihu.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 下午 2:35
 * mail : 2726449200@qq.com
 * describe ：电视直播
 */
@Route(path = "/activity/LiveActivity")
public class LiveActivity extends BaseActivity {
    @BindView(R.id.ry_live)
     RecyclerView mRecyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    private String [] mUrlList = new String[]{
            "http://panda.10188.com:100/playbill/getPageList?channelId=375&playDate=2018-11-26",
            "http://220.248.175.231:6610/001/2/ch00000090990000001014/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001023/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001015/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001016/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001017/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001018/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001019/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001020/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001021/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001027/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001028/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001029/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001030/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001031/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001053/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001077/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001069/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.230:6610/001/2/ch00000090990000001047/index.m3u8?virtualDomain=001.live_hls.zte.com",
            "http://220.248.175.231:6610/001/2/ch00000090990000001081/index.m3u8?virtualDomain=001.live_hls.zte.com",
    };
    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        initToolBar();
        //1 表示一列
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        LiveItemAdapter adapter = new LiveItemAdapter(this, Arrays.asList(mUrlList));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.scrollToPosition(0);//回到第一个位置
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        ViewGroup parent = findViewById(R.id.activity_live);
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this, parent)
                .setLeftText("电视节目直播")
                .create();
        Toolbar toolbar = navigationBar.findViewById(com.peakmain.baselibrary.R.id.view_root);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://actionbar 左边箭头id
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

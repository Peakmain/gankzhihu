package com.peakmain.gankzhihu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.peakmain.baselibrary.recylerview.adapter.CommonRecyclerAdapter;
import com.peakmain.baselibrary.recylerview.adapter.OnItemClickListener;
import com.peakmain.baselibrary.recylerview.adapter.ViewHolder;
import com.peakmain.baselibrary.recylerview.itemdecoration.RecyclerGridSpaceDecoration;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/21 0021 下午 3:40
 * mail : 2726449200@qq.com
 * describe ：
 */
public class RefreshLoadActivity extends AppCompatActivity implements RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    private LoadRefreshRecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mDatas = new ArrayList<>();
        initData();

        mRecyclerView = (LoadRefreshRecyclerView) findViewById(R.id.recycler_view);
        // 添加头部和底部刷新效果
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);

        // 设置正在获取数据页面和无数据页面
        mRecyclerView.addLoadingView(findViewById(R.id.load_view));
        mRecyclerView.addEmptyView(findViewById(R.id.empty_view));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new RecyclerGridSpaceDecoration(20));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter = new HomeAdapter(RefreshLoadActivity.this, mDatas);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, 2000);



        /*mAdapter = new HomeAdapter(RefreshLoadActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);*/

        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mDatas.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
        // mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }

    protected void initData() {
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas=new ArrayList<>();
                initData();
                mRecyclerView.onStopRefresh();
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                mRecyclerView.onStopLoad();
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    class HomeAdapter extends CommonRecyclerAdapter<String> {

        public HomeAdapter(Context context, List<String> data) {
            super(context, data, R.layout.item_home);
        }

        @Override
        public void convert(ViewHolder holder, String item) {
            holder.setText(R.id.id_num, item);
        }
    }
}

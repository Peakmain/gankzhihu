package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peakmain.baselibrary.recylerview.refreshload.DefaultLoadCreator;
import com.peakmain.baselibrary.recylerview.refreshload.DefaultRefreshCreator;
import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.baselibrary.recylerview.widget.RefreshRecyclerView;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.DailyListAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.daily.DailyTimeLine;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.DailyApi;
import com.peakmain.gankzhihu.ui.contract.DailyContract;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 4:07
 * mail : 2726449200@qq.com
 * describe ：
 */
public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter {
    private Context mContext;

    @Inject
    public DailyPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

    @Override
    public void getDailyTimeLine(String num) {
        if (mView != null) {
            mView.showLoading();
            RetrofitManager.createDailyIo(DailyApi.class)
                    .getDailyTimeLine(num)
                    .compose(mView.bindToLife())
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(dailyTimeLine -> {
                        if (dailyTimeLine.getMeta().getMsg().equals("success")) {
                            mView.disPlayDailyTimeLine(dailyTimeLine);
                        }
                    }, this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

}

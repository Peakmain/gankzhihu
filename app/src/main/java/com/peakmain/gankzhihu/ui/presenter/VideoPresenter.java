package com.peakmain.gankzhihu.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.gankzhihu.adapter.VideoPageAdapter;
import com.peakmain.gankzhihu.base.BasePresenter;
import com.peakmain.gankzhihu.bean.video.VideoPageData;
import com.peakmain.gankzhihu.di.scope.ContextLife;
import com.peakmain.gankzhihu.net.RetrofitManager;
import com.peakmain.gankzhihu.net.services.VideoApi;
import com.peakmain.gankzhihu.ui.contract.VideoContract;
import com.peakmain.gankzhihu.ui.contract.VideoContract.Presenter;
import com.peakmain.gankzhihu.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:19
 * mail : 2726449200@qq.com
 * describe ：
 */
public class VideoPresenter extends BasePresenter<VideoContract.View> implements Presenter {

    private int page = 20;

    private Context mContext;
    @Inject
    public VideoPresenter(@ContextLife Context context) {
        mContext = context;
    }

    @Override
    public void getVideoData() {
        if (mView != null) {
            mView.showLoading();
            RetrofitManager.createVideoIo(VideoApi.class)
                    .getVideoData(page, "baidu", "863425026599592", "baisibudejie",
                            "5.1.1", "android", "", "862720032545006&mac=20%3A5d%3A47%3A82%3Ae2%3A7e", "7.0.8")
                    .compose(RxSchedulers.applySchedulers())
                    .compose(mView.bindToLife())
                    .subscribe(videoPageData -> {
                        mView.displayVideo(videoPageData);
                    }, this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
    }
}

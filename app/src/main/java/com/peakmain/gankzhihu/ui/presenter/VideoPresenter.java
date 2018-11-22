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
                            "4.2.2", "android", "", "98%3A6c%3Af5%3A4b%3A72%3A6d", "6.2.8")
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

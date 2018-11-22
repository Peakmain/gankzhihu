package com.peakmain.gankzhihu.ui.contract;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakmain.baselibrary.recylerview.widget.LoadRefreshRecyclerView;
import com.peakmain.gankzhihu.base.BaseContract;
import com.peakmain.gankzhihu.bean.video.VideoPageData;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:19
 * mail : 2726449200@qq.com
 * describe ：
 */
public class VideoContract {
    public interface View extends BaseContract.BaseView{
        void displayVideo(VideoPageData videoPageData);
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
          void getVideoData();
    }
}

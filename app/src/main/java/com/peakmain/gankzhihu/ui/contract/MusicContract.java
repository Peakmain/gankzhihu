package com.peakmain.gankzhihu.ui.contract;

import com.peakmain.gankzhihu.base.BaseContract;
import com.peakmain.gankzhihu.bean.video.MediaItem;
import com.peakmain.gankzhihu.bean.video.VideoPageData;

import java.util.List;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:19
 * mail : 2726449200@qq.com
 * describe ：
 */
public class MusicContract {
    public interface View extends BaseContract.BaseView{
        void displayMusic(List<MediaItem> mediaItems);
    }
    public interface Presenter extends BaseContract.BasePresenter<View>{
        void getMusicDataFromLocal();
    }
}

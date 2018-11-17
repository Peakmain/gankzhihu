package com.peakmain.gankzhihu.net.services;

import com.peakmain.gankzhihu.bean.jandan.JokeBean;
import com.peakmain.gankzhihu.net.Constant;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/16 0016 下午 5:38
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface JokeApi {
    /**
     * 段子接口
     * http://i.jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments&page=1
     */
    //获取段子
    @GET(Constant.JOKE_BASE_URL)
    Observable<JokeBean> getDetailData(@Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                       @Query("page") int page
    );
}

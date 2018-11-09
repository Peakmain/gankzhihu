package com.peakmain.gankzhihu.net.services;

import com.peakmain.gankzhihu.bean.daily.DailyTimeLine;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:37
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface DailyApi {
    @GET("homes/index/{num}.json")
    Observable<DailyTimeLine> getDailyTimeLine(@Path("num") String num);

    @GET("options/index/{id}/{num}.json")
    Observable<DailyTimeLine> getDailyFeedDetail(@Path("id") String id,@Path("num") String num);
}

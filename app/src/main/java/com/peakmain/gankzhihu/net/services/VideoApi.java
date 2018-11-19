package com.peakmain.gankzhihu.net.services;

import com.peakmain.gankzhihu.bean.video.VideoPageData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 上午 10:23
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface VideoApi {
    @GET("0-{page}.json")
    Observable<VideoPageData> getVideoData(@Path("page")int page,@Query("market") String market, @Query("udid") String udid,
                                           @Query("appname") String appname, @Query("os") String os,
                                           @Query("client") String client, @Query("visiting") String visiting,
                                           @Query("mac") String mac, @Query("ver") String ver);
}

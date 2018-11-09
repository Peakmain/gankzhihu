package com.peakmain.gankzhihu.net.services;

import com.peakmain.gankzhihu.bean.gank.GankData;
import com.peakmain.gankzhihu.bean.gank.Meizhi;
import com.peakmain.gankzhihu.bean.gank.Video;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:42
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface GankApi {

    @GET("data/福利/10/{page}")
    Observable<Meizhi> getMeizhiData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("data/休息视频/10/{page}")
    Observable<Video> getVideoData(@Path("page") int page);
}

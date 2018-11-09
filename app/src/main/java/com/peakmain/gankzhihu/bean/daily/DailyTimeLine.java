package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:37
 * mail : 2726449200@qq.com
 * describe ：
 */

public class DailyTimeLine implements Serializable {

    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }
    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "DailyTimeLine{" +
                "meta=" + meta +
                ", response=" + response +
                '}';
    }
}

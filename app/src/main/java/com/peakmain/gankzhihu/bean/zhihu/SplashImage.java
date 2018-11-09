package com.peakmain.gankzhihu.bean.zhihu;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:45
 * mail : 2726449200@qq.com
 * describe ：
 */


public class SplashImage implements Serializable {

    private String text;//图片出处
    private String img;//图片地址

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "SplashImage{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}

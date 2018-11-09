package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:40
 * mail : 2726449200@qq.com
 * describe ：
 */
public class Category implements Serializable {

    private String image_lab;
    private String title;

    public String getImage_lab() {
        return image_lab;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "image_lab='" + image_lab + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
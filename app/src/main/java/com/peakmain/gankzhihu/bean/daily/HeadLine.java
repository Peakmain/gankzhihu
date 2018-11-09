package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:41
 * mail : 2726449200@qq.com
 * describe ：
 */
public class HeadLine implements Serializable {

    private String description;
    private String title;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "HeadLine{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
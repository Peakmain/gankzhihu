package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:40
 * mail : 2726449200@qq.com
 * describe ：
 */
public class Post implements Serializable {

    private String appview;
    private String title;
    private String id;
    private String description;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public String getAppview() {
        return appview;
    }

    public void setAppview(String appview) {
        this.appview = appview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Post{" +
                "appview='" + appview + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}

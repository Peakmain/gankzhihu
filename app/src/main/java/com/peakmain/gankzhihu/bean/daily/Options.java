package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:41
 * mail : 2726449200@qq.com
 * describe ：
 */
public class Options implements Serializable {

    private String content;

    private Author author;

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public class Author implements Serializable {
        private String avatar;
        private String name;

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "avatar='" + avatar + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Options{" +
                "content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}

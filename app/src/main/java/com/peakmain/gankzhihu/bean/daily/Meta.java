package com.peakmain.gankzhihu.bean.daily;

import java.io.Serializable;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/7 0007 下午 5:38
 * mail : 2726449200@qq.com
 * describe ：
 */
public class Meta implements Serializable {

    private String msg;
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
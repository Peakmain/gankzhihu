package com.peakmain.gankzhihu.rx;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/19 0019 下午 2:14
 * mail : 2726449200@qq.com
 * describe ：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterBus {
}

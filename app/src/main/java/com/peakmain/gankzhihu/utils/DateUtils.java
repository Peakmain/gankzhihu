package com.peakmain.gankzhihu.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 2:00
 * mail : 2726449200@qq.com
 * describe ：日期工具类
 */

public class DateUtils {

    public static boolean isSameDate(Date date1, Date date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(date2);

        return cal.get(Calendar.DAY_OF_YEAR) == selectedDate.get(Calendar.DAY_OF_YEAR);
    }

}

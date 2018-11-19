package com.peakmain.gankzhihu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/9 0009 下午 2:00
 * mail : 2726449200@qq.com
 * describe ：日期工具类
 */

public class DateUtils {
    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    public static boolean isSameDate(Date date1, Date date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(date2);

        return cal.get(Calendar.DAY_OF_YEAR) == selectedDate.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * 获取目标时间和当前时间之间的差距
     *
     * @param date
     * @return
     */
    public static String getTimestampString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();
        if (splitTime < (30 * ONE_DAY)) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚";
            }
            if (splitTime < ONE_HOUR) {
                return String.format("%d分钟前", splitTime / ONE_MINUTE);
            }

            if (splitTime < ONE_DAY) {
                return String.format("%d小时前", splitTime / ONE_HOUR);
            }

            return String.format("%d天前  %s", splitTime / ONE_DAY,date2HHmm(date));
        }
        String result;
        result = "M月d日 HH:mm";
        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
    }
    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * String 转换 Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    private static StringBuilder mFormatBuilder;
    private static Formatter mFormatter;
    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}

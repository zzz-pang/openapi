package com.play.openapi.gateway.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    /**
     * 返回指定格式的日期
     *
     * @param strDate 字符串日期
     * @param pattern 返回日期样式
     */

    public static Date getDate(String strDate, String pattern) {
        if (strDate == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(strDate.trim());
        } catch (ParseException ex) {
            return null;
        }
    }


    /**
     * 提交URL访问时间和服务器接收到请求的时间比较，验证链接是否超时
     * 开始日期小于等于结束日期为True,反之False
     *
     * @param visitTime  服务器获得商家请求的时间
     * @param serverTime 服务器接收日期
     */
    public static boolean compareTimeStamp(Date visitTime, Date serverTime) {
        Calendar visitCalendar = Calendar.getInstance();
        Calendar serverCalendar = Calendar.getInstance();
        visitCalendar.setTime(visitTime);
        serverCalendar.setTime(serverTime);
        int result = serverCalendar.compareTo(visitCalendar);
        if (result == 0 || result == -1)
            return true;
        else
            return false;


    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @interval 设置间隔天数
     * @type Calendar.DATE(1) or Calendar.MONTH(2) or Calendar.YEAR(5)orCalendar.MINUTE(12)
     */

    public static Date addDate(Date date, int interval, int type) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, interval);
        return calendar.getTime();

    }


    /**
     * 获取前三个月的日期
     *
     * @return
     */
    public static String getBeforeThreeMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        return format.format(calendar.getTime());
    }

    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 将date 日期格式化        *
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String fomartDate(Date date) throws Exception {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Z"));
        return sdf.format(date);
    }

    /**
     * 将date 日期格式化        *
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String getFomartDate(Date date) throws Exception {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 返回指定格式的日期字符串
     *
     * @param date    转换日期
     * @param pattern 日期格式
     */
    public static String getDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
    }

    /**
     * 获取相差的描述
     * @param startTime 开始日期
     * @param endDate  结束日期
     * @return
     */
    public static long getSecondsDiffer(Date startTime,Date endDate){
        long st=startTime.getTime();
        long ed=endDate.getTime();
        return (ed-st)/1000;
    }

}

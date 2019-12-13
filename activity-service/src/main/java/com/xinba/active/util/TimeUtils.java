package com.xinba.active.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @createBy XiaoWu
 * @date 2019/11/19 9:34
 */
public class TimeUtils {

    // 将时间转换成标准的时间格式 yyyy-MM-dd HH:mm:ss
    public static String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    // 将标准时间字符串转发成 date类型
    public static Date strToDate(String dayStr) throws ParseException {
        String trim = dayStr.trim();
        if(10 == trim.length()){
            trim = trim + " 00:00:00";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(trim);
    }

    // 获取一天的 开始时刻
    public static Date getOneDayStartTime(Date date){
        Calendar oneDayStart = Calendar.getInstance();
        oneDayStart.setTime(date);
        oneDayStart.set(Calendar.HOUR_OF_DAY, 0);
        oneDayStart.set(Calendar.MINUTE, 0);
        oneDayStart.set(Calendar.SECOND, 0);
        oneDayStart.set(Calendar.MILLISECOND, 0);
        return oneDayStart.getTime();
    }

    // 获取一天的 结束时刻
    public static Date getOneDayEndTime(Date date){
        Calendar oneDayEnd = Calendar.getInstance();
        oneDayEnd.setTime(date);
        oneDayEnd.set(Calendar.HOUR_OF_DAY, 23);
        oneDayEnd.set(Calendar.MINUTE, 59);
        oneDayEnd.set(Calendar.SECOND, 59);
        oneDayEnd.set(Calendar.MILLISECOND, 999);
        return oneDayEnd.getTime();
    }

    // 获取前一天的日期
    public static Date getPreviousDay(Date date){
        return getPreviousDay(date, 1);
    }

    public static Date getPreviousDay(Date date,int previous){
        Calendar previousDay = Calendar.getInstance();
        previousDay.setTime(date);
        previousDay.add(Calendar.DAY_OF_YEAR, -previous);
        return previousDay.getTime();
    }

    // 获取后一天的日期
    public static Date getNextDay(Date date){
        return getNextDay(date, 1);
    }

    public static Date getNextDay(Date date, int next){
        Calendar nextDay = Calendar.getInstance();
        nextDay.setTime(date);
        nextDay.add(Calendar.DAY_OF_YEAR, next);
        return nextDay.getTime();
    }

    // 计算开始时间 和结束时间，间隔多少日
    // 依旧利用 日历类
    public static int calBetweenDays(Date startTime, Date endTime){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);
        // 一共显示多少天 一周计算 是6天 必须加1
        return endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR) + 1;
    }

    // 获取时间 前15天的开始时和结束时
    public static Map getBeforeDaysStratAndEnd(Date date){
        // 新建map存储对象
        Map<String,String> map = new HashMap<>();
        // 结束时间
        map.put(TimeConsts.END_TIME_KEY,TimeUtils.dateToStr(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 这里只是简单相减
        // 显示十五天，就只减14天
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        Date endTime = calendar.getTime();
        // 开始时间
        map.put(TimeConsts.START_TIME_KEY, TimeUtils.dateToStr(endTime));

        return map;
    }

    // 获取时间 所在周的开始时和结束时
    public static Map getWeekStartAndEnd(Date date){
        // 新建map存储对象
        Map<String,String> map = new HashMap<>();
        int mondayPlus = getMondayPlus(date);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        // 计算 周一的日期
        gregorianCalendar.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = gregorianCalendar.getTime();
        Date mondayStartTime = getOneDayStartTime(monday);
        String mondayStr = dateToStr(mondayStartTime);
        map.put(TimeConsts.START_TIME_KEY, mondayStr);

        // 计算 周日的日期
        gregorianCalendar.add(GregorianCalendar.DATE, 6);
        Date sunday = gregorianCalendar.getTime();
        Date sundayEndTime = getOneDayEndTime(sunday);
        String sundayStr = dateToStr(sundayEndTime);
        map.put(TimeConsts.END_TIME_KEY, sundayStr);

        return map;
    }

    // 获得日期 与本周一相差的天数
    private static int getMondayPlus(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 1){
            return -6;
        }
        return 2 - dayOfWeek;
    }

    // 获取时间 所在天的开始时和结束时
    public static Map getDayStartAndEnd(Date date){
        Map<String,String> map = new HashMap<>();
        return null;
    }

    // 获取时间 所在月的开始时和结束时
    public static Map getMonthStartAndEnd(Date date){
        Map<String,String> map = new HashMap<>();
        return null;
    }

    // 获取时间 所在年的开始时和结束时
    public static Map getYearStartAndEnd(Date date){
        Map<String,String> map = new HashMap<>();
        return null;
    }
}

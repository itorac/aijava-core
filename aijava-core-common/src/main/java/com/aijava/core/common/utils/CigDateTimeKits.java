package com.aijava.core.common.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.aijava.core.common.utils.constants.TimeKitsConstants.*;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName CigDateTimeKits.java
 * @Description todo
 * @createTime 2022 年04 月19 日 12:50
 */
public class CigDateTimeKits {
    /**
     * 当前时间转换为时间戳
     *
     * @param localDateTime 当前日期时间
     * @return 时间戳
     */
    public static Long toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return localDateTime.toEpochSecond(CHINA_STANDARD_ZONE);
    }

    /**
     * 当前日期转换为时间戳
     *
     * @param localDate 当前日期
     * @return 时间戳
     */
    public static Long toTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return localDate.atStartOfDay(CHINA_STANDARD_ZONE).toEpochSecond();
    }

    /**
     * LocalDateTime 转换成 Date 类型
     *
     * @param localDateTime 当前日期时间
     * @return java.util.Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        ZonedDateTime zonedDateTime = localDateTime.atZone(CHINA_STANDARD_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate 转换成 Date 类型
     *
     * @param localDate 当前日期
     * @return java.util.Date
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(CHINA_STANDARD_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 转换为标准时间的字符串
     *
     * @param localDateTime 当前时间
     * @return 标准时间类型字符串: yyyy-MM-dd HH:mm:ss
     */
    public static String toStandardTimeString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(STANDARD_TIME_FORMAT));
    }

    /**
     * 转换为标准时间的字符串
     *
     * @param localDate 当前日期
     * @return 标准时间类型字符串: yyyy-MM-dd HH:mm:ss
     */
    public static String toStandardTimeString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return localDate.format(DateTimeFormatter.ofPattern(STANDARD_TIME_FORMAT));
    }

    /**
     * 转换成日期时间
     *
     * @param text 时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parseLocalDateTime(CharSequence text) {
        if (text == null) {
            return null;
        }

        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(STANDARD_TIME_FORMAT));
    }

    /**
     * 转换成日期时间
     *
     * @param timestamp 时间戳
     * @return 日期时间
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null || timestamp == 0) {
            return null;
        }

        return Instant.ofEpochMilli(timestamp).atZone(CHINA_STANDARD_ZONE).toLocalDateTime();
    }

    /**
     * 转换成日期类型
     *
     * @param timestamp 时间戳
     * @return 日期
     */
    public static LocalDate toLocalDate(Long timestamp) {
        if (timestamp == null || timestamp == 0) {
            return null;
        }

        return Instant.ofEpochMilli(timestamp).atZone(CHINA_STANDARD_ZONE).toLocalDate();
    }

    /**
     * 时间字符串转换为Date
     *
     * @param timeString
     * @return
     */
    public static Date timeStringToDate(String timeString) {
        LocalDate localDate = LocalDate.parse(timeString, DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT));
        return Date.from(localDate.atStartOfDay(CHINA_STANDARD_ZONE).toInstant());
    }

    /**
     * 根据指定格式，转换时间字符串
     *
     * @param timeString
     * @param timeFormatString
     * @return
     */
    public static Date timeStringToData(String timeString, String timeFormatString) {

        if ("".equals("" + timeString)) {
            return null;
        }
        if (timeFormatString == null) {
            timeFormatString = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormatString, Locale.CHINA);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(timeString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    /**
     * 获取今日日期
     *
     * @param formatter yyyyMMdd
     * @return
     */
    public static String getTodayDateStr(String formatter) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(calendar.getTime());
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }

    /**
     * @param date 传递的时间  day 需要加的天数  如果需要减一天的话 day值为负数
     * @return
     * @autor zhangyijian
     * @desctibe 给传递的时候加一天
     */
    public static Date addDays(String date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(CigDateTimeKits.timeStringToDate(date));
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 判断两个时间的差是否超过24小时
     */
    public static boolean judgeDateIn24Hours(Date startTime, Date endTime) {
        long judgeLong = endTime.getTime() - startTime.getTime();
        if (judgeLong < 0) {
            return false;
        }
        double result = judgeLong * 1.0 / (1000 * 60 * 60);
        return result <= 24;
    }
}

package com.aijava.core.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author  zhangyijian
 * @deprecated 日期工具类📅
 * @date 2022-03-02
 */
public class DateTimeUtils {

    /**
     * @author zhangyijian
     * @deprecated todo 获取当前时间到第二天凌晨的秒时间差
     * @return
     */
    public static Long getSecondNextMorning(){
        LocalDateTime localDateTime = LocalDateTime.now();
        long l1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
        LocalDate localDate1 = LocalDate.now().plusDays(1);
        LocalDateTime localDateTime1 = localDate1.atStartOfDay();
        long milli = localDateTime1.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
        return (milli - l1) / 1000;
    }
}

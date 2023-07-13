package com.aijava.core.common.utils.constants;

import java.time.ZoneOffset;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName TimeKitsConstants.java
 * @Description 时间转换工具使用定义的常量
 * @createTime 2022 年04 月19 日 12:51
 */
public class TimeKitsConstants {
    /**
     * 中国标准时区-北京东八区
     */
    public static final ZoneOffset CHINA_STANDARD_ZONE = ZoneOffset.of("+8");
    /**
     * 标准时间转换格式：    yyyy-MM-dd HH:mm:ss
     * eg: 2021-12-11 19:30:01
     */
    public static final String STANDARD_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 标准日期转换格式：    yyyy-MM-dd
     * eg: 2021-12-11
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_DAY_HOUR_MIN_SECOND_FORMAT = "MMddHHmmsss";

    public static final Integer WEEK_NUM = 7;

    public static final Integer MOTH_NUM = 30;
}

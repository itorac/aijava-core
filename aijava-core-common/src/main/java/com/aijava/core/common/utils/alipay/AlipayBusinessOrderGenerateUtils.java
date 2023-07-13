package com.aijava.core.common.utils.alipay;

import com.aijava.core.common.utils.CigDateTimeKits;
import com.aijava.core.common.utils.alipay.enums.AlipayBusinessTypeEnum;
import com.aijava.core.common.utils.constants.TimeKitsConstants;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AlipayBusinessOrderGenerateUtils.java
 * @Description 支付宝业务订单号生成工具
 * @createTime 2022 年04 月19 日 12:48
 */
public class AlipayBusinessOrderGenerateUtils {
    /**
     * 业务前缀,以今年作为前缀
     */
    private static final String BUSINESS_ORDER_CODE_PREFIX = "2022";

    /**
     * 根据业务类型，随机获取当前业务对应的订单号码
     *
     * @param businessTypeEnum
     * @return
     */
    public static String generateNewBusinessOrderCode(AlipayBusinessTypeEnum businessTypeEnum) {
        return BUSINESS_ORDER_CODE_PREFIX + businessTypeEnum.getCode() + CigDateTimeKits.getTodayDateStr(TimeKitsConstants.TIME_DAY_HOUR_MIN_SECOND_FORMAT);
    }
}

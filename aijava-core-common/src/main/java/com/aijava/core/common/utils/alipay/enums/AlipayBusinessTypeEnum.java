package com.aijava.core.common.utils.alipay.enums;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AlipayBusinessTypeEnum.java
 * @Description 支付宝业务类型枚举
 * @createTime 2022 年04 月08 日 19:40
 */
public enum AlipayBusinessTypeEnum {

    /**
     * ISV代创建小程序
     */
    ISV_CREATE_MINI_APP("952701"),
    /**
     * 服务提报
     */
    SERVICE_APPLY_MINI_APP("952702");

    private String code;

    AlipayBusinessTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

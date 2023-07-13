package com.aijava.core.security.alipay;

import com.alipay.api.AlipayClient;
import org.springframework.stereotype.Component;

/**
 * @author zhangyijian
 * @describe 策略抽象类
 */
@Component
public abstract class AliPayClientStrategy {
    /**
     * 小程序Id
     * @return int 返回当前操作数
     */
    public abstract String appId();

    /**
     * 不同的小程序Id 获取不同的client
     * @return MissionRewardReceiveVO 返回金额和数量
     */
    public abstract AlipayClient client();


    /**
     * TODO 获取支付宝公钥
     * @Author zhangyijian
     * @Date 2022/4/6 8:47 PM
     * @return String 返回支付宝公钥
     **/
    public abstract String alipayPublicKey();

    /**
     * TODO 获取支付宝解密key
     * @Author zhangyijian
     * @Date 2022/4/6 8:47 PM
     * @return String 返回支付宝加解密key
     **/
    public abstract String decrypt_key();
}

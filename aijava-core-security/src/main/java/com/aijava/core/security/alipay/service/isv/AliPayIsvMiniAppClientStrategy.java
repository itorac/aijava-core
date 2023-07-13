package com.aijava.core.security.alipay.service.isv;

import com.aijava.core.security.alipay.AliPayClientStrategy;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.aijava.core.security.alipay.constant.AliPayIsvClientConstant;
import org.springframework.stereotype.Component;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AliPayIsvMiniAppClientStrategy.java
 * @Description 支付宝三方ISV客户端初始化
 * @createTime 2022 年04 月12 日 14:03
 */
@Component
public class AliPayIsvMiniAppClientStrategy extends AliPayClientStrategy {

    public AlipayClient alipayClient;

    /**
     * 小程序Id
     *
     * @return int 返回当前操作数
     */
    @Override
    public String appId() {
        return AliPayIsvClientConstant.APPID;
    }

    /**
     * 不同的小程序Id 获取不同的client
     *
     * @return MissionRewardReceiveVO 返回金额和数量
     */
    @Override
    public AlipayClient client() {
        return this.alipayClient = new DefaultAlipayClient(
                AliPayIsvClientConstant.URL,
                AliPayIsvClientConstant.APPID,
                AliPayIsvClientConstant.APP_PRIVATE_KEY,
                AliPayIsvClientConstant.FORMAT,
                AliPayIsvClientConstant.CHARSET,
                AliPayIsvClientConstant.ALIPAY_PUBLIC_KEY,
                AliPayIsvClientConstant.SIGN_TYPE);
    }

    /**
     * TODO 获取支付宝公钥
     *
     * @return String 返回支付宝公钥
     * @Author zhangyijian
     * @Date 2022/4/6 8:47 PM
     **/
    @Override
    public String alipayPublicKey() {
        return AliPayIsvClientConstant.ALIPAY_PUBLIC_KEY;
    }

    /**
     * TODO 获取支付宝解密key
     *
     * @return String 返回支付宝加解密key
     * @Author zhangyijian
     * @Date 2022/4/6 8:47 PM
     **/
    @Override
    public String decrypt_key() {
        return AliPayIsvClientConstant.DECRYPT_KEY;
    }
}

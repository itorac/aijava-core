package com.aijava.core.security.alipay.service.mh;


import com.aijava.core.security.alipay.AliPayClientStrategy;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.aijava.core.security.alipay.constant.ALiPayMHConstant;

/**
 * 看直播任务奖励领取类
 * @author zhangyijian
 */
@Component
public class XBMHMiniAppClientStrategy extends AliPayClientStrategy {

    public AlipayClient alipayClient;

    @Override
    public String appId() {
        return ALiPayMHConstant.APPID;
    }

    @Override
    public AlipayClient client() {
        return this.alipayClient = new DefaultAlipayClient(ALiPayMHConstant.URL, ALiPayMHConstant.APPID, ALiPayMHConstant.APP_PRIVATE_KEY,
                ALiPayMHConstant.FORMAT, ALiPayMHConstant.CHARSET, ALiPayMHConstant.ALIPAY_PUBLIC_KEY, ALiPayMHConstant.SIGN_TYPE);
    }

    @Override
    public String alipayPublicKey() {
        return ALiPayMHConstant.ALIPAY_PUBLIC_KEY;
    }

    @Override
    public String decrypt_key() {
        return ALiPayMHConstant.DECRYPT_KEY;
    }
}

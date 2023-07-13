package com.aijava.core.security.alipay.service.wm;

import com.aijava.core.security.alipay.AliPayClientStrategy;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.aijava.core.security.alipay.constant.ALiPayWMConstant;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhangyijian
 */
@Component
public class WMJYMiniAppClientStrategy extends AliPayClientStrategy {

    public AlipayClient alipayClient;

    @Override
    public String appId() {
        return ALiPayWMConstant.APPID;
    }

    @Override
    public AlipayClient client() {
        return this.alipayClient = new DefaultAlipayClient(ALiPayWMConstant.URL, ALiPayWMConstant.APPID, ALiPayWMConstant.APP_PRIVATE_KEY,
                ALiPayWMConstant.FORMAT, ALiPayWMConstant.CHARSET, ALiPayWMConstant.ALIPAY_PUBLIC_KEY, ALiPayWMConstant.SIGN_TYPE);
    }

    @Override
    public String alipayPublicKey() {
        return ALiPayWMConstant.ALIPAY_PUBLIC_KEY;
    }

    @Override
    public String decrypt_key() {
        return ALiPayWMConstant.DECRYPT_KEY;
    }
}

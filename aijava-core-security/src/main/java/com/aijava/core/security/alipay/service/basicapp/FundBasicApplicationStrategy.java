package com.aijava.core.security.alipay.service.basicapp;

import com.aijava.core.security.alipay.AliPayClientStrategy;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.aijava.core.security.alipay.constant.basicapp.AliPayFundBasicApplicationConstant;
import org.springframework.stereotype.Component;

/**
 * @descriptions: 微秒纪元资金基础应用
 * @author: zhangyijian
 * @date: 2022/10/14 4:53 PM
 * @version: 1.0
 */
@Component
public class FundBasicApplicationStrategy extends AliPayClientStrategy {

    public AlipayClient alipayClient;
    @Override
    public String appId() {
        return AliPayFundBasicApplicationConstant.APPID;
    }

    @Override
    public AlipayClient client() {
        CertAlipayRequest alipayConfig = new CertAlipayRequest();
        alipayConfig.setPrivateKey(AliPayFundBasicApplicationConstant.APP_PRIVATE_KEY);
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(AliPayFundBasicApplicationConstant.APPID);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        alipayConfig.setEncryptor("");
        alipayConfig.setFormat("json");
        alipayConfig.setCertPath("/opt/crt/appCertPublicKey_2021003154676536.crt");
        alipayConfig.setAlipayPublicCertPath("/opt/crt/alipayCertPublicKey_RSA2.crt");
        alipayConfig.setRootCertPath("/opt/crt/alipayRootCert.crt");
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return alipayClient;
    }

    @Override
    public String alipayPublicKey() {
        return null;
    }

    @Override
    public String decrypt_key() {
        return AliPayFundBasicApplicationConstant.DECRYPT_KEY;
    }
}

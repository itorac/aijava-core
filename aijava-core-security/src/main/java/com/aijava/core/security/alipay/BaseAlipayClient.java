package com.aijava.core.security.alipay;

import javax.annotation.Resource;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.aijava.core.security.alipay.bean.AliappConfig;
import com.aijava.core.security.alipay.bean.CigAlipayClient;

/**
 * @ClassName: BaseAlipayClient
 * @Description: 支付宝客户端
 * @author xiegr
 * @date 2022-04-18 11:53:55
 */
public abstract class BaseAlipayClient {

	@Resource
	private CigAlipayClient cigAlipayClient;

	/**
	 * @Title: getAlipayClient
	 * @Description: 根据mallId获取支付宝客户端,一般用于商户端
	 * @param aliappConfig
	 * @return
	 * @author xiegr
	 * @date 2022-04-15 06:08:18
	 */
	public AlipayClient getAlipayClient(Long mallId) {
		AliappConfig aliappConfig = getAliappConfig(mallId);
		return new DefaultAlipayClient(aliappConfig.GATEWAY_URL, aliappConfig.getAppId(),
				aliappConfig.getAppPrivateKey(), aliappConfig.FORMAT, aliappConfig.CHARSET,
				aliappConfig.getAlipayPublicKey(), aliappConfig.SIGN_TYPE);
	}

	/**
	 * @Title: getAlipayClient
	 * @Description: 根据商户appid获取三方AlipayClient和商户appauthtoken,适用于所有平台
	 * @param appId
	 * @return
	 * @author xiegr
	 * @date 2022-04-20 05:51:05
	 */
	public CigAlipayClient getAlipayClient(String appId) {
		AliappConfig aliappConfig = getAliappConfig(appId);
		AlipayClient alipayClient = new DefaultAlipayClient(aliappConfig.GATEWAY_URL, aliappConfig.getAppId(),
				aliappConfig.getAppPrivateKey(), aliappConfig.FORMAT, aliappConfig.CHARSET,
				aliappConfig.getAlipayPublicKey(), aliappConfig.SIGN_TYPE);
		cigAlipayClient.setAlipayClient(alipayClient);
		cigAlipayClient.setAppAuthToken(aliappConfig.getAppAuthToken());
		cigAlipayClient.setPId(aliappConfig.getPId());
		return cigAlipayClient;
	}

	/**
	 * @Title: getAlipayEncryptClient
	 * @Description: 根据商户appid获取三方AlipayClient和商户appauthtoken,带内容ENCRYPY_KEY加密,适用于所有平台
	 * @param appId
	 * @return
	 * @author xiegr
	 * @date 2022-04-20 05:53:45
	 */
	public CigAlipayClient getAlipayEncryptClient(String appId) {
		AliappConfig aliappConfig = getAliappConfig(appId);
		AlipayClient alipayClient = new DefaultAlipayClient(aliappConfig.GATEWAY_URL, aliappConfig.getAppId(),
				aliappConfig.getAppPrivateKey(), aliappConfig.FORMAT, aliappConfig.CHARSET,
				aliappConfig.getAlipayPublicKey(), aliappConfig.SIGN_TYPE, aliappConfig.getEncryptKey(),
				aliappConfig.ENCRYPY_TYPE);
		cigAlipayClient.setAlipayClient(alipayClient);
		cigAlipayClient.setAppAuthToken(aliappConfig.getAppAuthToken());
		cigAlipayClient.setPId(aliappConfig.getPId());
		return cigAlipayClient;
	}

	/**
	 * @Title: getAlipayPublicKey
	 * @Description: 获取支付宝公钥
	 * @param appId
	 * @return
	 * @author xiegr
	 * @date 2022-04-18 05:56:14
	 */
	public String getAlipayPublicKey(String appId) {
		return getAliappConfig(appId).getAlipayPublicKey();
	}

	/**
	 * @Title: getEncryptKey
	 * @Description: 获取接口内容AES密钥
	 * @param appId
	 * @return
	 * @author xiegr
	 * @date 2022-04-18 05:56:24
	 */
	public String getEncryptKey(String appId) {
		return getAliappConfig(appId).getEncryptKey();
	}

	/**
	 * @Title: getAliappConfig
	 * @Description: 根据mallId获取AliappConfig
	 * @return
	 * @author xiegr
	 * @date 2022-04-15 06:12:11
	 */
	public abstract AliappConfig getAliappConfig(Long mallId);

	/**
	 * @Title: getAliappConfig
	 * @Description: 根据appid获取AliappConfig
	 * @param appId
	 * @return
	 * @author xiegr
	 * @date 2022-04-18 03:19:23
	 */
	public abstract AliappConfig getAliappConfig(String appId);

}

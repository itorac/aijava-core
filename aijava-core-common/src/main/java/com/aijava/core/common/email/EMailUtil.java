package com.aijava.core.common.email;

import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: EMailUtil
 * @Description:发邮件
 * @author xiegr
 * @date 2022-01-19 05:06:53
 */
public class EMailUtil {

	private static Logger logger = LoggerFactory.getLogger(EMailUtil.class);

	/**
	 * hostname
	 */
	private static final String HOSTNAME = "smtp.exmail.qq.com";
	/**
	 * 端口号
	 */
	private static final int SMTPPORT = 25;
	/**
	 * 字符集编码
	 */
	private static final String CHARSET = "UTF-8";
	/**
	 * 发件人邮箱
	 */
	private static final String FROM = "发件人邮箱";
	/**
	 * password
	 * 
	 * 密码
	 */
	private static final String PWD = "密码";

	/**
	 * 发送邮件方法
	 *
	 * @param subject
	 * @param content
	 * @param receivers
	 * @throws EmailException
	 */
	public static String send(String subject, String content, String... receivers) {
		String messageId = null;
		try {
			Email email = new SimpleEmail();
			email.setHostName(HOSTNAME);
			email.setSmtpPort(SMTPPORT);
			email.setAuthenticator(new DefaultAuthenticator(FROM, PWD));
			email.setSSLOnConnect(false);
			email.setCharset(CHARSET);
			email.setFrom(FROM);
			email.setSubject(subject);
			email.setMsg(content);
			email.addTo(receivers);
			messageId = email.send();
			logger.info("{} 邮件发送成功,messageId:{}", subject, messageId);
			return messageId;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 邮件发送失败,messageId:{}", subject, messageId);
		}
		return null;
	}

	/**
	 * 发送html文件
	 * 
	 * @Title: htmlSend
	 * @Description:html发送
	 * @param subject
	 * @param content
	 * @param receivers
	 * @return 设定文件 String 返回类型
	 *
	 */
	public static String htmlSend(String subject, String content, String... receivers) {
		String messageId = null;
		try {
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setHostName(HOSTNAME);
			htmlEmail.setSmtpPort(SMTPPORT);
			htmlEmail.setAuthenticator(new DefaultAuthenticator(FROM, PWD));
			htmlEmail.setSSLOnConnect(false);
			htmlEmail.setCharset(CHARSET);
			htmlEmail.setFrom(FROM);
			htmlEmail.setSubject(subject);
			htmlEmail.setHtmlMsg(content);
			htmlEmail.addTo(receivers);
			messageId = htmlEmail.send();
			logger.info("{} 邮件发送成功,messageId:{}", subject, messageId);
			return messageId;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 邮件发送失败,messageId:{}", subject, messageId);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		htmlSend("新邮箱测试发送html邮件", "<html><body><h1>hello,用新邮箱测试" + new Date() + "</h1></body></html>", "收件箱");
	}

}

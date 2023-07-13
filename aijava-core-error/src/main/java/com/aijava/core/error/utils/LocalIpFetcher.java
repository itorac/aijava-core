package com.aijava.core.error.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 
 * @ClassName: LocalIpFetcher
 * @Description: 获取ip地址
 * @author xiegr
 * @date 2021-11-30 10:03:39
 */
public class LocalIpFetcher {

	/**
	 * 
	 * @Title: fetchLocalIP
	 * @Description: 获取本地ip
	 * @return
	 * @author xiegr
	 * @date 2021-11-30 10:03:53
	 */
	public static String fetchLocalIP() {
		String localIP = "127.0.0.1";
		DatagramSocket sock = null;
		try {
			SocketAddress socket_addr = new InetSocketAddress(InetAddress.getByName("1.2.3.4"), 1);
			sock = new DatagramSocket();
			sock.connect(socket_addr);

			localIP = sock.getLocalAddress().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sock != null) {
				sock.disconnect();
				sock.close();
				sock = null;
			}
		}
		return localIP;
	}
}

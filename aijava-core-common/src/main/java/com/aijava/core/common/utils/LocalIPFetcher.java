package com.aijava.core.common.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 
 * @ClassName: LocalIPFetcher
 * @Description: 获取ip
 * @author xiegr
 * @date 2021-12-17 09:31:04
 */
public class LocalIPFetcher {

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

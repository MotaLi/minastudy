package com.eric.mina.test.server.chapter9;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class ImageServer {
	public static final int PORT = 33789;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ImageServerIoHandler handler = new ImageServerIoHandler();
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new ImageCodecFactory(false)));
		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("server is listenig at port " + PORT);
	}

}

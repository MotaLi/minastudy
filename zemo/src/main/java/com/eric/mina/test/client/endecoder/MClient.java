package com.eric.mina.test.client.endecoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eric.mina.test.server.endecoder.CmccSipcCodecFactory;

public class MClient {
	public MClient() {
		init();
	}
	// first simple example
	public void init() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		
//		connector.getFilterChain().addLast(
//				"codec",
//				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
//						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
//						LineDelimiter.WINDOWS.getValue())));
		
		// Test encoder decoder
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CmccSipcCodecFactory(Charset.forName("UTF-8"))));
		
		connector.setHandler(new MClientHandler("")); // "你好！\r\n大家好！"
		connector.connect(new InetSocketAddress("localhost", 9123));
	}

	
	public static void main(String args[]) {
		MClient	t = new MClient();	
	}
}

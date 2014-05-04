package com.eric.mina.test.client.endecoder.multi;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eric.mina.test.server.endecoder.multi.CmccSipcCodecFactory;
import com.eric.mina.test.server.endecoder.multi.SmsObject;

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
		ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 9123));
		future.awaitUninterruptibly();
		for (int i = 0; i < 3; i++) {
			SmsObject sms = new SmsObject();
			sms.setSender("15811954658");
			sms.setReceiver("13649827601");
			sms.setMessage("Hello,你好， this is a sms message.");
			future.getSession().write(sms);
			System.out.println("************** " + i);
		}
	}

	
	public static void main(String args[]) {
		MClient	t = new MClient();	
	}
}

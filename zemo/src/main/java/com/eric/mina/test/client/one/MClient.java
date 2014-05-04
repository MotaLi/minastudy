package com.eric.mina.test.client.one;

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

public class MClient {
	public MClient() {
//		init();
	}
	// first simple example
	public void init() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		connector.setHandler(new MClientHandler("你好！\r\n大家好！"));
		connector.connect(new InetSocketAddress("localhost", 9123));
		
	}
	
	// second example synchronized
	public void second() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		connector.setHandler(new MClientHandler("你好！\r\n大家好！"));
		// synchronized
		ConnectFuture connectfuture = connector.connect(new InetSocketAddress("localhost", 9123));
		connectfuture.awaitUninterruptibly();
	}
	
	// Third example asynchronous
	public void third() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		connector.setHandler(new MClientHandler("你好！\r\n大家好！"));
		// asynchronous
		ConnectFuture connectfuture = connector.connect(new InetSocketAddress("localhost", 9123));
		connectfuture.addListener(new IoFutureListener<ConnectFuture>() {
			@Override
			public void operationComplete(ConnectFuture future){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				// the following line will cause a exception while the connection set up at first time
				// if not commented.
				// The reason:The line 35 at MClientHandler. because that statement not commented, and interrupted the session.
				IoSession session = future.getSession();
				if (null == session) {
					System.out.println("The session is null");
				} else {
					System.out.println(session.getId());
				}
				System.out.println("+++++++++++++++++++++++++");
			}			
		});
		System.out.println("*********************************");
	}
	
	public static void main(String args[]) {
		MClient	t = new MClient();
		t.third();
	}
}

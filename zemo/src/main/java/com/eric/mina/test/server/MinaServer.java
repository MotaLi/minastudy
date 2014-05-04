package com.eric.mina.test.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	
	public void init() throws Exception{
		SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		//设置解析器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		acceptor.setHandler(new BexnHandler());
		acceptor.bind(new InetSocketAddress(8080));		
	}
	
	public MinaServer() throws Exception {
		init();
	}
	
	public static void main(String[] args) throws Exception {
		new MinaServer();
		System.out.println("Server start");
	}
}

class BexnHandler extends IoHandlerAdapter{
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("Server Received Message: " + message +  " from " + session.getRemoteAddress());		
		super.messageReceived(session, message);
		
//		session.write("messageReceived "+message);
//		session.closeOnFlush();
		
		session.write(" To client: "+message);
		session.close(false);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if (session.isConnected()) {
			session.close(false);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		session.close(false);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session); 
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30000);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		session.close(false);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}
}



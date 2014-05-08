package com.eric.mina.test.server.udp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpServer extends IoHandlerAdapter{
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		SocketAddress socketAddress = session.getRemoteAddress();
		log.info("Remote address is:" + socketAddress);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof IoBuffer) {
			IoBuffer buffer = (IoBuffer) message;
			long m = buffer.getLong();
			log.info("Message received.Free memory is :" + m);
		} else {
			log.info("Message type is not IoBuffer.");
		}
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	private static final Logger log = LoggerFactory.getLogger(UdpServer.class);

	public void test() throws Exception {
		IoAcceptor  acceptor = new NioDatagramAcceptor();		
		acceptor.getFilterChain().addLast("filter", new LoggingFilter());
		acceptor.setHandler(this);
		DatagramSessionConfig dcf = (DatagramSessionConfig)acceptor.getSessionConfig();
		dcf.setReuseAddress(true);
		acceptor.bind(new InetSocketAddress("localhost", 9123)); 
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		UdpServer t = new UdpServer();
		t.test();
	}
}

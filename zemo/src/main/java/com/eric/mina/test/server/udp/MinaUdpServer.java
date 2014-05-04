package com.eric.mina.test.server.udp;

import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.integration.jmx.IoServiceMBean;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaUdpServer extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory
			.getLogger(MinaUdpServer.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		IoBuffer buffer = (IoBuffer) message;
		String msg = buffer.getString(3, Charset.forName("UTF-8").newDecoder());
		log.info("The receive message is : " + msg);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {		
		super.sessionClosed(session);
		log.info("........Session is closed.");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {		
		super.sessionCreated(session);
		log.info("........Session is created.");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}
	
	public void init() throws Exception {
		IoAcceptor acceptor = new NioDatagramAcceptor();		
		acceptor.setHandler(this);
		acceptor.getFilterChain().addLast("log", new LoggingFilter());
		((DatagramSessionConfig)acceptor.getSessionConfig()).setReuseAddress(true);
		acceptor.bind(new InetSocketAddress(9122));
		this.registerObject(acceptor);
	}
	
	public void registerObject(IoAcceptor acceptor) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();		
		IoServiceMBean acceptorMBean = new IoServiceMBean(acceptor);
		ObjectName acceptorName = new ObjectName(acceptor.getClass()
				.getPackage().getName()
				+ ": type=IOAccepotr, Name = "
				+ acceptor.getClass().getSimpleName());
		mbs.registerMBean(acceptorMBean, acceptorName);
	}

	public MinaUdpServer() throws Exception {
		init();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MinaUdpServer server = new MinaUdpServer();
	}

}

package com.eric.mina.test.server.udp;

import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.JDialog;

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

import com.sun.jdmk.comm.HtmlAdaptorServer;

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
		// 创建MBean
		IoServiceMBean acceptorMBean = new IoServiceMBean(acceptor);
		ObjectName acceptorName = new ObjectName(acceptor.getClass()
				.getPackage().getName()
				+ ": type=IOAccepotr, Name = "
				+ acceptor.getClass().getSimpleName());
		// 将MBean注册到MBeanServer中
		mbs.registerMBean(acceptorMBean, acceptorName);
		
        //创建适配器，用于能够通过浏览器访问MBean (http://localhost:9797)
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(9797);
        mbs.registerMBean(adapter, new ObjectName(
                    "MyappMBean:name=htmladapter,port=9797"));
        adapter.start();      

        //由于是为了演示保持程序处于运行状态，创建一个图形窗口
        javax.swing.JDialog dialog = new JDialog();
        dialog.setName("jmx test");
        dialog.setVisible(true);
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

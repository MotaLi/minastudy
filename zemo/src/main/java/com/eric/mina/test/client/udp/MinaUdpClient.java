package com.eric.mina.test.client.udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class MinaUdpClient extends IoHandlerAdapter {
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
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

	public MinaUdpClient() throws Exception {
		init();
	}
	
	private void init() throws Exception {
		IoConnector connector = new NioDatagramConnector();
		connector.setHandler(this);
		ConnectFuture future = connector.connect(new InetSocketAddress("localhost",9122));
		future.awaitUninterruptibly();
		IoBuffer buffer = IoBuffer.allocate(7);
		buffer.putString("UDP OK!", Charset.forName("UTF-8").newEncoder());
		buffer.flip();
		WriteFuture wfuture = future.getSession().write(buffer);
		connector.dispose();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MinaUdpClient udpclient = new MinaUdpClient();
	}

}

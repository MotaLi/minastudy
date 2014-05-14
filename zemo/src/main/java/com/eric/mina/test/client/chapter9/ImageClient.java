package com.eric.mina.test.client.chapter9;

import java.awt.image.BufferedImage;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eric.mina.test.server.chapter9.ImageCodecFactory;
import com.eric.mina.test.server.chapter9.ImageResponse;

public class ImageClient implements IoHandler {
	public static final int CONNECT_TIMEOUT = 3000;

	private String host;
	private int port;
	private SocketConnector connector;
	private IoSession session;
	private ImageListener imageListener;

	static class ImageListener {
		void onImages(BufferedImage img1, BufferedImage img2) {			
		}
	}
	public ImageClient(String host, int port, ImageListener imageListener) {
		this.host = host;
		this.port = port;
		this.imageListener = imageListener;
		connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ImageCodecFactory(true)));
		connector.setHandler(this);
	}
	public static void main(String args[]) {
		ImageClient client = new ImageClient("localhost", 33789, null);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		ImageResponse response = (ImageResponse) message;
		imageListener.onImages(response.getImg1(), response.getImg2());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

}

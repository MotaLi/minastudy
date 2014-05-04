package com.eric.mina.test.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
			acceptor.bind(new InetSocketAddress(9123));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	class ServerHandler extends IoHandlerAdapter {
		
	}
}

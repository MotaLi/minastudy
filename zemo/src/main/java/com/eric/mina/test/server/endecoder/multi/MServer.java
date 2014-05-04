package com.eric.mina.test.server.endecoder.multi;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MServer {
	
	public void init() throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
//		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CmccSipcCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(new MServerHandler());
		acceptor.bind(new InetSocketAddress(9123));
		
		// ------------
		acceptor.getFilterChain().addLast("threadpool", new ExecutorFilter());
	}
	
	public MServer() throws Exception{
		init();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		new MServer();
	}

}

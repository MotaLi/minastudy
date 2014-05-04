package com.eric.mina.test.server.log.filter;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.util.ReferenceCountingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MServer {
	
	public void init() throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		acceptor.setHandler(new MServerHandler());
		
		// start--logger test
		LoggingFilter lf = new LoggingFilter();
		lf.setSessionOpenedLogLevel(LogLevel.ERROR);
		acceptor.getFilterChain().addLast("logger", lf);
		// end--
		
		// start-- filter test
		acceptor.getFilterChain().addLast("myfilter", new ReferenceCountingFilter(new MyFilter()));
		// end--
		
		acceptor.bind(new InetSocketAddress(9123));	
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

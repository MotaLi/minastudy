package com.eric.mina.test.client.demux;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eric.mina.test.server.demux.MathProtocolCodecFactory;
import com.eric.mina.test.server.demux.ResultMessage;

public class Client extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(Client.class);
	
	public static void main(String args[]) {
		Client client = new Client();
		IoConnector connector = new NioSocketConnector();		
		connector.setConnectTimeoutMillis(30000);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MathProtocolCodecFactory(false)));
		connector.setHandler(client);
		connector.connect(new InetSocketAddress("localhost", 9123));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		SendMessage sm = new SendMessage();
		sm.setI(100);
		sm.setJ(99);
		sm.setSymbol('-');
		session.write(sm);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		ResultMessage rs = (ResultMessage) message;
		log.info("Caculator result:" + String.valueOf(rs.getResult()));
	}


}

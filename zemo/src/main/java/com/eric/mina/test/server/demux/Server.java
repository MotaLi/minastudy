package com.eric.mina.test.server.demux;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eric.mina.test.client.demux.SendMessage;

public class Server extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(Server.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		IoAcceptor acceptor = new NioSocketAcceptor();
		LoggingFilter lf = new LoggingFilter();
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
		acceptor.getFilterChain().addLast("logger", lf);
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MathProtocolCodecFactory(true)));
		acceptor.setHandler(server);
		acceptor.bind(new InetSocketAddress(9123));

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
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
		session.close(true);
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
		SendMessage sm = (SendMessage) message;
		log.info("The message received is [ " + sm.getI() + " "
				+ sm.getSymbol() + " " + sm.getJ() + " ]");
		ResultMessage rs = new ResultMessage();
		rs.setResult(sm.getI() + sm.getJ());
		session.write(rs);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

}

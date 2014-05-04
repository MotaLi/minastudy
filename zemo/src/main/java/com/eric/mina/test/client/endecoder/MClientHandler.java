package com.eric.mina.test.client.endecoder;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eric.mina.test.server.endecoder.SmsObject;

public class MClientHandler extends IoHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(MClientHandler.class);
	private final String values;
	
	public MClientHandler(String values) {
		this.values = values;
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
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
//		session.close(true);
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
		
		// session.write(values);
		
		// 发送短信
		SmsObject sms = new SmsObject();
		sms.setSender("15811954658");
		sms.setReceiver("13649827601");
		sms.setMessage("Hello,你好， this is a sms message.");
		session.write(sms);
	}

}

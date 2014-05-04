package com.eric.mina.test.server.log.filter;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class MyFilter extends IoFilterAdapter {

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_destroy");
		super.destroy();
		
	}

	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_exceptionCaught");
		super.exceptionCaught(nextFilter, session, cause);
	}

	@Override
	public void filterClose(NextFilter nextFilter, IoSession session)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_filterClose");
		super.filterClose(nextFilter, session);
	}

	@Override
	public void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_filterWrite");
		super.filterWrite(nextFilter, session, writeRequest);
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_init");
		super.init();
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_messageReceived");
		super.messageReceived(nextFilter, session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_messageSent");
		super.messageSent(nextFilter, session, writeRequest);
	}

	@Override
	public void onPostAdd(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_onPostAdd");
		super.onPostAdd(parent, name, nextFilter);
	}

	@Override
	public void onPostRemove(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_onPostRemove");
		super.onPostRemove(parent, name, nextFilter);
	}

	@Override
	public void onPreAdd(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_onPreAdd");
		super.onPreAdd(parent, name, nextFilter);
	}

	@Override
	public void onPreRemove(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_onPreRemove");
		super.onPreRemove(parent, name, nextFilter);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_sessionClosed");
		super.sessionClosed(nextFilter, session);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_sessionCreated");
		super.sessionCreated(nextFilter, session);
	}

	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session,
			IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_sessionIdle");
		super.sessionIdle(nextFilter, session, status);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%%%_sessionOpened");
		super.sessionOpened(nextFilter, session);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}

package com.eric.mina.test.client.udp;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpClient extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(UdpClient.class);

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
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
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
	}
	
	private void init() throws Exception {
		IoConnector connector = new NioDatagramConnector();		
		connector.setHandler(this);
		connector.getFilterChain().addLast("filter", new LoggingFilter());
		ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 9123));
		future.addListener(new IoFutureListener<IoFuture>() {
			public void operationComplete(IoFuture future) {
                ConnectFuture connFuture = (ConnectFuture)future;
                if( connFuture.isConnected() ){
                   IoSession session = future.getSession();
                    try {
                        sendData(session);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.error("Not connected...exiting");
                }
            }

		});
	}
	
	private void sendData(IoSession session) throws InterruptedException {
	    for (int i = 0; i < 30; i++) {
	        long free = Runtime.getRuntime().freeMemory();
	        IoBuffer buffer = IoBuffer.allocate(8);
	        buffer.putLong(free);
	        buffer.flip();	        
	        session.write(buffer);
	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	            throw new InterruptedException(e.getMessage());
	        }
	    }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		UdpClient client = new UdpClient();
		client.init();

	}
}

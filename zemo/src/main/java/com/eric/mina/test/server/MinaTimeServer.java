package com.eric.mina.test.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {
	private static final int PORT = 9123;

	public static void main(String[] args) throws IOException {
		// 创建服务器监听
		IoAcceptor acceptor = new NioSocketAcceptor();
		// 增加日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 增加编码过滤器
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));

		// 指定业务逻辑处理器
		acceptor.setHandler(new TimeServerHandler());
		// 设置buffer的长度
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 设置连接超时时间
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 绑定端口
		acceptor.bind(new InetSocketAddress(PORT));
	}
}

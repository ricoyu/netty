package io.netty.example.myhttp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * <p>
 * Copyright: (C), 2023-11-06 15:45
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class HttpServer {
	
	public static final int port = 6789; //设置服务端端口
	private static EventLoopGroup group = new NioEventLoopGroup(); //通过nio方式来接收连接和处理连接
	private static ServerBootstrap bootstrap = new ServerBootstrap();
	
	private static final boolean SSL = false;
	
	/**
	 * Netty 创建全都是实现自AbsrtactBootstrap
	 * 客户端的是Bootstrap, 服务端的是ServerBootstrap
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final SslContext sslCtx;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		} else {
			sslCtx = null;
		}
		try {
			bootstrap.group(group)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerHandlerInit(sslCtx));
			
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			System.out.println("服务器启动, 等待连接...");
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync(); // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
		}
	}
}

package io.netty.example.stickyhalfpackage.lengthfield;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * <p>
 * Copyright: (C), 2023-11-04 18:03
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LengthFieldEchoServer {
	
	public static final String RESPONSE = "Welcome to Netty!";
	
	private final int port;
	
	public LengthFieldEchoServer(int port) {
		this.port = port;
	}
	
	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
					.channel(NioServerSocketChannel.class)
					.localAddress(port)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//基于换行回车符解决粘报半包问题
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							//ch.pipeline().addLast(new StringDecoder(UTF_8));
							ch.pipeline().addLast(new LengthFieldPrepender(2));
							//ch.pipeline().addLast(new StringEncoder(UTF_8));
							//添加一个EchoServerHandler到子Channel的ChannelPipeline
							ch.pipeline().addLast(new LengthFieldEchoServerHandler());
							
						}
					});
			ChannelFuture channelFuture = bootstrap.bind().sync();
			System.out.println("服务器启动, 等待连接...");
			channelFuture.channel().closeFuture().sync();
			System.out.println("有客户端连接上来了");
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) {
		try {
			new LengthFieldEchoServer(8080).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

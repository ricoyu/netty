package io.netty.example.stickyhalfpackage.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

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
public class DelimiterEchoServer {
	
	public static final String DELIMITER_SAMBOL = "@~";
	
	private final int port;
	
	public DelimiterEchoServer(int port) {
		this.port = port;
	}
	
	public void start() throws InterruptedException {
		DelimiterEchoServerHandler echoServerHandler = new DelimiterEchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
					.channel(NioServerSocketChannel.class)
					.localAddress(port)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer(DELIMITER_SAMBOL.getBytes(UTF_8));
							//基于换行回车符解决粘报半包问题
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							//添加一个EchoServerHandler到子Channel的ChannelPipeline
							ch.pipeline().addLast(echoServerHandler);
							
						}
					});
			ChannelFuture channelFuture = bootstrap.bind().sync();
			System.out.println("服务器启动, 等待连接...");
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) {
		try {
			new DelimiterEchoServer(8080).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

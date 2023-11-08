package io.netty.example.udp.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * <p>
 * Copyright: (C), 2023-11-08 14:06
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class UdpAnswerSide {
	
	public static final int ANSWER_PORT=8080;
	public static final String ANSWER = "古诗来了";
	
	public void run(int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//UDP无连接, 没有所谓的接收连接的说法
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioDatagramChannel.class) //UDP一些要用NioDatagramChannel来创建
					.handler(new AnswerHandler());
			
			//没有接收客户端连接的过程, 监听本地端口即可
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			System.out.println("应答服务已启动...");
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new UdpAnswerSide().run(ANSWER_PORT);
	}
}

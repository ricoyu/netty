package io.netty.example.udp.broadcast.acceptside;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.example.udp.broadcast.LogConst;

import java.net.InetSocketAddress;

/**
 * 日志的接收端
 * <p>
 * Copyright: (C), 2023-11-08 16:52
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogEventMonitor {
	
	private final EventLoopGroup group;
	private final Bootstrap bootstrap;
	
	public LogEventMonitor(InetSocketAddress address) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, true)
				.option(ChannelOption.SO_REUSEADDR, true)
				.handler(new ChannelInitializer<NioDatagramChannel>() {
					@Override
					protected void initChannel(NioDatagramChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new LogEventDecoder());
						pipeline.addLast(new LogEventHandler());
					}
				}).localAddress(address);
	}
	
	public Channel bind() {
		//绑定channel, 注意, DatagramChannel是无连接的
		return bootstrap.bind().syncUninterruptibly().channel();
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(LogConst.MONITOR_SIDE_PORT));
		try {
			Channel channel = monitor.bind();
			System.out.println("Udp Answer Side running");
			channel.closeFuture().sync();
		} finally {
			monitor.stop();
		}
	}
}

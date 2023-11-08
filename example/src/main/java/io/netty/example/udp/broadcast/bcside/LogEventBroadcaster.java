package io.netty.example.udp.broadcast.bcside;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.example.udp.broadcast.LogConst;
import io.netty.example.udp.broadcast.LogMsg;

import java.net.InetSocketAddress;

/**
 * <p>
 * Copyright: (C), 2023-11-08 16:14
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogEventBroadcaster {
	
	private final EventLoopGroup group;
	
	private final Bootstrap bootstrap;
	
	public LogEventBroadcaster(InetSocketAddress remoteAddress) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioDatagramChannel.class)
				//设置通讯选项为广播模式
				.option(ChannelOption.SO_BROADCAST, true)
				.handler(new LogEventEncoder(remoteAddress));
	}
	
	public void run() throws Exception {
		Channel channel = bootstrap.bind(0).sync().channel();
		long count = 0;
		//启动主处理循环, 模拟日志发送
		for (; ; ) {
			String msg = LogConst.getLogInfo();
			channel.writeAndFlush(new LogMsg(null, ++count, msg));
			System.out.println("广播端发送消息: " + msg);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("线程被中断了吗? : " + Thread.interrupted());
				break;
			}
		}
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		//如果是广播的话IP固定写成255.255.255.255, 表示这是一个广播地址
		LogEventBroadcaster broadcaster = new LogEventBroadcaster(
				new InetSocketAddress("255.255.255.255",
						LogConst.MONITOR_SIDE_PORT));
		try {
			broadcaster.run();
		} finally {
			broadcaster.stop();
		}
	}
}

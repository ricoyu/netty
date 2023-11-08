package io.netty.example.udp.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.*;

/**
 * 发送端
 * <p>
 * Copyright: (C), 2023-11-08 14:04
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class UdpquestionSide {
	
	public static final String QUESTION = "告诉我一句古诗";
	
	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioDatagramChannel.class) //表示这是一个UDP通讯
					.handler(new QuestionHandler());
			
			Channel channel = bootstrap.bind(0).sync().channel();
			channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(QUESTION, UTF_8), 
					new InetSocketAddress("localhost", port)))
					.sync();
			if(!channel.closeFuture().await(15000, MILLISECONDS)) {
				System.out.println("等待超时");
			}
		}finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new UdpquestionSide().run(UdpAnswerSide.ANSWER_PORT);
	}
}

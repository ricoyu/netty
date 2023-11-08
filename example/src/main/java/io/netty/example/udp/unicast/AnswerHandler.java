package io.netty.example.udp.unicast;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * Copyright: (C), 2023-11-08 14:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class AnswerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	private static final String[] DICTIONARY = {
			"只要功夫深, 铁杵磨成针",
			"旧时王谢堂前燕, 飞入寻常百姓家",
			"一寸光阴一寸金, 寸金难买寸光阴",
			"老骥伏枥, 志在千里. 烈士暮年, 壮心不已"
	};
	
	private static Random random = new Random();
	private String nextQuote() {
		return DICTIONARY[random.nextInt(DICTIONARY.length-1)];
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		String request = packet.content().toString(UTF_8);
		System.out.println("接收到请求: "+request);
		if (UdpquestionSide.QUESTION.equals(request)) {
			String answer = UdpAnswerSide.ANSWER + nextQuote();
			System.out.println("接收到请求");
			ctx.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer(answer, UTF_8), 
						packet.sender()));
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}

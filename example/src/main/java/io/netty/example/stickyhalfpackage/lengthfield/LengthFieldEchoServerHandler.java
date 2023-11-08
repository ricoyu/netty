package io.netty.example.stickyhalfpackage.lengthfield;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * Copyright: (C), 2023-11-04 17:58
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LengthFieldEchoServerHandler extends ChannelInboundHandlerAdapter {
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		String request = in.toString(UTF_8);
		System.out.println("Server accept[" + request + "] and the counter is:" + counter.incrementAndGet());
		String resp = LengthFieldEchoServer.RESPONSE;
		ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes(UTF_8)));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			System.out.println("远程主机关闭的连接");
			ctx.close();
		} else {
			cause.printStackTrace();
		}
	}
}

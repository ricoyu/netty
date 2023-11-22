package io.netty.example.stickyhalfpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 演示粘包/半包问题
 * <p>
 * Copyright: (C), 2023-11-04 17:50
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf msg = null;
		String request = "Mark liston, King,James,Deer" + System.getProperty("line.separator");
		//连发100条消息, 实际会发生粘包, 服务端只收到一条合并起来的消息
		for (int i = 0; i < 100; i++) {
			msg = Unpooled.buffer(request.length());
			msg.writeBytes(request.getBytes());
			//这里是写一行发一行
			ctx.writeAndFlush(msg);
		}
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("Client accept[" + msg.toString(UTF_8) + "] and the counter is:" + counter.incrementAndGet());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
}
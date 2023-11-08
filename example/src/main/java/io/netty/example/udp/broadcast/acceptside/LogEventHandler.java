package io.netty.example.udp.broadcast.acceptside;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.udp.broadcast.LogMsg;

/**
 * 日志的业务处理类, 实际的业务处理, 接收日志信息
 * <p>
 * Copyright: (C), 2023-11-08 17:16
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogMsg> {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogMsg msg) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(msg.getTime());
		sb.append(" [");
		sb.append(msg.getSource().toString());
		sb.append("]; [");
		sb.append(msg.getMsgId());
		sb.append("] : ");
		sb.append(msg.getMsg());
		
		System.out.println(sb.toString());
	}
}

package io.netty.example.udp.broadcast.bcside;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.example.udp.broadcast.LogMsg;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 编码, 将实际的日志实体类编码为DatagramPacket
 * <p>
 * Copyright: (C), 2023-11-08 16:37
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogMsg> {
	
	private final InetSocketAddress remoteAddress;
	
	public LogEventEncoder(InetSocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, LogMsg msg, List<Object> out) throws Exception {
		byte[] bytes = msg.getMsg().getBytes(UTF_8);
		//两个long类型的字段, long占8字节, 额外的一字节是LogMsg.SEPARATOR占用的空间
		ByteBuf buf = ctx.alloc().buffer(bytes.length + 8 * 2 + 1);
		buf.writeLong(msg.getTime());
		buf.writeLong(msg.getMsgId());
		buf.writeByte(LogMsg.SEPARATOR);
		buf.writeBytes(bytes);
		
		out.add(new DatagramPacket(buf, remoteAddress));
	}
}

package io.netty.example.udp.broadcast.acceptside;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.example.udp.broadcast.LogMsg;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 解码, 将DatagramPacket解码为实际的日志实体类LogMsg
 * <p>
 * Copyright: (C), 2023-11-08 17:02
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogEventDecoder extends MessageToMessageEncoder<DatagramPacket> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
		ByteBuf buf = msg.content();
		long time = buf.readLong();
		System.out.println("接收到"+time+"发送的消息");
		long msgId = buf.readLong();
		System.out.println("消息ID:" + msgId);
		byte separator = buf.readByte();
		//获取读索引当前的位置, 就是分隔符的索引+1
		int idx = buf.readerIndex();
		//提取日志消息, 从读索引开始, 到最后为日志的信息
		String msgContent = buf.slice(idx, buf.readableBytes()).toString(UTF_8);
		
		LogMsg logMsg = new LogMsg(msg.sender(), msgContent, msgId, time);
		//作为本handler的处理结果, 交给后面的handler进行处理
		out.add(logMsg);
	}
}

package io.netty.example.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * <p>
 * Copyright: (C), 2023-11-08 11:17
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		//检查是否有足够的字节来编码, int为4个字节
		while (msg.readableBytes() >= 4) {
			//从输入的ByteBuf中读取下一个整数, 并且计算其绝对值
			int value = Math.abs(msg.readInt());
			//将该整数写入到编码消息的List中
			out.add(value);
		}
	}
}

package io.netty.example.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * <p>
 * Copyright: (C), 2023-11-06 15:32
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class TooLongExtSample extends ByteToMessageDecoder {
	
	private static final int MAX_SIZE = 1024;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readbleBytes = in.readableBytes(); //当前可读字节数
		if (readbleBytes > MAX_SIZE) {
			ctx.close(); //关闭连接
			throw new TooLongFrameException("传入的数据太多了, 最大不能超过" + MAX_SIZE);
		}
	}
}

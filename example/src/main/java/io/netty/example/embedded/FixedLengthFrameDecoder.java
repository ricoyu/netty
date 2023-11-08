package io.netty.example.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 扩展ByteToMessageDecoder以处理入站字节, 并将它们解码为消息
 * <p>
 * Copyright: (C), 2023-11-08 10:32
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
	
	private final int frameLength;
	
	public FixedLengthFrameDecoder(int frameLength) {
		if (frameLength<=0) {
			throw new IllegalArgumentException("frameLength must be a positive value " + frameLength);
		}
		this.frameLength = frameLength;
	}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//检查是否有足够的字节可以被读取, 以生成下一个帧
		while (in.readableBytes() >= frameLength) {
			//从ByteBuf中读取一个新帧
			ByteBuf buf = in.readBytes(frameLength);
			//将该帧添加到已被解码的消息列表中
			out.add(buf);
		}
	}
}

package io.netty.example.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 测试入站
 * <p>
 * Copyright: (C), 2023-11-08 10:39
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class FixedLengthFrameDecoderTest {
	
	@Test
	public void testFramesDecoded() {
		//创建一个ByteBuf, 并存储9个字节
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 9; i++) {
			buf.writeByte(i);
		}
		
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
		assertFalse(channel.writeInbound(input.readBytes(1)));
		assertFalse(channel.writeInbound(input.readBytes(1)));
		assertTrue(channel.writeInbound(input.readBytes(1)));
		
		assertTrue(channel.writeInbound(input.readBytes(6)));
		
		channel.finish();
		
		/*
		 * 读取生成的消息, 并且验证是否有3帧, 其中每帧都为3字节
		 */
		ByteBuf read = (ByteBuf)channel.readInbound();
		//和源进行对比
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		read = (ByteBuf)channel.readInbound();
		//和源进行对比
		assertEquals(buf.readSlice(3), read);
		read.release();

		read = (ByteBuf)channel.readInbound();
		//和源进行对比
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		assertNull(channel.readInbound());
		buf.release();
	}
}

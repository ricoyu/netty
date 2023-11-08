package io.netty.example.myhttp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;

import java.net.URI;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * Copyright: (C), 2023-11-06 18:08
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		URI uri = new URI("/test");
		String msg = "hello";
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_1, 
				GET, 
				uri.toASCIIString(), 
				Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
		request.headers().set(HttpHeaderNames.HOST, HttpClient.HOST);
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
		ctx.writeAndFlush(request);
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		FullHttpResponse response = (FullHttpResponse) msg;
		System.out.println(response.status());
		System.out.println(response.headers());
		ByteBuf content = response.content();
		System.out.println(content.toString(UTF_8));
		//释放内存, 避免内存泄漏
		response.release();
	}
}

package io.netty.example.myhttp.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * Copyright: (C), 2023-11-06 16:29
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class BusiHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 * 建立连接时，发送一条消息
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String request = "";
		String result = "";
		FullHttpRequest httpRequest = (FullHttpRequest) msg;
		System.out.println(httpRequest.headers());
		
		try {
			String path = httpRequest.uri();
			//获取body
			String body = httpRequest.content().toString(io.netty.util.CharsetUtil.UTF_8);
			//获取请求方法
			String method = httpRequest.method().name();
			System.out.println("接收到:" + method + " 请求");
			if (!"/test".equalsIgnoreCase(path)) {
				result = "非法请求!"+path;
				send(ctx, result, HttpResponseStatus.BAD_REQUEST);
				return;
			} 
			
			//如果是get请求
			if(HttpMethod.GET.name().equals(method)){
				//接收到的消息, 做业务逻辑处理
				System.out.println("body:" + body);
				result = "GET请求,应答: " + RespConstant.getNews();
				send(ctx, result, HttpResponseStatus.OK);
				return;
			}
			
			if (HttpMethod.POST.name().equals(method)) {
				//接收到的消息, 做业务逻辑处理
				System.out.println("body:" + body);
				result = "POST请求,应答: " + RespConstant.getNews();
				send(ctx, result, HttpResponseStatus.OK);
				return;
			}
		} finally {
			httpRequest.release();
		}
	}
	
	private void send(ChannelHandlerContext ctx, String content, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
				status, 
				Unpooled.copiedBuffer(content, UTF_8));
		
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}

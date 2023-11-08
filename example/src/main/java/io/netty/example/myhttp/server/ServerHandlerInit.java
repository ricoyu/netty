package io.netty.example.myhttp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * <p>
 * Copyright: (C), 2023-11-06 15:53
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {
	
	private final SslContext sslCtx;
	
	public ServerHandlerInit(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		//把请求报文或者响应报文解码成HttpMessage, HttpContent和LastHttpContent.并把它们封装成一个FullHttpRequest对象
		pipeline.addLast("decoder", new HttpRequestDecoder());
		//把应答报文进行编码
		pipeline.addLast("encoder", new HttpResponseEncoder());
		//聚合http报文为一个完整的报文
		pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
		//对应答报文进行压缩
		pipeline.addLast("compressor", new HttpContentCompressor());
		pipeline.addLast(new BusiHandler());
	}
}

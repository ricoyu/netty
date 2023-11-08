package io.netty.example.myhttp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.myhttp.server.HttpServer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * <p>
 * Copyright: (C), 2023-11-06 17:58
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class HttpClient {
	
	public static final String HOST = "localhost";
	private static final boolean SSL = false;
	
	public void connect(String host, int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new HttpClientCodec());//这是一个编解码器
							//聚合http报文为一个完整的报文
							ch.pipeline().addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
							//对应答报文进行压缩
							ch.pipeline().addLast("decompressor", new HttpContentDecompressor());
							ch.pipeline().addLast(new HttpClientInboundHandler());
						}
					});
			
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();
			System.out.println("客户端关闭");
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		client.connect("localhost", HttpServer.port);
	}
}

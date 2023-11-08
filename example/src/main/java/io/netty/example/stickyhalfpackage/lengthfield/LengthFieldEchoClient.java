package io.netty.example.stickyhalfpackage.lengthfield;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * <p>
 * Copyright: (C), 2023-11-04 17:45
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LengthFieldEchoClient {
	
	public static final String REQUEST = "Mark,liston,King,James,Deer";
	private final int port;
	
	private final String host;
	
	public LengthFieldEchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.remoteAddress(host, port)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//基于换行回车符解决粘报半包问题
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2,0, 2));
							//ch.pipeline().addLast(new StringDecoder(UTF_8));
							ch.pipeline().addLast(new LengthFieldPrepender(2));
							//ch.pipeline().addLast(new StringEncoder(UTF_8));
							//添加一个EchoServerHandler到子Channel的ChannelPipeline
							ch.pipeline().addLast(new LengthFieldEchoClientHandler());
							
						}
					});
			ChannelFuture channelFuture = bootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();
		}finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) {
		try {
			new LengthFieldEchoClient("localhost", 8080).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
			
}

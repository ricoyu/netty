package io.netty.example.udp.broadcast;

import java.net.InetSocketAddress;

/**
 * 日志实体类
 * <p>
 * Copyright: (C), 2023-11-08 16:05
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogMsg {
	
	public static final byte SEPARATOR = (byte)':';
	
	//源的InetSocketAddress
	private final InetSocketAddress source;
	
	//消息内容
	private final String msg;
	
	//消息id
	private final long msgId;
	
	//消息发送的时间
	private final long time;
	
	public LogMsg(String msg) {
		this(null, msg, -1, System.currentTimeMillis());
	}
	
	public LogMsg(InetSocketAddress source, long msgId, String msg) {
		this(source, msg, msgId, System.currentTimeMillis());
	}
	
	public LogMsg(InetSocketAddress source, String msg, long msgId, long time) {
		this.source = source;
		this.msg = msg;
		this.msgId = msgId;
		this.time = time;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public long getMsgId() {
		return msgId;
	}
	
	public long getTime() {
		return time;
	}
	
	public InetSocketAddress getSource() {
		return source;
	}
}

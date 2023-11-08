/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.AbstractConstant;
import io.netty.util.ConstantPool;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * A {@link ChannelOption} allows to configure a {@link ChannelConfig} in a type-safe
 * way. Which {@link ChannelOption} is supported depends on the actual implementation
 * of {@link ChannelConfig} and may depend on the nature of the transport it belongs
 * to.
 *
 * @param <T>   the type of the value which is valid for the {@link ChannelOption}
 */
public class ChannelOption<T> extends AbstractConstant<ChannelOption<T>> {

    private static final ConstantPool<ChannelOption<Object>> pool = new ConstantPool<ChannelOption<Object>>() {
        @Override
        protected ChannelOption<Object> newConstant(int id, String name) {
            return new ChannelOption<Object>(id, name);
        }
    };

    /**
     * Returns the {@link ChannelOption} of the specified name.
     */
    @SuppressWarnings("unchecked")
    public static <T> ChannelOption<T> valueOf(String name) {
        return (ChannelOption<T>) pool.valueOf(name);
    }

    /**
     * Shortcut of {@link #valueOf(String) valueOf(firstNameComponent.getName() + "#" + secondNameComponent)}.
     */
    @SuppressWarnings("unchecked")
    public static <T> ChannelOption<T> valueOf(Class<?> firstNameComponent, String secondNameComponent) {
        return (ChannelOption<T>) pool.valueOf(firstNameComponent, secondNameComponent);
    }

    /**
     * Returns {@code true} if a {@link ChannelOption} exists for the given {@code name}.
     */
    public static boolean exists(String name) {
        return pool.exists(name);
    }

    /**
     * Creates a new {@link ChannelOption} for the given {@code name} or fail with an
     * {@link IllegalArgumentException} if a {@link ChannelOption} for the given {@code name} exists.
     */
    @SuppressWarnings("unchecked")
    public static <T> ChannelOption<T> newInstance(String name) {
        return (ChannelOption<T>) pool.newInstance(name);
    }

    public static final ChannelOption<ByteBufAllocator> ALLOCATOR = valueOf("ALLOCATOR");
    public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");
    public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");

    public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS = valueOf("CONNECT_TIMEOUT_MILLIS");
    /**
     * @deprecated Use {@link MaxMessagesRecvByteBufAllocator}
     */
    @Deprecated
    public static final ChannelOption<Integer> MAX_MESSAGES_PER_READ = valueOf("MAX_MESSAGES_PER_READ");
    public static final ChannelOption<Integer> WRITE_SPIN_COUNT = valueOf("WRITE_SPIN_COUNT");
    /**
     * @deprecated Use {@link #WRITE_BUFFER_WATER_MARK}
     */
    @Deprecated
    public static final ChannelOption<Integer> WRITE_BUFFER_HIGH_WATER_MARK = valueOf("WRITE_BUFFER_HIGH_WATER_MARK");
    /**
     * @deprecated Use {@link #WRITE_BUFFER_WATER_MARK}
     */
    @Deprecated
    public static final ChannelOption<Integer> WRITE_BUFFER_LOW_WATER_MARK = valueOf("WRITE_BUFFER_LOW_WATER_MARK");
    public static final ChannelOption<WriteBufferWaterMark> WRITE_BUFFER_WATER_MARK =
            valueOf("WRITE_BUFFER_WATER_MARK");

    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");
    public static final ChannelOption<Boolean> AUTO_READ = valueOf("AUTO_READ");

    /**
     * If {@code true} then the {@link Channel} is closed automatically and immediately on write failure.
     * The default value is {@code true}.
     */
    public static final ChannelOption<Boolean> AUTO_CLOSE = valueOf("AUTO_CLOSE");

    public static final ChannelOption<Boolean> SO_BROADCAST = valueOf("SO_BROADCAST");
    /**
     * TCP协议中, 有一个keepalive的机制, 用来检测对方是否还活着, 一般情况下, 如果一段时间内没有数据传输, 那么就会发送一个
     * keepalive的包, 如果对方没有回应, 那么就会认为对方已经挂掉了, 但是这个机制是有问题的, 因为如果对方挂掉了, 那么这个
     * keepalive的包是发不出去的, 所以这个机制并不可靠, 但是如果对方没有挂掉, 那么这个机制是可靠的, 所以这个机制是有用的,
     * <p/>
     * 一般我们在写网络程序里面很少用, 因为在TCP协议里面, 这个间隔是2个小时, 也就是说, 如果2个小时内没有数据传输, 那么
     * 就会发送一个keepalive的包, 这个时间太长了, 一般我们不会让客户端和服务端之间2个小时内没有数据传输, 所以这个机制
     * 一般不会用到, 但是在某些特殊的情况下, 这个机制是有用的, 比如说, 我们的客户端和服务端之间的网络是通过一个代理服务器
     * 连接的, 这个代理服务器是有可能挂掉的, 如果代理服务器挂掉了, 那么客户端和服务端之间就没有数据传输了, 这个时候就可以
     * 用到这个机制了, 但是这个机制是不可靠的, 因为如果代理服务器挂掉了, 那么客户端和服务端之间的数据是发不出去的, 所以
     * 这个机制是不可靠的, 但是如果代理服务器没有挂掉, 那么这个机制是可靠的, 所以这个机制是有用的
     * <p/>
     * 一般网络编程中我们很少去使用操作系统级别的KEEPALIVE机制, 而是自己实现
     */
    public static final ChannelOption<Boolean> SO_KEEPALIVE = valueOf("SO_KEEPALIVE");
    /**
     * 这个参数指定了内核为发送缓冲区分配的最大空间量。增加这个参数可以在发送大量数据时减少网络延迟, 允许更多的数据排队等待发送。
     */
    public static final ChannelOption<Integer> SO_SNDBUF = valueOf("SO_SNDBUF");
    /**
     * 这个参数指定了内核为接收缓冲区分配的最大空间量. 增加这个值可以让你的服务器处理更高的并发连接, 或是接收更大的数据块, 但也会占用更多的内存。
     */
    public static final ChannelOption<Integer> SO_RCVBUF = valueOf("SO_RCVBUF");
    /**
     * 假设某个服务启动后占用80端口, 过了一会这个服务挂掉了, 这是操作系统不会马上释放80端口, 如果此时立刻
     * 有另外一个服务启动要占用80端口, 那么此时有可能会报端口占用的错误, 这个时候就需要设置这个参数, 使得操作系统
     * 立刻释放端口, 这样新的服务就可以占用80端口了
     */
    public static final ChannelOption<Boolean> SO_REUSEADDR = valueOf("SO_REUSEADDR");
    /**
     * <ul>当在套接字上设置SO_LINGER选项时，你可以指定两个重要的行为:
     *     <li/>是否等待数据发送完成: 当启用SO_LINGER并且close调用发生时，如果还有数据待发送，系统会试图先发送完这些数据，然后再进行close操作
     *     <li/>关闭连接的方式: 在某些情况下，如果SO_LINGER设置了非零的超时时间，close调用会阻塞直到所有数据发送完毕并被对方确认，或者超时发生。如果在此期间，TCP的状态变为CLOSED，那么系统将发送RST给对方，丢弃未发送完的数据
     *     <li/>Netty中设置SO_LINGER选项的方式如下:
     *     <li/>bootstrap.option(ChannelOption.SO_LINGER, 10); // 意味着在SocketChannel被关闭时, 底层的socket会等待最多10秒钟, 试图发送所有剩余的数据。
     *     <li/>bootstrap.option(ChannelOption.SO_LINGER, 0); // 立即关闭socket，不等待
     *     <li/>bootstrap.option(ChannelOption.SO_LINGER, -1); // 禁用SO_LINGER, 使用系统默认行为
     * </ul>
     */
    public static final ChannelOption<Integer> SO_LINGER = valueOf("SO_LINGER");
    /**
     * 监听的时候的可连接队列的大小(等待队列), 一般我们不会去设置这个值, 因为操作系统会自动调整这个值, 但是如果我们的服务端
     * 和客户端之间的网络比较差, 那么我们可以设置这个值, 使得可连接队列的大小变大, 这样就可以减少网络传输的次数, 从而
     * 提高性能
     * <p/>
     * Linux一次只能处理一个连接, 如果有多个连接过来, 那么就会放到这个队列里面, 等待处理, 这个队列的大小就是这个参数
     */
    public static final ChannelOption<Integer> SO_BACKLOG = valueOf("SO_BACKLOG");
    public static final ChannelOption<Integer> SO_TIMEOUT = valueOf("SO_TIMEOUT");

    public static final ChannelOption<Integer> IP_TOS = valueOf("IP_TOS");
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
    public static final ChannelOption<Integer> IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");
    
    /**
     * 禁用nagle算法, <p/>
     * Nagle算法通过减少需要发送的小数据包的数量来增加网络的效率。它会积累小数据包并在确认前一个数据包之后或积累了更多数据时才发送。这样做可以减少网络上的小数据包数量，但是也会增加数据的发送延迟。<p/>
     * 但是如果我们的服务端和客户端之间的网络比较差, 那么我们可以禁用nagle算法, 这样就可以减少网络传输的次数, 从而提高性能
     * <p/>
     * 数据写完之后马上发出去, 不要累积一定的数据量再发出去
     */
    public static final ChannelOption<Boolean> TCP_NODELAY = valueOf("TCP_NODELAY");

    @Deprecated
    public static final ChannelOption<Boolean> DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION =
            valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");

    public static final ChannelOption<Boolean> SINGLE_EVENTEXECUTOR_PER_GROUP =
            valueOf("SINGLE_EVENTEXECUTOR_PER_GROUP");

    /**
     * Creates a new {@link ChannelOption} with the specified unique {@code name}.
     */
    private ChannelOption(int id, String name) {
        super(id, name);
    }

    @Deprecated
    protected ChannelOption(String name) {
        this(pool.nextId(), name);
    }

    /**
     * Validate the value which is set for the {@link ChannelOption}. Sub-classes
     * may override this for special checks.
     */
    public void validate(T value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
    }
}

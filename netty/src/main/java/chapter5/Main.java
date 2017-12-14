package chapter5;

import com.sun.xml.internal.stream.util.BufferAllocator;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/12/14 0014.
 */
@Slf4j
public class Main {

    private static final String host = "localhost";
    private static final int port = 1888;

    public static void main(String[] args) {
        startClient();
    }

    public static void startClient() {


        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            Channel channel = ctx.channel();
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < 1024; i++) {
                                sb.append("1");
                            }
                            ByteBuf buf = Unpooled.copiedBuffer(sb.toString(), CharsetUtil.UTF_8);
/*
                            ByteBufAllocator alloc  =  ctx.alloc();
                            ByteBuf b  =  alloc.directBuffer();
                            */
                            channel.writeAndFlush(buf.retainedDuplicate());
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            log.info("readble: " + buf.readableBytes() + " writeable:" + buf.writableBytes()+
                            " c: "+ buf.capacity());
                            ByteBuf buf2 = buf;
                            System.out.println(buf2.refCnt());
                        }

                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                           // ctx.channel().close();
                            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                            ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
                        }
                    });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();

            //阻塞，直到Channel 关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            group.shutdownGracefully();
        }
    }
}

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
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
        EventLoopGroup group = new NioEventLoopGroup(2);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline(); // get reference to pipeline;
                            ByteBuf b = Unpooled.copiedBuffer("frome the pip", CharsetUtil.UTF_8);
                            pipeline.channel().writeAndFlush(b);

                            //将该实例作为"handler1"添加到ChannelPipeline 中
                            pipeline.addFirst("handler1", new FirstHandler());
                            pipeline.addAfter("handler1", "handler2", new SecondHandler());
                            pipeline.addLast("handler3", new ThirdHandler());

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


    private static ChannelInboundHandlerAdapter inBounderHandlerTest() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                log.info("{} handleradded ", this.getClass().getSimpleName());
            }

            @Override
            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                log.info("{} registered", this.getClass().getSimpleName());
            }

            @Override
            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                log.info("{} unregistered", this.getClass().getSimpleName());
            }

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                log.info("{} handlerRemoved", this.getClass().getSimpleName());
            }

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                log.info("{} channelActive", this.getClass().getSimpleName());
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
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                log.info("{} inactive", this.getClass().getSimpleName());
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("{} channelRead", this.getClass().getSimpleName());
                ByteBuf buf = (ByteBuf) msg;
                log.info("readble: " + buf.readableBytes() + " writeable:" + buf.writableBytes() +
                        " c: " + buf.capacity());
                ByteBuf buf2 = buf;
                System.out.println(buf2.refCnt());
            }

            @Override
            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                log.info("{} writeability changed", this.getClass().getSimpleName());
            }

            @Override
            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                log.info("{} readComplete ", this.getClass().getSimpleName());
                // ctx.channel().close();
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                log.info(" {} exception", this.getClass().getSimpleName());
                super.exceptionCaught(ctx, cause);
            }
        };
    }


    private static ChannelOutboundHandlerAdapter outBounderHandlerTest() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                log.info("handleradded ");
            }

            //chanel绑定到本地时被调用
            @Override
            public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                super.bind(ctx, localAddress, promise);
            }

            //channel 连接到远程节点时被调用
            @Override
            public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                super.connect(ctx, remoteAddress, localAddress, promise);
            }

            //channel从远程节点断开时被调用
            @Override
            public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                super.disconnect(ctx, promise);
            }

            //当请求关闭channel时被调用
            @Override
            public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                super.close(ctx, promise);
            }

            // channel从eventloopzh中注销时被调用
            @Override
            public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                super.deregister(ctx, promise);
            }

            //
            @Override
            public void read(ChannelHandlerContext ctx) throws Exception {
                super.read(ctx);
            }

            @Override
            public void flush(ChannelHandlerContext ctx) throws Exception {
                super.flush(ctx);
            }

            // 请求通过channel将数据写到远程节点时被调用
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                super.write(ctx, msg, promise);
            }

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                log.info("handlerRemoved");
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                super.exceptionCaught(ctx, cause);
            }
        };
    }

    private static final class FirstHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerAdded ", this.getClass().getSimpleName());
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelRegistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelActive", this.getClass().getSimpleName());
            //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
            ByteBuf b = Unpooled.copiedBuffer("im the act "+this.getClass().getSimpleName(), CharsetUtil.UTF_8);
            ctx.writeAndFlush(b);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("{} channelRead", this.getClass().getSimpleName());
            ByteBuf buf = (ByteBuf) msg;
            buf.retain();
            log.info("      readable: " + buf.readableBytes() +
                    " writeable:" + buf.writableBytes() +
                    " capacity: " + buf.capacity()+" " +
                    "msg:"+((ByteBuf) msg).toString(CharsetUtil.UTF_8));

            ctx.writeAndFlush(buf.writeBytes(" channelRead".getBytes()));
            ctx.fireChannelRead(buf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelReadComplete ", this.getClass().getSimpleName());
            ctx.fireChannelReadComplete();
             //ctx.channel().close();
            //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            //ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            log.info("{} userEventTriggered ", this.getClass().getSimpleName());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelInactive", this.getClass().getSimpleName());
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelUnregistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelWritabilityChanged", this.getClass().getSimpleName());
        }
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerRemoved", this.getClass().getSimpleName());
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info(" {} exceptionCaught", this.getClass().getSimpleName());
            super.exceptionCaught(ctx, cause);
        }
    }

    private static final class SecondHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerAdded ", this.getClass().getSimpleName());
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelRegistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelActive", this.getClass().getSimpleName());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("{} channelRead", this.getClass().getSimpleName());
            ByteBuf buf = (ByteBuf) msg;
            buf.retain();

            log.info("      readable: " + buf.readableBytes() + " writeable:" + buf.writableBytes() + " c: " + buf.capacity()+" msg:"+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
            ctx.writeAndFlush(buf.writeBytes(" sec".getBytes()));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelReadComplete ", this.getClass().getSimpleName());
            //ctx.channel().close();
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            //ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            log.info("{} userEventTriggered ", this.getClass().getSimpleName());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelInactive", this.getClass().getSimpleName());
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelUnregistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelWritabilityChanged", this.getClass().getSimpleName());
        }
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerRemoved", this.getClass().getSimpleName());
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info(" {} exceptionCaught", this.getClass().getSimpleName());
            super.exceptionCaught(ctx, cause);
        }    }

    private static final class ThirdHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerAdded ", this.getClass().getSimpleName());
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelRegistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelActive", this.getClass().getSimpleName());
            Channel c = ctx.channel();
            ByteBuf b = Unpooled.copiedBuffer("im the first", CharsetUtil.UTF_8);
            ctx.writeAndFlush(b);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("{} channelRead", this.getClass().getSimpleName());
            ByteBuf buf = (ByteBuf) msg;
            log.info("      readble: " + buf.readableBytes() + " writeable:" + buf.writableBytes() + " c: " + buf.capacity()+" msg:"+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelReadComplete ", this.getClass().getSimpleName());
            ctx.channel().close();
            //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            //ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            log.info("{} userEventTriggered ", this.getClass().getSimpleName());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelInactive", this.getClass().getSimpleName());
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelUnregistered", this.getClass().getSimpleName());
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            log.info("{} channelWritabilityChanged", this.getClass().getSimpleName());
        }
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info("{} handlerRemoved", this.getClass().getSimpleName());
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info(" {} exceptionCaught", this.getClass().getSimpleName());
            super.exceptionCaught(ctx, cause);
        }    }

}

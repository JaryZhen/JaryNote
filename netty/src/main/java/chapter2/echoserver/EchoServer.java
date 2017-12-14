package chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 代码清单 2-2 EchoServer 类
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
                " <port>"
            );
            return;
        }
        //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        int port = Integer.parseInt(args[0]);
        //调用服务器的 start()方法
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //(1) 创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //(2) 创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                //(3) 指定所使用的 NIO 传输 Channel
                .channel(NioServerSocketChannel.class)
                //(4) 使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                //(5) 添加一个EchoServerHandler到于Channel的 ChannelPipeline
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("Server initChanneled :"+new Date());

                        //EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                        //这里对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable，
                        //这将在后面的章节中讲到。
                        ch.pipeline().addLast(serverHandler);
                    }
                });

            //(6) 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    //检查操作的状态
                    if (future.isSuccess()) {
                        //检查是否是异步的
                        Thread.sleep(500);
                        System.out.println(" isActive: "+future.channel().isActive()+" isRegistered: "+ future.channel().isRegistered()+" ");
                        System.out.println("started and listening for connections on:" + future.channel().localAddress());

                        //如果操作是成功的，则创建一个 ByteBuf 以持有数据
                        ByteBuf buffer = Unpooled.copiedBuffer("Server say:Hello", Charset.defaultCharset());
                        //将数据异步地发送到远程节点。返回一个 ChannelFuture
                        ChannelFuture wf = future.channel().writeAndFlush(buffer);
                        wf.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                if (future.isSuccess()) {
                                    System.out.println("yes");
                                }else {
                                    System.out.println("no");
                                }
                            }
                        });
                        // ...
                    } else {
                        //如果发生错误，则访问描述原因的 Throwable
                        Throwable cause = future.cause();
                        cause.printStackTrace();
                    }
                }
            });

            System.out.println("sync");
            //(7) 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
                       System.out.println("a sync");

        } finally {
            //(8) 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}

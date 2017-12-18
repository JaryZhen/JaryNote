package chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Jary on 2017/12/18 0018.
 */
@Slf4j
public class GetLogHandler  extends ChannelOutboundHandlerAdapter{
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        log.info("GetLogHandler read");
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

        //通过使用 ReferenceCountUtil.realse(...)方法释放资源
       // ReferenceCountUtil.release(msg);
        //通知 ChannelPromise数据已经被处理了
        //promise.setSuccess();
        DatagramPacket buf = (DatagramPacket) msg;
        log.info("GetLogHandler write {}",buf.toString());

        buf.retain();
        ctx.writeAndFlush(msg);
    }
}

package chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 代码清单 13-2 LogEventEncoder
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */

@Slf4j
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {


    private final InetSocketAddress remote;
    public LogEventEncoder (InetSocketAddress remote){

        this.remote = remote;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        byte [] file = msg.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte [] msgs = msg.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = ctx.alloc().buffer(file.length+msgs.length+1);

        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msgs);
        out.add(new DatagramPacket(buf,remote));
    }
}
